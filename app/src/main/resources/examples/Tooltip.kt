package noria.ui.components.examples

import noria.ui.components.gallery.gallery
import noria.ui.components.withTooltip
import noria.ui.text.uiText

fun tooltipExamples() = gallery("Tooltip", "Tooltip.kt") {
  example("Tooltip") {
    withTooltip("Tooltip text") {
      uiText("Point on me")
    }
  }
}