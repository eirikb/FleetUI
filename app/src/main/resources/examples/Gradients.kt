package noria.ui.components.examples

import noria.model.dimensions.Color
import noria.model.dimensions.Point
import noria.ui.components.*
import noria.ui.components.gallery.gallery
import noria.ui.components.modifiers.Modifier
import noria.ui.components.modifiers.withModifier

fun gradientExamples() = gallery("Gradients", "Gradients.kt") {
  val colors = (0..7).map {
    val part = it / 7f
    GradientColor(part, Color.fromHSV(part, 0.99f, 0.99f))
  }.toTypedArray()
  example("Linear") {
    vbox {
      withModifier(Modifier.gradient(GradientType.Horizontal,
                                     *colors)) {
        gap(width = 80.unit, height = 40.unit)
      }
      withModifier(Modifier.gradient(GradientType.LinearGradient(startPoint = Point.ZERO,
                                                                 endPoint = Point(3.unit, 7.unit)),
                                     *colors)) {
        gap(width = 80.unit, height = 40.unit)
      }
    }
  }
  example("Radial") {
    vbox {
      withModifier(Modifier.gradient(GradientType.RadialGradient(center = Point(40.unit, 20.unit),
                                                                 radius = 13.unit,
                                                                 extendMode = GradientExtendMode.CLAMP),
                                     *colors)) {
        gap(width = 80.unit, height = 40.unit)
      }
    }
  }
}
