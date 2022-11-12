package noria.ui.components.examples

import noria.model.dimensions.Color
import noria.ui.components.gallery.gallery
import noria.ui.components.gap
import noria.ui.components.modifiers.Modifier
import noria.ui.components.modifiers.shadow
import noria.ui.components.modifiers.withModifier
import noria.ui.components.vbox

fun shadowExamples() = gallery("Shadows", "Shadows.kt") {
  example("Outer Shadow") {
    vbox {
      withModifier(Modifier.shadow(color = Color.green)) {
        gap(width = 80.unit, height = 40.unit)
      }
    }
  }
}
