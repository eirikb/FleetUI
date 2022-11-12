package noria.ui.components.examples

import noria.model.Propagate
import noria.model.dimensions.Dimension
import noria.model.dimensions.px
import noria.model.dimensions.times
import noria.remember
import noria.state
import noria.ui.components.*
import noria.ui.components.gallery.Gallery
import noria.ui.components.gallery.gallery
import noria.ui.components.list.*
import noria.ui.components.modifiers.Modifier
import noria.ui.components.modifiers.clickable
import noria.ui.components.modifiers.withModifier
import noria.ui.core.*
import noria.ui.events.inputHandler
import noria.ui.text.uiText
import java.lang.Math.random

fun lazyColumnGallery(): Gallery {
  return gallery("List View", "list2/ListView.kt") {
    example("Height Key Based Lazy Column") {
      constrain(preferredHeight = 500.px, preferredWidth = 500.px) {
        scroll(direction = ScrollDirection.VERTICAL) {
          heightKeyBasedLazyColumn(size = 10000) { i ->
            Row(key = i
            ) {
              focusable(key = i) {
                focusData {
                  inputHandler {
                    println(i)
                    Propagate.CONTINUE
                  }
                }
                val ref = focusNode
                withModifier(Modifier.clickable(onClick = {
                  println("requestFocus $i")
                  ref.requestFocus()
                  Propagate.CONTINUE
                })) {
                  uiText("$i")
                }
              }
            }
          }
        }
      }
    }

    example("Approximating Lazy Column") {
      constrain(preferredHeight = 500.px, preferredWidth = 500.px) {
        scroll(direction = ScrollDirection.VERTICAL) {
          val rowCount = 1000
          val heightMap = remember { HashMap<Int, Dimension>(rowCount) }
          approximatingLazyColumn(count = rowCount) { i ->
            Row(key = i) {
              focusable(key = i) {
                focusData {
                  inputHandler {
                    println(i)
                    Propagate.CONTINUE
                  }
                }
                val ref = focusNode
                withModifier(Modifier.clickable(onClick = {
                  println("requestFocus $i")
                  ref.requestFocus()
                  Propagate.CONTINUE
                })) {
                  val height = heightMap.computeIfAbsent(i) { 10.px + random() * 40.px }
                  constrain(preferredHeight = height) {
                    uiText("$i ($height)")
                  }
                }
              }
            }
          }
        }
      }
    }

    example("List") {
      constrain(preferredHeight = 300.px) {
        listView(listModel(10000, onConfirm = { items -> println("confirmed items: $items"); Propagate.STOP }) { idx ->
          ListItem(idx, idx) { opts ->
            defaultListCell(text = idx.toString(),
                            listItemOpts = opts)
          }
        })
      }
    }

    example("List with SpeedSearch") {
      constrain(preferredHeight = 300.px) {
        listView(listModel(10000, options = ListViewOptions(speedSearchOptions = SpeedSearchOptions.Default()),
                           onConfirm = { items -> println("confirmed items: $items"); Propagate.STOP }) { idx ->
          ListItem(idx,
                   idx,
                   textToMatch = idx.toString()) { opts ->
            defaultListCell(text = idx.toString(),
                            listItemOpts = opts)
          }
        })
      }
    }

    example("List with Custom SpeedSearch") {
      constrain(preferredHeight = 300.px) {
        vbox {
          val state = state { createTextInputData() }

          textInput(textInputModel(state))

          listView(listModel(10000, options = ListViewOptions(speedSearchOptions = SpeedSearchOptions.Custom(state)),
                             onConfirm = { items -> println("confirmed items: $items"); Propagate.STOP }) { idx ->
            ListItem(idx,
                     idx,
                     textToMatch = idx.toString()) { opts ->
              defaultListCell(text = idx.toString(),
                              listItemOpts = opts)
            }
          })
        }
      }
    }


  }
}