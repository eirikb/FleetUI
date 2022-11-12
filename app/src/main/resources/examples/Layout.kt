package noria.ui.components.examples

import fleet.util.enumSetOf
import noria.model.dimensions.Color
import noria.model.dimensions.Dimension
import noria.model.dimensions.Size
import noria.state
import noria.ui.components.*
import noria.ui.components.gallery.Gallery
import noria.ui.components.gallery.gallery
import noria.ui.components.list.ListState
import noria.ui.components.list.defaultListState
import noria.ui.components.modifiers.*
import noria.ui.core.UIContext
import noria.ui.core.layout
import noria.ui.text.uiText
import noria.ui.theme.ThemeKeys

fun layoutExamples(): Gallery = gallery("Layout", "Layout.kt") {
  /**
   * Constraints define the ranges for width and height that a component may choose from during layout.
   *
   * If the minimum and maximum size are the same, then the component must lay itself out at the given size, even if it would like to use
   * a different size. This situation is called "tight constraints".
   *
   * This box is stretched horizontally even though it would like to only take up 10 units of width.
   */
  example("Tight Constraints") {
    exampleBox(Color.green, 10.unit) // The explicit size only applies to the height. It has no effect on the width due to tight constraints
  }

  example("Loosening Constraints (Single Child)") {
    vbox(Align.Stretch) {
      uiText("align")
      gap(height = 1.unit)
      constrain(preferredHeight = 20.unit) {
        box(border = Border(Color.gray)) {
          align {
            exampleBox(Color.green, 10.unit)
          }
        }
      }
      gap(height = 2.unit)

      uiText("alignToTheRight")
      gap(height = 1.unit)
      constrain(preferredHeight = 20.unit) {
        box(border = Border(Color.gray)) {
          alignToTheRight {
            exampleBox(Color.green, 10.unit)
          }
        }
      }
      gap(height = 2.unit)

      uiText("alignToTheBottom")
      gap(height = 1.unit)
      constrain(preferredHeight = 20.unit) {
        box(border = Border(Color.gray)) {
          alignToTheBottom {
            exampleBox(Color.green, 10.unit)
          }
        }
      }
      gap(height = 2.unit)

      uiText("centerHorizontally")
      gap(height = 1.unit)
      constrain(preferredHeight = 20.unit) {
        box(border = Border(Color.gray)) {
          centerHorizontally {
            exampleBox(Color.green, 10.unit)
          }
        }
      }
      gap(height = 2.unit)

      gap(height = 2.unit)
      uiText("centerVertically")
      gap(height = 1.unit)
      constrain(preferredHeight = 20.unit) {
        box(border = Border(Color.gray)) {
          centerVertically {
            exampleBox(Color.green, 10.unit)
          }
        }
      }
      gap(height = 2.unit)

      gap(height = 2.unit)
      uiText("center")
      gap(height = 1.unit)
      constrain(preferredHeight = 20.unit) {
        box(border = Border(Color.gray)) {
          center {
            exampleBox(Color.green, 10.unit)
          }
        }
      }
      gap(height = 2.unit)

      uiText("align with custom arguments")
      gap(height = 1.unit)
      constrain(preferredHeight = 20.unit) {
        box(border = Border(Color.gray)) {
          align(alignX = 0.7f, alignY = 0.2f, childAlignX = 1.0f, childAlignY = 0f) {
            exampleBox(Color.green, 10.unit)
          }
        }
      }
      gap(height = 2.unit)

      uiText("(and more variations - see Align.kt)")
    }
  }

  /**
   * `vbox` is used to stack components vertically
   */
  example("VBox") {
    vbox {
      exampleBox(Color.red)
      exampleBox(Color.green)
      exampleBox(Color.blue)
    }
  }

  /**
   * `hbox` is used to stack components horizontally
   */
  example("HBox") {
    hbox {
      exampleBox(Color.red)
      exampleBox(Color.green)
      exampleBox(Color.blue)
    }
  }

  /**
   * You can pass `align` parameter to align hbox children
   */
  example("HBox Alignment") {
    vbox {
      uiText("Interactive HBox Alignment")
      gap(height = 1.unit)

      vbox {
        val defaultAlignment = Align.Top
        val alignments = listOf(Align.Top, Align.Center, Align.Bottom)
        val listState = defaultListState(defaultAlignment)
        dropDownList(
          items = alignments,
          listState = listState,
          presentationFn = { "$it${if (it == defaultAlignment) " (Default)" else ""}" }
        )
        val alignment = listState.read().cursorKey as Align? ?: defaultAlignment
        stretchHorizontally {
          center {
            padding(4.unit) {
              hbox(align = alignment) {
                exampleBox(Color.red, 4.unit)
                exampleBox(Color.green, 8.unit)
                exampleBox(Color.blue, 12.unit)
              }
            }
          }
        }
      }

      uiText("Align.Top (Default)")
      gap(height = 1.unit)
      hbox {
        exampleBox(Color.red, 4.unit)
        exampleBox(Color.green, 8.unit)
        exampleBox(Color.blue, 12.unit)
      }

      gap(height = 4.unit)

      uiText("Align.Center")
      gap(height = 1.unit)
      hbox(align = Align.Center) {
        exampleBox(Color.red, 4.unit)
        exampleBox(Color.green, 8.unit)
        exampleBox(Color.blue, 12.unit)
      }

      gap(height = 4.unit)

      uiText("Align.Bottom")
      gap(height = 1.unit)
      hbox(align = Align.Bottom) {
        exampleBox(Color.red, 4.unit)
        exampleBox(Color.green, 8.unit)
        exampleBox(Color.blue, 12.unit)
      }

    }
  }

  /**
   * You can pass `align` parameter to align vbox children
   */
  example("VBox Alignment") {
    vbox {
      uiText("Interactive VBox Alignment")
      gap(height = 1.unit)

      vbox {
        val defaultAlignment = Align.Left
        val alignments = listOf(Align.Left, Align.Center, Align.Right)
        val listState = defaultListState(defaultAlignment)
        dropDownList(
          items = alignments,
          listState = listState,
          presentationFn = { "$it${if (it == defaultAlignment) " (Default)" else ""}" }
        )
        val alignment = listState.read().cursorKey as Align? ?: defaultAlignment
        stretchHorizontally {
          center {
            padding(4.unit) {
              vbox(align = alignment) {
                exampleBox(Color.red, 4.unit)
                exampleBox(Color.green, 8.unit)
                exampleBox(Color.blue, 12.unit)
              }
            }
          }
        }
      }

      uiText("Align.Left (Default)")
      gap(height = 1.unit)
      vbox {
        exampleBox(Color.red, 4.unit)
        exampleBox(Color.green, 8.unit)
        exampleBox(Color.blue, 12.unit)
      }

      gap(height = 4.unit)

      uiText("Align.Center")
      gap(height = 1.unit)
      vbox(align = Align.Center) {
        exampleBox(Color.red, 4.unit)
        exampleBox(Color.green, 8.unit)
        exampleBox(Color.blue, 12.unit)
      }

      gap(height = 4.unit)

      uiText("Align.Right")
      gap(height = 1.unit)
      vbox(align = Align.Right) {
        exampleBox(Color.red, 4.unit)
        exampleBox(Color.green, 8.unit)
        exampleBox(Color.blue, 12.unit)
      }
    }
  }

  /**
   * `vbox` and `hbox` loosen the constraints on their children: They lay them out with `minHeight` or `minWidth` of zero, respectively
   */
  example("Loosening Constraints (Multiple Children)") {
    vbox(Align.Stretch) {
      constrain(preferredHeight = 20.unit) {
        box(Color.gray) {
          hbox {
            exampleBox(Color.red)
            exampleBox(Color.green)
            exampleBox(Color.blue)
          }
        }
      }

      gap(height = 2.unit)
      constrain(preferredHeight = 50.unit) {
        box(Color.gray) {
          vbox {
            exampleBox(Color.red)
            exampleBox(Color.green)
            exampleBox(Color.blue)
          }
        }
      }
    }
  }

  example("Stack") {
    align {
      stack {
        exampleBox(Color.red, size = 14.unit)

        uiText("Some Content")

        padding(left = 4.unit, top = 4.unit) {
          exampleBox(Color.green)
        }

        padding(left = 8.unit, top = 8.unit) {
          exampleBox(Color.blue)
        }
      }
    }
  }

  /**
   * Use the `flexible` function to create items which will dynamically grow or shrink to fulfill the constraints given to the `hbox` or
   * `vbox`. `flexible` items will grow when the minimum constraints would otherwise not be satisfied, and they will shrink when the maximum
   * constraints would be exceeded.
   * When there is more than a single `flexible` child, the `growWeight` and `shrinkWeight` determine how much space is given or taken from
   * each `flexible` child.
   *
   * The red box and the green one have a weight ratio of 1:10 for both growing and shrinking, and both of them have an intrinsic width of
   * 50 units. That is why, at exactly 100 units of total width, both boxes use exactly those 50 units. Above 100 units of total width, the
   * green box grows 10 times as fast as the red one. It will also shrink 10 times as fast as the red box below 100 units of total width.
   *
   * Resize the box to change the total width
   */
  example("Uniform Growing & Shrinking") {
    val totalSizeState = state<Pair<Dimension?, Dimension?>> { 100.unit to 10.unit }
    vbox {
      uiText("Total width: ${totalSizeState.read().first!! / 1.unit}")
      withModifier(Modifier.box(theme[ThemeKeys.PopupFill], border = Border(theme[ThemeKeys.Border]))) {
        resizable(resizeBorder = enumSetOf(ResizeBorder.Right), sizeCell = totalSizeState) {
          hbox {
            flexible(1f) {
              decorate(backgroundColor = Color.red.applyAlpha(.5f)) {
                gap(50.unit, 10.unit)
              }
            }
            flexible(10f) {
              decorate(backgroundColor = Color.green.applyAlpha(.5f)) {
                gap(50.unit, 10.unit)
              }
            }
          }
        }
      }
    }
  }

  /**
   * You can use the `flexible` function overload with separate `growWeight` and `shrinkWeight` parameters to create items with different
   * weights for growing and shrinking. This can be useful if you want to have a `hbox` or `vbox` which will shrink its children if
   * there is little space but which will not make them grow when its min constraints aren't fulfilled.
   *
   * The red box and the green one have a *shrinking* weight ratio of 2:1, 0 `growWeight` and both of them have an intrinsic width of
   * 50 units. That is why, at exactly 100 units of total width, both boxes take up 50 units of width. Above 100 units of total width, none
   * of the two will grow. Below 100 units of total width, the red box will shrink twice as fast as the green one.
   *
   * Resize the box to change the total width
   */
  example("Shrinking Only") {
    val totalSizeState = state<Pair<Dimension?, Dimension?>> { 100.unit to 10.unit }
    vbox {
      uiText("Total width: ${totalSizeState.read().first!! / 1.unit}")
      withModifier(Modifier.box(theme[ThemeKeys.PopupFill], border = Border(theme[ThemeKeys.Border]))) {
        resizable(resizeBorder = enumSetOf(ResizeBorder.Right), sizeCell = totalSizeState) {
          hbox {
            flexible(0f, 2f) {
              decorate(backgroundColor = Color.red.applyAlpha(.5f)) {
                gap(50.unit, 10.unit)
              }
            }
            flexible(0f, 1f) {
              decorate(backgroundColor = Color.green.applyAlpha(.5f)) {
                gap(50.unit, 10.unit)
              }
            }
          }
        }
      }
    }
  }

  // todo[unterhofer] What is this supposed to demonstrate? I don't see an order in here
  example("Shrinking Order") {
    val totalSizeState = state<Pair<Dimension?, Dimension?>> { 100.unit to 10.unit }
    vbox {
      uiText("Total width: ${totalSizeState.read().first!! / 1.unit}")
      withModifier(Modifier.box(theme[ThemeKeys.PopupFill], border = Border(theme[ThemeKeys.Border]))) {
        resizable(resizeBorder = enumSetOf(ResizeBorder.Right), sizeCell = totalSizeState) {
          hbox {
            decorate(backgroundColor = Color.red.applyAlpha(.5f)) {
              gap(30.unit, 10.unit)
            }

            flexible {
              decorate(backgroundColor = Color.blue.applyAlpha(.5f)) {
                gap(40.unit, 10.unit)
              }
            }

            decorate(backgroundColor = Color.red.applyAlpha(.5f)) {
              gap(30.unit, 10.unit)
            }
          }
        }
      }
    }
  }
}


fun UIContext.exampleBox(color: Color, size: Dimension = 10.unit) {
  decorate(backgroundColor = color.applyAlpha(.5f)) {
    layout { cs ->
      Size(size, size).coerceIn(cs)
    }
  }
}