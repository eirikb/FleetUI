package fleetui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import noria.state
import noria.ui.components.button
import noria.ui.components.vbox
import noria.ui.core.UIContext
import noria.ui.core.boundary
import noria.ui.core.launchOnce
import noria.ui.text.uiText

/***
 * Example for Flow-based state handling.
 * Don't know how to use RhizomeDB.
 *
 * Use like this:
 * In App.kt:
 *   mainWindow("") {
 *      flowDemo()
 *   }
 */

fun UIContext.flowDemo() {
    val flow = MutableStateFlow(0)

    vbox {
        var i = 0
        btn("Click me") {
            flow.emit(i++)
        }
        on(flow) {
            uiText("Clicked $it times")
        }
    }
}

fun UIContext.btn(text: String, action: suspend () -> Unit) {
    button(text, onClick = {
        CoroutineScope(Dispatchers.IO).launch {
            action()
        }
    })
}

fun <T> UIContext.on(flow: Flow<T>, builder: UIContext.(t: T) -> Unit) {
    val s = state<T?> { null }
    launchOnce {
        flow.collectLatest { i ->
            s.update { i }
        }
    }
    boundary {
        s.read()?.let {
            builder(this, it)
        }
    }
}
