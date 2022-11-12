package noria.ui.components.examples

import noria.model.dimensions.*
import noria.ui.components.*
import noria.ui.components.gallery.gallery
import noria.ui.components.modifiers.Modifier
import noria.ui.components.modifiers.padding
import noria.ui.components.split.SplitDirection
import noria.ui.components.split.splitView
import noria.ui.core.*
import noria.ui.core.toSkija
import noria.ui.text.*
import noria.ui.theme.TextStyleKeys
import noria.ui.theme.ThemeKeys
import org.jetbrains.skia.FontEdging
import org.jetbrains.skia.FontHinting
import org.jetbrains.skia.Paint
import org.jetbrains.skia.TextBlob
import org.jetbrains.skia.shaper.Shaper
import org.jetbrains.skia.shaper.ShapingOptions
import org.jetbrains.skia.shaper.TextBlobBuilderRunHandler

fun textExamples() = gallery("Text", "Text.kt") {
  example("Regular UI Text") {
    uiText("Text")
  }

  example("Trimmed Text") {
    align {
      constrain(preferredWidth = 30.unit) {
        trimmedTextLine("Some text with an ellipsis in the middle")
      }
    }
  }

  example("Fleet UI Kit Typography") {
    val fonts = listOf(
      "Header 1" to TextStyleKeys.Header1,
      "Header 2" to TextStyleKeys.Header2,
      "Header 3" to TextStyleKeys.Header3,
      "Default" to TextStyleKeys.Default,
      "Default italic" to TextStyleKeys.DefaultItalic,
      "Default semi-bold" to TextStyleKeys.DefaultSemiBold,
      "Default bold" to TextStyleKeys.DefaultBold,
      "Default extra bold" to TextStyleKeys.DefaultExtraBold,
      "Small" to TextStyleKeys.Small,
      "Code" to TextStyleKeys.Code
    )
    val padding = Modifier.padding(1.unit, 2.unit)

    hbox {
      vbox {
        fonts.forEach { (text, key) ->
          hbox(Align.Center) {
            uiText(text,
                   textStyleKey = key,
                   modifier = padding
            )
            val textSpec = textSpec(key)
            uiText("— ${textSpec.fontSpec.size}, weight=${textSpec.fontSpec.weight}, lineHeight=${textSpec.lineHeight}",
                   color = theme[ThemeKeys.DimmedText],
                   modifier = padding
            )
          }
        }
      }
    }
  }


  example("Emoji") {
    uiText("""😁🔥🥦👮🏼""")
  }

  example("Custom Color") {
    uiText("Colored text", color = theme[ThemeKeys.SelectedText])
  }

  example("Custom Spec") {
    uiText("Bold text", textStyleKey = TextStyleKeys.DefaultBold)
  }

  val loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

  example("Wrapping Text in a Box") {
    constrain({ cs -> cs.bounded(100.unit) }) {
      uiText(text = loremIpsum,
             paragraphStyle = ParagraphStyle.multiline)
    }
  }

  example("Multiline Text") {
    splitView(direction = SplitDirection.HORIZONTAL) {
      first {
        uiText(loremIpsum, paragraphStyle = ParagraphStyle.multiline)
      }
      second {
        uiText(loremIpsum, paragraphStyle = ParagraphStyle.multiline)
      }
    }
  }

  example("Different Line Height") {
    val text = "First line\nSecond line"
    val textSpec = textSpec(TextStyleKeys.Default)
    fun FlexContext.withHeight(height: Float?) {
      vbox(modifier = Modifier.padding(1.unit)) {
        uiText("height: $height")
        gap(1.unit)
        uiText(text,
               userTextSpec = textSpec.copy(lineHeight = height),
               paragraphStyle = ParagraphStyle.multiline)
      }
    }
    hbox {
      withHeight(null)
      withHeight(0f)
      withHeight(1f)
      withHeight(1.2f)
      withHeight(1.3f)
    }
  }

  fun UIContext.textLine(text: String,
                         fontSpec: FontSpec,
                         fontRasterSettings: FontRasterSettings,
                         color: Color = theme[ThemeKeys.Text],
                         textDecoration: TextDecoration? = null) {
    val shaper = Shaper.makeShapeThenWrap()
    shaper.let {
      val handler = TextBlobBuilderRunHandler<Any>(text)
      layout { cs ->
        val font = findFont(fontSpec, fontRasterSettings)
        val fgColor = color.applyAlpha(contrast)
        shaper.shape(text, font, ShapingOptions.DEFAULT, cs.maxWidth.toPhysical().value, handler)
        val blob: TextBlob? = handler.makeBlob()
        blob?.let {
          val lineWidth = blob.blockBounds.width.ppx
          val left = 0f.ppx
          val boxHeight = blob.blockBounds.height.ppx
          render(Rect(Point.ZERO, Size(lineWidth.toLogical(), boxHeight.toLogical()).coerceIn(cs))) {
            Paint().use { paint ->
              paint.color4f = fgColor.toSkija()
              canvas.drawTextBlob(blob, 0f, 0f, paint)
            }

            if (textDecoration != null) {
              renderDecoration(decoration = textDecoration,
                               metrics = font.textDecorationMetrics(),
                               left = left,
                               width = lineWidth,
                               fallbackColor = fgColor)
            }
          }
        } ?: Size.ZERO
      }
    }
  }

  example("Font Fallbacks") {
    val text = "Hello world こんにちは世界"
    val rasterSettings = fontRasterSettings()
    vbox {
      uiText(text, textStyleKey = TextStyleKeys.Code)
      textLine(text, fontSpec = textSpec(TextStyleKeys.Default).fontSpec, textDecoration = TextDecoration(Color.red), fontRasterSettings = rasterSettings)
      textLine("שר-++ה", fontSpec = textSpec(TextStyleKeys.Code).fontSpec, rasterSettings)
      textLine("שר-++ה", fontSpec = textSpec(TextStyleKeys.Default).fontSpec, rasterSettings)
      textLine("Sarah (שר--+ה)", fontSpec = textSpec(TextStyleKeys.Code).fontSpec, rasterSettings)
      textLine("Sarah (שר--+ה)", fontSpec = textSpec(TextStyleKeys.Default).fontSpec, rasterSettings)
    }
  }

  example("Paragraph Building") {
    val p = buildParagraph(fontRasterSettings = fontRasterSettings()) {
      styledText("Regular text, ", textStyle(textSpec(TextStyleKeys.Default)))
      styledText("bold text, ", textStyle(textSpec(TextStyleKeys.DefaultBold)))
      styledText("source code, ", textStyle(textSpec(TextStyleKeys.Code)))
      styledText("colored", textStyle(textSpec(TextStyleKeys.Code),
                                      color = Color.white,
                                      background = Color.black))
    }
    resourceHolder(p)
    uiParagraph(p)
  }

  example("Baseline Comparison Between Rendering Strategies") {
    hbox {
      uiText("Paragraph_")
      trimmedTextLine("_TextLine")
    }
  }

  example("Different Font Rendering Settings") {
    // Now only low level text rendering api respects font rendering settings
    val defaultTextSpec = textSpec(TextStyleKeys.Default)
    val fontSpec = defaultTextSpec.fontSpec
    // defaults for my mac is:
    // FontHinting.NORMAL
    val hintingOptions = listOf(FontHinting.NONE, FontHinting.SLIGHT, FontHinting.NORMAL, FontHinting.FULL)
    // false
    val isAutoHintingForcedOptions = listOf(false, true)
    // false
    val subpixelOptions = listOf(false, true)
    // FontEdging.ANTI_ALIAS
    val edgingOptions = listOf(FontEdging.ALIAS, FontEdging.ANTI_ALIAS, FontEdging.SUBPIXEL_ANTI_ALIAS)
    val text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    vbox {
      for (subpixel in subpixelOptions) {
        for (edging in edgingOptions) {
          for (hinting in hintingOptions) {
            for (autoHintingForced in isAutoHintingForcedOptions) {
              val rasterSettings = FontRasterSettings(subpixel = subpixel,
                                                      edging = edging,
                                                      hinting = hinting,
                                                      autoHintingForced = autoHintingForced)
              uiText("$rasterSettings")
              textLine(text, fontSpec = fontSpec, fontRasterSettings = rasterSettings)
              gap(height = 2.unit)
            }
          }
        }
      }
    }
  }
}