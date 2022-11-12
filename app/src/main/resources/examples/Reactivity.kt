package noria.ui.components.examples

import kotlinx.coroutines.delay
import noria.*
import noria.windowManagement.api.ElementState
import noria.windowManagement.extensions.Keys
import noria.windowManagement.extensions.key
import noria.windowManagement.extensions.state
import noria.model.Propagate
import noria.model.dimensions.BorderRadius
import noria.model.dimensions.Color
import noria.model.dimensions.Constraints
import noria.model.dimensions.px
import noria.ui.components.*
import noria.ui.components.gallery.gallery
import noria.ui.components.modifiers.*
import noria.ui.core.*
import noria.ui.events.InputEvent
import noria.ui.events.inputHandler
import noria.ui.text.uiText
import noria.ui.theme.ThemeKeys

private fun UIContext.withCount(count: Int, text: String, builder: UIBuilder) {
  box(border = Border(color = theme[ThemeKeys.Fill]), uniformBorderRadius = 2.unit) {
    padding(2.unit) {
      stack {
        centerVertically {
          builder()
        }

        alignToTheBottom {
          uiText("$text: $count", color = theme[ThemeKeys.DimmedText])
        }
      }
    }
  }
}

private fun UIContext.blinkOnRerender(builder: UIBuilder) {
  val rerender = state { false }
  launchRestart(WILDCARD) {
    delay(200)
    rerender.update { false }
  }

  rerender.update { true }
  stack {
    val size = measure(Constraints.NONE, builder)
    builder()

    boundary {
      if (rerender.read()) {
        decorate(backgroundColor = Color.green.copy(alpha = .2f)) {
          constrain(preferredWidth = size.width,
                    preferredHeight = size.height) {
            gap()
          }
        }
      }
    }
  }
}

fun reactivityExamples() = gallery("Reactivity", "Reactivity.kt") {

  /**
   * Boundary allows you control rerendering of subtrees
   */
  example("Simple Boundary") {
    val counter = state { 42 }
    vbox {

      hbox(align = Align.Center) {
        button("Increment") {
          counter.update { it + 1 }
        }
        gap(width = 2.unit)
        uiText("value: ${counter.read()}")
      }
      gap(width = 2.unit)

      blinkOnRerender {
        padding(vertical = 2.unit) {
          uiText("I will blink when counter updates")
        }
      }

      boundary {
        blinkOnRerender {
          padding(vertical = 2.unit) {
            uiText("And I won't")
          }
        }
      }
    }
  }


  /**
   * This example shows how to use "expr { }" to build incremental computation trees.
   * First column consists of editable inputCells, each one of them returns a thunk.
   * Second and third columns consist of sumCelss, which receive two thunks as input
   * and return a thunk corresponding to its sum. SumCell also shows how many times it
   * was reevaluated. As you can see thunk reevaluates only when its dependencies changes
   */
  example("Incremental Cells", preferredHeight = 600.px) {

    val cellWidth = 20.unit
    val cellHeight = cellWidth

    fun UIContext.inputCell(initialValue: Int): Cell<Int?> {
      val valueThunk: StateCell<Int?> = state { initialValue }
      focusable {
        hbox(align = Align.Center,
             modifier = Modifier
               .padding(vertical = 2.unit, horizontal = 2.unit)
               .constrain(preferredWidth = cellWidth, preferredHeight = cellHeight)
               .box(border = Border(theme[ThemeKeys.Text]), uniformBorderRadius = 2.unit)) {
          flexible {
            textInput(valueThunk.readNonReactive()?.toString() ?: "", onInput = { newContent ->
              valueThunk.update { currentValue -> newContent.toIntOrNull() ?: currentValue }
            })
          }
        }
      }
      return valueThunk
    }

    fun UIContext.sumCell(expr1: Cell<Int?>, expr2: Cell<Int?>): Cell<Int?> {
      val reevalCount = state { 0 }
      val sumThunk = cell {
        reevalCount.update { i -> i + 1 }
        val val1 = expr1.read()
        val val2 = expr2.read()

        if (val1 != null && val2 != null) {
          val1 + val2
        }
        else {
          null
        }
      }

      sideEffect {
        reevalCount.update { 0 }
      }

      padding(vertical = 2.unit, horizontal = 2.unit) {
        constrain(preferredWidth = cellWidth,
                  preferredHeight = cellHeight) {
          box(border = Border(color = theme[ThemeKeys.Text]), borderRadius = BorderRadius.uniform(2.unit)) {
            withCount(reevalCount.read(), "evals") {
              uiText(sumThunk.read().toString())
            }
          }
        }
      }

      return sumThunk
    }

    var e1: Cell<Int?>? = null
    var e2: Cell<Int?>? = null
    var e3: Cell<Int?>? = null
    var e4: Cell<Int?>? = null

    var s1: Cell<Int?>? = null
    var s2: Cell<Int?>? = null

    hbox(align = Align.Center) {
      vbox {

        e1 = inputCell(4)
        e2 = inputCell(8)

        gap(4.unit)

        e3 = inputCell(15)
        e4 = inputCell(16)
      }

      vbox {
        hbox(align = Align.Center) {
          uiText("sum=")
          s1 = sumCell(e1!!, e2!!)
        }

        gap(height = cellHeight + 4.unit)

        hbox(align = Align.Center) {
          uiText("sum=")
          s2 = sumCell(e3!!, e4!!)
        }
      }

      vbox {
        hbox(align = Align.Center) {
          uiText("sum=")
          sumCell(s1!!, s2!!)
        }
      }
    }
  }

  /**
   * Example of how to use the keyboard to move a box from left to right
   */
  example("Moving Box with Keyboard", preferredHeight = 600.px) {
    val speed = state { 0 }

    val padding = state { 0 }
    launchOnce {
      while (true) {
        padding.update {
          it + speed.readNonReactive()
        }
        delay(16)
      }
    }

    focusable {
      val ref = focusNode
      clickable(onClick = {
        ref.requestFocus()
      }) {
        withModifier(
          Modifier
            .border(color = Color.green,
                    borderThickness = SeparatorThickness.Custom(2.px))
        ) {
          padding(5.unit) {
            vbox {
              uiText("Use left and right keys to move the box")
              scroll(direction = ScrollDirection.BOTH) {
                padding(left = padding.read().px) {
                  exampleBox(Color.red, 4.unit)
                }
              }
            }
          }
        }
        focusData {
          inputHandler { e ->
            if (e is InputEvent.RawKeyboardInput) {
              val keyboardInputEvent = e.keyboardInputEvent
              val key = keyboardInputEvent.key
              when (keyboardInputEvent.state) {
                ElementState.Pressed -> {
                  speed.update { currentValue ->
                    when (key) {
                      Keys.Right -> 1
                      Keys.Left -> -1
                      else -> {
                        currentValue
                      }
                    }
                  }
                }
                ElementState.Released -> {
                  speed.update { currentValue ->
                    when (key) {
                      Keys.Right -> 0
                      Keys.Left -> 0
                      else -> {
                        currentValue
                      }
                    }
                  }
                }
              }
            }
            Propagate.CONTINUE
          }
        }
      }
    }
  }
}