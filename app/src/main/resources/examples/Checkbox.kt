package noria.ui.components.examples

import noria.model.dimensions.px
import noria.scope
import noria.state
import noria.ui.components.*
import noria.ui.components.gallery.gallery
import noria.ui.components.modifiers.Modifier
import noria.ui.components.modifiers.padding

fun checkboxExamples() = gallery("Checkbox", "Checkbox.kt") {
  example("Simple Checkbox") {
    vbox {
      val checked = state { CheckboxState.Checked }
      val partial = state { CheckboxState.Partial }
      val unchecked = state { CheckboxState.Unchecked }
      val modifier = Modifier.padding(vertical = 1.unit)
      checkbox(checked, "checked", modifier = modifier)
      checkbox(partial, "partial", modifier = modifier)
      checkbox(unchecked, "unchecked", modifier = modifier)
    }
  }
  example("Disabled Checkbox") {
    vbox {
      val modifier = Modifier.padding(vertical = 1.unit)
      checkbox(CheckboxState.Checked, label = "checked", enabled = false, modifier = modifier,
               onClick = { error("Should not call onChange") })
      checkbox(CheckboxState.Partial, label = "partial", enabled = false, modifier = modifier,
               onClick = { error("Should not call onChange") })
      checkbox(CheckboxState.Unchecked, label = "partial", enabled = false, modifier = modifier,
               onClick = { error("Should not call onChange") })
    }
  }
  example("Checkbox Tree", preferredHeight = 400.px) {
    val childLabels = listOf("foo", "baz", "bar", "qux")
    val selectionState = state { childLabels.associateWith { false } }
    val modifier = Modifier.padding(vertical = 1.unit)
    vbox {
      hbox {
        checkbox(
          state = when (selectionState.read().values.count { it == true }) {
            childLabels.size -> CheckboxState.Checked
            0 -> CheckboxState.Unchecked
            else -> CheckboxState.Partial
          },
          onClick = {
            selectionState.update { selectionMap ->
              if (selectionMap.values.all { it == true }) {
                childLabels.associateWith { false }
              }
              else {
                childLabels.associateWith { true }
              }
            }
          },
          label = "root",
          modifier = modifier,
        )
      }
      padding(horizontal = 1.unit) {
        vbox {
          childLabels.forEachIndexed { i, label ->
            scope(i) {
              hbox {
                checkbox(checked = selectionState.read()[label] ?: false,
                         onClick = {
                           selectionState.update { selectionMap ->
                             selectionMap + (label to (selectionMap[label] != true))
                           }
                         },
                         label = label,
                         modifier = modifier)
              }
            }
          }
        }
      }
    }
  }
}