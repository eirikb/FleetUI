package noria.ui.components.examples

import andel.editor.simpleEditor
import andel.intervals.Interval
import andel.intervals.Intervals
import andel.text.Text
import fleet.util.nextLongValue
import noria.model.ThemeKey
import noria.model.components.editor.TextAttributesKey
import noria.model.dimensions.Color
import noria.model.dimensions.px
import noria.state
import noria.ui.components.TextDecoration
import noria.ui.components.constrain
import noria.ui.components.editor.editorData
import noria.ui.components.editor.editorView
import noria.ui.components.editor.simpleEditorModel
import noria.ui.components.gallery.Gallery
import noria.ui.components.gallery.gallery
import noria.ui.core.boundary
import noria.ui.text.TextAttributes

fun editorViewGallery(): Gallery =
  gallery("Editor View", "EditorView.kt") {
    example("Text Decorations") {
      constrain(preferredWidth = 500.px) {
        val color = Color.red
        var text = ""
        val intervals = mutableListOf<Interval<Long, TextAttributesKey>>()
        val themeMap = mutableMapOf<ThemeKey<*>, TextAttributes>()
        TextDecoration.Style.values()
          .filterNot { it == TextDecoration.Style.BORDER}
          .forEach { style ->
          TextDecoration.Position.values().forEach { pos ->
            text += "${style.toString().toLowerCase().capitalize()} ${pos.toString().toLowerCase().capitalize()} "
            listOf(1, 2, 3).forEach { thickness ->
              val key = "$style $pos ${thickness}"
              themeMap[ThemeKey<Any>(key)] = TextAttributes(decoration = TextDecoration(color, style, pos, thickness.toFloat()))
              var start = text.length.toLong()
              text += "$thickness >"
              intervals.add(Interval(nextLongValue(), start, text.length.toLong(), false, false, TextAttributesKey(key)))
              start = text.length.toLong()
              text += "< px pi_qp-fg–ly"
              intervals.add(Interval(nextLongValue(), start, text.length.toLong(), false, false, TextAttributesKey(key)))
              text += " "
            }
            text += "\n\n"
          }
        }

        val model = state { simpleEditor(Text.makeText(text)) }
        boundary {
          val simpleEditorState = model.read()
          val editorModel = simpleEditorModel(model)
          var data = simpleEditorState.editorData(model, theme.copy(map = theme.map.putAll(themeMap)))
          data = data.copy(markup = Intervals.droppingCollapsed().fromIntervals(intervals))
          editorView(editorData = data, editorModel = editorModel)
        }
      }
    }
  }
