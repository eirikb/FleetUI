package noria.ui.components.examples

import noria.model.dimensions.Color
import noria.model.dimensions.px
import noria.state
import noria.ui.components.*
import noria.ui.components.gallery.gallery
import noria.ui.components.modifiers.Modifier
import noria.ui.components.modifiers.clickable
import noria.ui.components.modifiers.fill
import noria.ui.components.modifiers.padding
import noria.ui.core.UIContext
import noria.ui.text.uiText

fun popupExamples() = gallery("Popup", "Popup.kt") {
  example("Default Positioning") {
    withPopup(
      popupBuilder = {
        uiText(text = "I am Popup",
               color = Color.white,
               modifier = Modifier
                 .fill(Color.blue)
                 .padding(vertical = 5.px, horizontal = 2.px))
      },
    ) {
      uiText(text = "Popup Anchor",
             color = Color.white,
             modifier = Modifier
               .fill(Color.red)
               .padding(vertical = 30.px, horizontal = 15.px))
    }
  }

  /**
   * Position popups using AnchorJoint and PopupJoint
   */
  example("Custom Positioning") {
    fun UIContext.positionedPopup(place: PopupPlace, direction: PopupDirection) {
      withPopup(
        popupBuilder = {
          uiText(text = "I am Popup",
                 color = Color.white,
                 modifier = Modifier
                   .fill(Color.blue)
                   .padding(vertical = 5.px, horizontal = 2.px))
        },
        position = PopupPosition(place = place, direction = direction),
      ) {
        uiText(text = "Popup Anchor",
               color = Color.white,
               modifier = Modifier
                 .fill(Color.red)
                 .padding(vertical = 30.px, horizontal = 15.px))
      }
    }

    vbox {

      gap(height = 60.px)
      val position = state { PopupPlace.Left }
      val direction = if (position.read() == PopupPlace.Bottom || position.read() == PopupPlace.Top) {
        state { PopupDirection.LeftToRight }
      }
      else {
        state { PopupDirection.TopToBottom }
      }
      hbox {
        dropDownList(PopupPlace.values().map { it.toString() }) { change ->
          position.update { PopupPlace.valueOf(change) }
        }

        gap(width = 60.px)

        if (position.read() == PopupPlace.Bottom || position.read() == PopupPlace.Top) {
          dropDownList(listOf(PopupDirection.LeftToRight.name, PopupDirection.RightToLeft.name)) { change ->
            direction.update { PopupDirection.valueOf(change) }
          }
        }
        else {
          dropDownList(listOf(PopupDirection.TopToBottom.name, PopupDirection.BottomToTop.name)) { change ->
            direction.update { PopupDirection.valueOf(change) }
          }
        }
      }

      stretchHorizontally {
        center {
          padding(vertical = 20.unit) {
            positionedPopup(
              place = position.read(),
              direction = direction.read()
            )
          }
        }
      }
    }
  }

  example("Hiding Popup") {
    val popupIsShown = state { false }
    withCloseablePopup(
      shownState = popupIsShown,
      popupBuilder = {
        uiText(text = "I am Popup",
               color = Color.white,
               modifier = Modifier
                 .fill(Color.blue)
                 .padding(vertical = 5.px, horizontal = 2.px))
      },
    ) {
      uiText(
        text = "Click Me!",
        color = Color.white,
        modifier = Modifier
          .clickable(onClick = {
            popupIsShown.update { !it }
          })
          .fill(Color.red)
          .padding(vertical = 30.px, horizontal = 15.px)
      )
    }
  }

  example("Popup Button") {
    stretchHorizontally {
      center {
        padding(vertical = 10.unit) {
          popupButton(
            popupBuilder = {
              uiText(text = "I am Popup",
                     color = Color.white,
                     modifier = Modifier
                       .fill(Color.blue)
                       .padding(vertical = 5.px, horizontal = 2.px))
            },
          ) { colors, _ ->
            padding(4.unit) {
              uiText("Click Me!", colors.foreground)
            }
          }
        }
      }
    }
  }
}
