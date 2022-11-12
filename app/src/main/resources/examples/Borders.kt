package noria.ui.components.examples

import noria.model.dimensions.Color
import noria.model.dimensions.px
import noria.ui.components.*
import noria.ui.components.gallery.gallery
import noria.ui.components.modifiers.Modifier
import noria.ui.components.modifiers.border
import noria.ui.components.modifiers.fill
import noria.ui.components.modifiers.withModifier
import noria.ui.text.uiText

fun borderExamples() = gallery("Borders", "Borders.kt") {
  example("Simple Border") {
    withModifier(
      Modifier
        .border(color = Color.green,
                borderThickness = SeparatorThickness.Thick)
    ) {
      gap(width = 80.unit, height = 40.unit)
    }
  }

  example("Round Border") {
    withModifier(
      Modifier
        .border(color = Color.green,
                borderThickness = SeparatorThickness.Thick,
                borderRadius = 4.px)
    ) {
      gap(width = 80.unit, height = 40.unit)
    }
  }

  example("Partial Border") {
    withModifier(
      Modifier
        .border(color = Color.green,
                borderThickness = SeparatorThickness.Thick,
                borderRadius = 4.px,
                sideFlags = BORDER_LEFT + BORDER_BOTTOM)
        .fill(fillColor = Color.gray)
    ) {
      gap(width = 80.unit, height = 40.unit)
    }
  }

  example("Partial Round Border") {
    withModifier(
      Modifier
        .border(
          color = Color.green,
          borderThickness = SeparatorThickness.Thick,
          borderRadius = 10.px,
          borderRadiusTopRight = 0.px,
          borderRadiusBottomRight = 0.px,
        )
    ) {
      gap(width = 80.unit, height = 40.unit)
    }
  }


  example("Circle") {
    align {
      circle(5.unit, Color.css("25a0f6")) {
        center {
          uiText("M", color = Color.white)
        }
      }
    }
  }
}
