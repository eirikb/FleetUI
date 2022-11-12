package noria.ui.components.examples

import noria.sideEffect
import noria.state
import noria.ui.components.Align
import noria.ui.components.gallery.gallery
import noria.ui.components.gap
import noria.ui.components.hbox
import noria.ui.components.modifiers.Modifier
import noria.ui.components.modifiers.constrain
import noria.ui.text.uiText

fun lazyConstraint() = gallery("Lazy Constrain", "LazyConstrain.kt") {
  example("Lazy Constrain") {
    val headerHeight = state { 0.unit }
    val height = 8.unit
    sideEffect { headerHeight.update { height } }
    hbox(align = Align.Center, modifier = Modifier.constrain(preferredHeight = headerHeight.read())) {
      gap(width = 3.unit)
      uiText("TEXT")
    }
  }
}
