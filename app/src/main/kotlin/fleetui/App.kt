package fleetui

import fleet.dock.api.ThemeId
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
import noria.ui.components.examples.noriaExamples
import noria.ui.components.gallery.galleryView
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
import java.io.InputStream
import java.nio.file.Path

fun registerFonts() {
    val fontProvider = TypefaceFontProvider()
    setFontCollection(FontCollection().setDefaultFontManager(FontMgr.default).setAssetFontManager(fontProvider))
}

class FileResourceReader(private val path: String) : ResourceReader {
    override fun read(p0: String?) = FileInputStream("$path/$p0")
}

fun NoriaContext.mainWindow() {
    val theme = FleetTheme(ThemeId("light"), ThemeLoader.fromPath(Path.of("../light.json")))
    val dndRootState: StateCell<DnDRoot> = state { DnDRoot.Idle }
    val actionManager = DefaultActionManager()
    val portalNetwork = remember { MutableOpenMap.empty<PortalNetwork>() }
    cell(OpenMap {
        set(BindingThemeKey, theme.loadNoriaTheme())
        set(AnalyticsKey, NoopAnalytics)
        set(ActionManagerKey, actionManager)
        set(DnDKey, dndRootState)
        set(PortalNetworkKey, portalNetwork)
        set(ResourceReaderKey, FileResourceReader("../icons"))
    }) {
        window(
            title = "Gallery",
            initialSize = Size(800.px, 600.px),
            initialPosition = null,
            initialScreenId = null,
        ) {
            focusData {
                inputHandler { event ->
                    performActionFromKeyboard(actionManager, focusNode.actionContext(), event)
                }
            }

            vbox {
                gap(height = windowHeaderHeight)
                galleryView(noriaExamples())
            }
        }
    }
}

fun findAction(actionContext: ActionContext, triggers: Set<Trigger>): Action? {
    val contextActions = actionContext[CommonDataKeys.ContextActions] ?: emptyList()
    return contextActions.firstOrNull { action -> action.triggers.intersect(triggers).isNotEmpty() }
}

fun main() {
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
        withRenderLoop(
            root = {
                mainWindow()
            },
            bindings = OpenMap {
                set(ActionManagerKey, actionManager)
            }) {
            awaitCancellation()
        }
    }
}
