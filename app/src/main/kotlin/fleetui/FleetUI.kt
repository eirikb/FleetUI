package fleetui

import fleet.frontend.editor.actions.type
import fleet.dock.api.ThemeId
import fleet.frontend.editor.actions.DeleteDirection
import fleet.frontend.editor.actions.delete
import fleet.themes.FleetTheme
import fleet.themes.ThemeLoader
import fleet.themes.loadNoriaTheme
import fleet.util.openmap.MutableOpenMap
import fleet.util.openmap.OpenMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitCancellation
import noria.*
import noria.model.*
import noria.model.dimensions.Size
import noria.model.dimensions.px
import noria.ui.ResourceReader
import noria.ui.components.*
import noria.ui.components.editor.isAcceptable
import noria.ui.core.*
import noria.ui.events.inputHandler
import noria.ui.events.performActionFromKeyboard
import noria.ui.loop.withRenderLoop
import noria.windowManagement.api.EventHandlerRegistry
import noria.windowManagement.api.EventHandlerResult
import noria.windowManagement.api.letIfIs
import noria.windowManagement.api.setFontCollection
import noria.windowManagement.extensions.WindowEvent
import noria.windowManagement.extensions.eventLoop
import noria.windowManagement.extensions.stopApplication
import noria.windowManagement.impl.skiko.AwtSkikoWindowManager
import org.jetbrains.skia.FontMgr
import org.jetbrains.skia.paragraph.FontCollection
import org.jetbrains.skia.paragraph.TypefaceFontProvider
import java.io.FileInputStream
import java.nio.file.Path

fun registerFonts() {
    val fontProvider = TypefaceFontProvider()
    setFontCollection(FontCollection().setDefaultFontManager(FontMgr.default).setAssetFontManager(fontProvider))
}

class FileResourceReader(private val path: String) : ResourceReader {
    override fun read(p0: String?) = FileInputStream("$path/$p0")
}

fun NoriaContext.mainWindow(title: String, initialSize: Size, builder: UIContext.() -> Unit) {
    val theme = FleetTheme(ThemeId("light"), ThemeLoader.fromPath(Path.of("light.json")))
    val dndRootState: StateCell<DnDRoot> = state { DnDRoot.Idle }
    val actionManager = DefaultActionManager()
    val portalNetwork = remember { MutableOpenMap.empty<PortalNetwork>() }
    cell(OpenMap {
        set(BindingThemeKey, theme.loadNoriaTheme())
        set(AnalyticsKey, NoopAnalytics)
        set(ActionManagerKey, actionManager)
        set(DnDKey, dndRootState)
        set(PortalNetworkKey, portalNetwork)
        set(ResourceReaderKey, FileResourceReader("icons"))
    }) {
        window(
            title = title,
            initialSize = initialSize,
            initialPosition = null,
            initialScreenId = null,
        ) {
            contextActions(
                Action.contextAction(
                    defaultPresentation = ActionPresentation("Typing"),
                    presenter = { actionContext ->
                        val typedText = actionContext[CommonDataKeys.TypedText]!!
                        if (typedText.all { it.isAcceptable || it == '\b' }) {
                            ActionPresentation("Typing")
                        } else null
                    },
                    perform = { actionContext ->
                        val editorModel = actionContext[CommonDataKeys.EditorModel]!!
                        val typedText = actionContext[CommonDataKeys.TypedText]!!
                        if (typedText[0] == '\b') {
                            editorModel.mutate {
                                delete(DeleteDirection.BACKWARD)
                            }
                        } else {
                            editorModel.mutate {
                                type(typedText, withSmartHandlers = false)
                            }
                        }
                        Propagate.STOP
                    },
                    requirements = setOf(CommonDataKeys.EditorModel, CommonDataKeys.TypedText),
                    triggers = setOf(CommonTriggers.Typing)
                )
            )
            focusData {
                inputHandler { event ->
                    performActionFromKeyboard(actionManager, focusNode.actionContext(), event)
                }
            }

            builder()
        }
    }
}

fun findAction(actionContext: ActionContext, triggers: Set<Trigger>): Action? {
    val contextActions = actionContext[CommonDataKeys.ContextActions] ?: emptyList()
    return contextActions.firstOrNull { action -> action.triggers.intersect(triggers).isNotEmpty() }
}

fun mainWindow(title: String, initialSize: Size = Size(800.px, 600.px), builder: UIContext.() -> Unit) {
    registerFonts()
    val windowManager = AwtSkikoWindowManager

    val eventHandlerRegistry = EventHandlerRegistry()
    eventHandlerRegistry.registerHandler { event ->
        event.letIfIs(WindowEvent.CloseRequested) {
            windowManager.stopApplication()
            EventHandlerResult.Handled
        } ?: EventHandlerResult.Unhandled
    }
    val actionManager = ActionManager { actionContext, triggers ->
        val action = findAction(actionContext, triggers)
        (action?.perform(actionContext) ?: Propagate.CONTINUE) to null
    }

    eventLoop(windowManager, eventHandlerRegistry::runHandlers, Dispatchers.Default + eventHandlerRegistry) {
        withRenderLoop(root = {
            mainWindow(title, initialSize, builder)
        }, bindings = OpenMap {
            set(ActionManagerKey, actionManager)
        }) {
            awaitCancellation()
        }
    }
}
