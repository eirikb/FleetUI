package noria.ui.components.examples

import noria.ui.components.gallery.gallery
import noria.ui.components.gap
import noria.ui.components.vbox
import noria.ui.core.inputLagDetector
import noria.ui.core.vsyncTester
import noria.ui.text.ParagraphStyle
import noria.ui.text.uiText
import noria.ui.text.webLink

fun renderPerformanceExamples() = gallery("Render Metrics", "RenderMetrics.kt") {
  example("Vsync Tester") {
    vbox {
      uiText("Click to toggle animation. When v-sync works smooth, text should looks grey.",
             paragraphStyle = ParagraphStyle.multiline)
      gap(height = 1.unit)
      webLink("https://www.vsynctester.com/manual.html")
      gap(height = 1.unit)
      vsyncTester()
      gap(height = 1.unit)
      inputLagDetector()
    }
  }
}