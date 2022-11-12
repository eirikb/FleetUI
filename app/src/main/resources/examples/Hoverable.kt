package noria.ui.components.examples

import noria.memo
import noria.model.dimensions.Color
import noria.state
import noria.ui.components.*
import noria.ui.components.gallery.gallery
import noria.ui.text.uiText

fun hoverableExamples() = gallery("Hoverable", "Hoverable.kt") {
  example("Boolean") {
    hoverable { hovered ->
      val color = if (hovered) Color.green else Color.red
      exampleBox(color)
    }
  }

  /**
   * Merges hoverables so that their surface area is treated as if it were one.
   *
   * This is particularly useful with adjacent or overlapping hoverables, when `onMouseLeave` should
   * not be called when the mouse switches between them.
   */
  example("Merged Hoverables") {
    val hoveredState = state { false }
    val enteredCountState = state { 0 }
    val leftCountState = state { 0 }
    val hoverableMerger = memo {
      hoverableMerger(
        onMouseEnter = {
          hoveredState.update { true }
          enteredCountState.update { it + 1 }
        },
        onMouseLeave = {
          hoveredState.update { false }
          leftCountState.update { it + 1 }
        },
      )
    }
    vbox {
      // This is a simplified example but the two hoverables can live in entirely different places of the layout tree
      withCloseablePopup(hoveredState,
                         {
                           hoverable(hoverableMerger) {
                             val color = if (hoveredState.read()) Color.green else Color.red
                             box(color) {
                               padding(2.unit) {
                                 uiText("Move the mouse into this popup to see how it extends the hoverable area")
                               }
                             }
                           }
                         },
                         disableClickOnAnchor = false) {
        hoverable(hoverableMerger) {
          val color = if (hoveredState.read()) Color.green else Color.red
          box(color) {
            padding(2.unit) {
              vbox {
                uiText("Hover me to show a popup")
                gap(2.unit)
                uiText("Entered ${enteredCountState.read()} times")
                uiText("Left ${leftCountState.read()} times")
              }
            }
          }
        }
      }
    }
  }
}