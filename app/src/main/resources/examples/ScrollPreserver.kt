package noria.ui.components.examples

import fleet.util.UID
import fleet.util.letIf
import noria.model.dimensions.Color
import noria.scope
import noria.state
import noria.ui.components.*
import noria.ui.components.gallery.gallery
import noria.ui.components.modifiers.*
import noria.ui.core.*
import noria.ui.text.uiText

fun scrollPreserverExamples() = gallery("Scroll Preserver", "ScrollPreserver.kt") {
  example("Sample") {
    val items = state { generateSequence { UID.random() }.take(10).toList() }
    val selectedItem = state<UID?> { null }

    vbox {

      button(text = "Add Item Above") {
        items.update {
          listOf(UID.random()) + it
        }
      }

      constrain(preferredHeight = 150.unit) {
        val scrollState = state { ScrollState.empty() }
        scroll(scrollState = scrollState, verticalScrollBarVisibility = ScrollBarVisibility.ALWAYS_VISIBLE) {
          scrollAnchorPreserver(scrollState) {
            vbox {
              items.read().forEach { item ->
                scope(item) {

                  val fillColor = Color.blue.copy(alpha = 0.3f)
                  val selected = selectedItem.read()
                  withModifier(
                    Modifier
                      .clickable(onClick = { selectedItem.update { item } })
                      .padding(vertical = 1.unit, horizontal = 2.unit)
                      .letIf(selectedItem.read() == item) { it.fill(fillColor = fillColor) }
                      .constrain(preferredWidth = 50.unit, preferredHeight = 25.unit)
                  ) {

                    layoutRef { ref ->

                      if (selected == item) {
                        bindings[ScrollPreserverAnchorKey]?.set(ScrollAnchor(selected, ref.node!!.origin, bindings[ScaleKey], ref))
                      }

                      center {
                        uiText(item.id)
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }

    }
  }
}