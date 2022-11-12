package noria.ui.components.examples

import noria.ReadScope.Companion.read
import noria.model.dimensions.*
import noria.remember
import noria.scope
import noria.state
import noria.ui.components.*
import noria.ui.components.gallery.gallery
import noria.ui.components.list.defaultListState
import noria.ui.core.animation.*
import noria.ui.core.layout
import noria.ui.core.render
import noria.ui.core.toPaint
import noria.ui.text.uiText
import org.jetbrains.skia.Path
import org.jetbrains.skia.Matrix33

fun animationExamples() = gallery("Animations", "Animations.kt") {
  example("Basic Infinite Animation") {
    val flag = state { true }
    val color = if (flag.read())
      infiniteValueAnimation(
        Color.white, Color.red, Color.VectorConverter, infiniteRepeatable(tween(2000, delayMillis = 500), RepeatMode.Reverse)
      )
    else
      state { Color.red }
    val text = "Animation is ${if (flag.read()) "on" else "off"}"
    vbox {
      exampleBox(color.read())
      gap(height = 2.unit)
      button(text) { flag.update { !it } }
    }
  }

  example("Explore Animation Easing") {
    val flag = state { true }
    val easings = EasingInformation
    val numberOfColumns = 3
    val widths = easings.withIndex().map { (offset, it) ->
      scope(offset) {
        animateFloatAsState(if (flag.read()) 5.0f else 50.0f, tween(1000, easing = it.second))
      }
    }

    vbox(align = Align.Center) {
      uiText("Boxes is on the ${if (flag.read()) "left" else "right"}")
      gap(height = 2.unit)
      button("Move to ${if (flag.read()) "right" else "left"}", onClick = { flag.update { !it } })
      gap(height = 5.unit)

      for ((rowOffset, row) in (widths zip easings.map { it.first }).chunked(numberOfColumns).withIndex()) {
        scope(rowOffset) {
          hbox(align = Align.Center) {
            gap(width = 5.unit)
            for ((offset, info) in row.withIndex()) {
              scope(offset) {
                constrain({ it.copy(minWidth = 65.unit, maxWidth = 65.unit) }) {
                  vbox {
                    uiText(info.second)
                    hbox {
                      gap(width = info.first.read().unit)
                      exampleBox(Color.red, size = 10.unit)
                    }
                  }
                }
              }
              gap(width = 2.unit)
            }
          }
        }
      }
    }
  }

  example("Explore Easing Curves") {
    vbox {
      val cubicEasings = remember {
        EasingInformation.mapNotNull { info -> (info.second as? CubicBezierEasing)?.let { info.first to it } }
      }
      val easingState = defaultListState(cubicEasings.first())
      dropDownList(cubicEasings, listState = easingState, presentationFn = { it.first })

      gap(height = 6.unit)

      stretchHorizontally {
        val size = Size(40.unit, 20.unit)
        center {
          constrain(preferredWidth = size.width, preferredHeight = size.height) {
            stack {
              layout { cs ->
                val easing = (easingState.read().cursorKey as? Pair<*, *>)?.second as? CubicBezierEasing ?: return@layout size
                val progress = animateValueOnce(
                  easing,
                  initialValue = 0f,
                  targetValue = 1f,
                  typeConverter = Float.VectorConverter,
                  animationSpec = infiniteRepeatable(
                    tween(durationMillis = 1500, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                  )
                ).read()
                val radius = 1.unit
                render(Rect(Point.ZERO, size)) {
                  val physical = Size(cs.minWidth, cs.minHeight).toPhysical()
                  Path()
                    .moveTo(0f, 1f)
                    .cubicTo(easing.a, 1 - easing.b, easing.c, 1 - easing.d, 1f, 0f)
                    .transform(Matrix33.makeScale(physical.width.value, physical.height.value))
                    .use { path ->
                      Color.black.toPaint().use { paint ->
                        paint.strokeWidth = 2f
                        paint.setStroke(true)
                        canvas.drawPath(path, paint)
                      }
                    }

                  Path()
                    .addCircle(0f, physical.height.value, radius.toPhysical().value)
                    .transform(Matrix33.makeTranslate(physical.width.value * progress, -easing.transform(progress) * physical.height.value))
                    .use { circle ->
                      Color.red.toPaint().use { paint ->
                        canvas.drawPath(circle, paint)
                      }
                    }
                }
              }

              alignToTheLeft {
                box(backgroundColor = Color.gray.applyAlpha(0.5f)) {
                  gap(width = 1.px, height = Dimension.INFINITY)
                }
              }

              alignToTheBottom {
                box(backgroundColor = Color.gray.applyAlpha(0.5f)) {
                  gap(width = Dimension.INFINITY, height = 1.px)
                }
              }
            }
          }
        }
      }
    }
  }

  example("Basic Animation As State") {
    val flag = state { true }
    val width = animateFloatAsState(if (flag.read()) 5.0f else 50.0f, spring(Spring.DampingRatioMediumBouncy))
    val color = animateColorAsState(if (flag.read()) Color.black else Color.red, tween(1000))
    vbox {
      uiText("Box is on the ${if (flag.read()) "left" else "right"}")
      gap(height = 2.unit)
      button("Move to ${if (flag.read()) "right" else "left"}", onClick = { flag.update { !it } })
      gap(height = 5.unit)
      hbox {
        gap(width = width.read().unit)
        exampleBox(color.read())
      }
    }
  }

  example("Complex Animation") {
    val isPressed = state { false }
    val background = animateColorAsState(if (isPressed.read()) Color.red else Color.white, tween(1000, easing = FastOutSlowInEasing))
    val radius = animateDimensionAsState(if (isPressed.read()) 10.unit else 2.unit, tween(700, delayMillis = 300))
    val alpha = animateFloatAsState(if (isPressed.read()) 0.0f else 1.0f, tween(1000))
    val horizontalPadding = animateDimensionAsState(if (isPressed.read()) 10.unit else 30.unit, tween(1000))
    val style = buttonStyle().let { style ->
      val newColors = style.colors.copy(
        background = background.read(),
        backgroundHovered = background.read(),
        foreground = style.colors.foreground.copy(alpha = alpha.read()),
        foregroundHovered = style.colors.foregroundHovered.copy(alpha = alpha.read()),
      )
      val newDecorators = style.decorations.copy(
        radius = BorderRadius.uniform(radius.read()),
        horizontalPadding = horizontalPadding.read(),
        verticalPadding = 10.unit
      )
      style.copy(decorations = newDecorators, colors = newColors)
    }

    hbox(align = Align.Center) {
      gap(width = 50.unit - horizontalPadding.read()) // :(
      button(style = style) { isPressed.update { !it } }
    }
  }
}
