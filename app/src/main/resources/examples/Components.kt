package noria.ui.components.examples

import fleet.util.enumSetOf
import noria.model.dimensions.Color
import noria.model.dimensions.Point
import noria.model.dimensions.px
import noria.state
import noria.ui.components.*
import noria.ui.components.gallery.gallery
import noria.ui.components.modifiers.*
import noria.ui.text.uiText
import noria.ui.theme.ThemeKeys

fun componentsExamples() = gallery("Components", "Components.kt") {
  example("DropDownList") {
    val items = listOf("foo", "bar", "baz", "qux")
    dropDownList(items) { println(it) }
  }

  example("DropDownList + Popup") {
    constrain(preferredHeight = 20.unit) {
      withPopup(
        popupBuilder = {
          withPopup(
            popupBuilder = {
              withPopup(
                popupBuilder = {
                  box(Color.gray) {
                    uiText("Fourth Popup")
                  }
                },
                position = PopupPosition(Point(0.px, 20.px)),
                anchorBuilder = {
                  box(Color.blue) {
                    uiText("Third Popup")
                  }
                }
              )
            },
            position = PopupPosition(Point(0.px, 20.px)),
            anchorBuilder = {
              box(Color.green) {
                uiText("Second Popup")
              }
            }
          )
        },
        position = PopupPosition(Point(0.px, 20.px)),
        anchorBuilder = {
          box(Color.red) {
            uiText("First Popup")
          }
        }
      )
    }
  }

  example("RadioGroup") {
    radioGroup(listOf("foo", "bar", "baz"),
               selectedOption = "bar",
               onSelectedChange = { println("selected: $it") })
  }

  example("Toggle") {
    val enabled = state { false }
    hoverable { hovered ->
      clickable(onClick = { enabled.update { enabled -> !enabled } }) {
        toggle(enabled.read(), hovered)
      }
    }
  }

  example("Resizable Popup") {
    vbox {
      val modifier = Modifier
        .box(border = Border(theme[ThemeKeys.Border]))
        .fill(fillColor = theme[ThemeKeys.PopupFill])
      withModifier(modifier) {
        resizable(resizeBorder = enumSetOf(ResizeBorder.Bottom, ResizeBorder.Right)) {
          stretch {
            center {
              uiText("Hello!")
            }
          }
        }
      }
    }
  }
}
