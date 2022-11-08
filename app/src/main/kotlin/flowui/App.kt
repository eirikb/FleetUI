package flowui

import fleet.frontend.FleetFrontendSettingsKeys
import fleet.frontend.keymap.KeymapSource
import fleet.frontend.keymap.keymap
import fleet.plugins.keymap.frontend.parseKeymap
import fleet.preferences.FleetPaths
import fleet.themes.DEFAULT_THEME
import fleet.themes.loadNoriaTheme
import fleet.util.openmap.MutableOpenMap
import fleet.util.openmap.OpenMap
import kotlinx.coroutines.awaitCancellation
import noria.*
import noria.model.*
import noria.model.dimensions.Size
import noria.model.dimensions.px
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
import kotlin.io.path.readText

fun registerFonts() {
    val fontProvider = TypefaceFontProvider()
    setFontCollection(FontCollection().setDefaultFontManager(FontMgr.default).setAssetFontManager(fontProvider))
}

fun NoriaContext.mainWindow() {
    val theme = DEFAULT_THEME.loadNoriaTheme()
    val dndRootState: StateCell<DnDRoot> = state { DnDRoot.Idle }
    val keymapText =
        FleetPaths.Frontend.keymapDirectory.resolve("${FleetFrontendSettingsKeys.keymap.defaultValue}.json").readText()
    val shortcuts = parseKeymap(keymapText, KeymapSource.DEFAULT).map { it.binding }
    val keymap = keymap(shortcuts).mapValues { (_, keyBindings) -> keyBindings.map { it.trigger }.toSet() }
    val actionManager = DefaultActionManager()
    val portalNetwork = remember { MutableOpenMap.empty<PortalNetwork>() }
    cell(OpenMap {
        set(BindingThemeKey, theme)
        set(ActionManagerKey, actionManager)
        set(DnDKey, dndRootState)
        set(PortalNetworkKey, portalNetwork)
    }) {
        window(
            title = "Gallery",
            initialSize = Size(800.px, 600.px),
            initialPosition = null,
            initialScreenId = null,
            keymap = keymap
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
        println("Got event $event")
        event.letIfIs(WindowEvent.CloseRequested) {
            windowManager.stopApplication()
            EventHandlerResult.Handled
        } ?: EventHandlerResult.Unhandled
    }
    val actionManager = ActionManager { actionContext, triggers ->
        val action = findAction(actionContext, triggers)
        (action?.perform(actionContext) ?: Propagate.CONTINUE) to null
    }

    println("Starting...")
    eventLoop(windowManager, eventHandlerRegistry::runHandlers, Dispatchers.Default + eventHandlerRegistry) {
        println("Never reached")
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
