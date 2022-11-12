package noria.ui.components.examples

import noria.StateCell
import noria.model.dimensions.Color
import noria.model.dimensions.Dimension
import noria.state
import noria.ui.components.*
import noria.ui.components.gallery.gallery
import noria.ui.components.modifiers.Modifier
import noria.ui.components.modifiers.fill
import noria.ui.components.split.*
import noria.ui.text.uiText
import noria.ui.theme.ThemeKeys

fun splitExamples() = gallery("Split", "Split.kt") {
  example("Part Preserving Split. Second") {
    vbox {
      val firstVisibility = state { true }
      val secondVisibility = state { true }

      hbox {
        fun action(flag: StateCell<Boolean>) = if (flag.read()) "Hide" else "Show"

        button("${action(firstVisibility)} first") { firstVisibility.update { !it } }

        gap(width = 4.unit)

        button("${action(secondVisibility)} second") { secondVisibility.update { !it } }
      }

      gap(height = 4.unit)

      constrain(preferredHeight = 100.unit) {
        splitView(SplitDirection.HORIZONTAL,
                  stretchPolicy = StretchPolicy.PartPreserve(
                    SplitPart.SECOND,
                    initialPartLength = 50.unit,
                    minPartLength = 50.unit)) {
          first(firstVisibility) {
            vbox(modifier = Modifier.fill(Color.blue)) {
              uiText("${"Long ".repeat(10)}Text")
              gap(width = Dimension.INFINITY, height = Dimension.INFINITY)
            }
          }
          second(secondVisibility) {
            vbox(modifier = Modifier.fill(Color.red)) {
              uiText("${"Long ".repeat(10)}Text")
              gap(width = Dimension.INFINITY, height = Dimension.INFINITY)
            }
          }
        }
      }
    }
  }
  example("Animated Part Preserving Split. First") {
    vbox {
      val firstVisibility = state { true }
      val secondVisibility = state { true }

      hbox {
        fun action(flag: StateCell<Boolean>) = if (flag.read()) "Hide" else "Show"

        button("${action(firstVisibility)} first") { firstVisibility.update { !it } }

        gap(width = 4.unit)

        button("${action(secondVisibility)} second") { secondVisibility.update { !it } }
      }

      gap(height = 4.unit)

      constrain(preferredHeight = 100.unit) {
        splitView(SplitDirection.HORIZONTAL,
                  stretchPolicy = StretchPolicy.PartPreserve(
                    SplitPart.FIRST,
                    initialPartLength = 50.unit,
                    minPartLength = 50.unit,
                    animation = SplitAnimation()
                  )) {
          first(firstVisibility) {
            vbox(modifier = Modifier.fill(Color.gray)) {
              uiText("${"Long ".repeat(10)}Text")
              gap(width = Dimension.INFINITY, height = Dimension.INFINITY)
            }
          }
          second(secondVisibility) {
            box(Color.red) {
              gap(width = Dimension.INFINITY, height = Dimension.INFINITY)
            }
          }
        }
      }
    }
  }
  example("Ratio Split") {
    vbox {
      val preferredWidth = state { 50.unit }
      val isFirstVisible = state { true }
      val isSecondVisible = state { true }
      val difference = 10.unit

      hbox {
        fun action(flag: StateCell<Boolean>) = if (flag.read()) "Hide" else "Show"

        button("${action(isFirstVisible)} first") { isFirstVisible.update { !it } }

        gap(width = 4.unit)

        button("${action(isSecondVisible)} second") { isSecondVisible.update { !it } }
      }

      gap(height = 4.unit)

      hbox {
        val diff = (difference.value / theme[ThemeKeys.GridUnit].value / scale.value).toInt()
        button("width - ${diff}") { preferredWidth.update { it - difference } }

        gap(width = 4.unit)

        button("width + ${diff}") { preferredWidth.update { it + difference } }
      }
      gap(height = 4.unit)
      constrain(preferredWidth = preferredWidth.read(), preferredHeight = 50.unit) {
        splitView(SplitDirection.HORIZONTAL,
                  stretchPolicy = StretchPolicy.Ratio(initialRatio = 0.5f)) {
          first(isFirstVisible) {
            box(Color.blue) {
              gap(width = Dimension.INFINITY, height = Dimension.INFINITY)
            }
          }
          second(isSecondVisible) {
            box(Color.red) {
              gap(width = Dimension.INFINITY, height = Dimension.INFINITY)
            }
          }
        }
      }
    }
  }
  example("Part Preserving Split") {
    vbox {
      uiText("First Preserve Part")
      constrain(preferredHeight = 100.unit) {
        splitView(SplitDirection.HORIZONTAL,
                  stretchPolicy = StretchPolicy.PartPreserve(SplitPart.FIRST, initialPartLength = 20.unit, minPartLength = 20.unit)) {
          first {
            box(Color.blue) {
              gap(width = Dimension.INFINITY, height = Dimension.INFINITY)
            }
          }
          second {
            box(Color.red) {
              gap(width = Dimension.INFINITY, height = Dimension.INFINITY)
            }
          }
        }
      }
      uiText("Second Preserve Part")
      constrain(preferredHeight = 100.unit) {
        splitView(SplitDirection.HORIZONTAL,
                  stretchPolicy = StretchPolicy.PartPreserve(SplitPart.SECOND, initialPartLength = 20.unit, minPartLength = 20.unit)) {
          first {
            box(Color.blue) {
              gap(width = Dimension.INFINITY, height = Dimension.INFINITY)
            }
          }
          second {
            box(Color.red) {
              gap(width = Dimension.INFINITY, height = Dimension.INFINITY)
            }
          }
        }
      }
      uiText("First Preserve Part")
      constrain(preferredHeight = 100.unit) {
        splitView(SplitDirection.VERTICAL,
                  stretchPolicy = StretchPolicy.PartPreserve(SplitPart.FIRST, initialPartLength = 20.unit, minPartLength = 20.unit)) {
          first {
            box(Color.blue) {
              gap(width = Dimension.INFINITY, height = Dimension.INFINITY)
            }
          }
          second {
            box(Color.red) {
              gap(width = Dimension.INFINITY, height = Dimension.INFINITY)
            }
          }
        }
      }
      uiText("Second Preserve Part")
      constrain(preferredHeight = 100.unit) {
        splitView(SplitDirection.VERTICAL,
                  stretchPolicy = StretchPolicy.PartPreserve(SplitPart.SECOND, initialPartLength = 20.unit, minPartLength = 20.unit)) {
          first {
            box(Color.blue) {
              gap(width = Dimension.INFINITY, height = Dimension.INFINITY)
            }
          }
          second {
            box(Color.red) {
              gap(width = Dimension.INFINITY, height = Dimension.INFINITY)
            }
          }
        }
      }
    }
  }
}