package noria.ui.components.examples

import kotlinx.coroutines.delay
import noria.state
import noria.ui.components.*
import noria.ui.components.gallery.gallery
import noria.ui.core.animation.*
import noria.ui.core.boundary
import noria.ui.core.launchOnce
import noria.ui.text.uiText

fun progressExamples() = gallery("Progress", "Progress.kt") {
  example("Determinate") {
    val completedPercent = infiniteValueAnimation(initialValue = 0f,
                                                  targetValue = 100f,
                                                  Float.VectorConverter,
                                                  infiniteRepeatable(tween(easing = LinearEasing,
                                                                           durationMillis = 5 * 1000),
                                                                     RepeatMode.Restart))
    vbox {
      boundary {
        determinateProgress(completedPercent = completedPercent.read())
      }
    }
  }

  example("Indeterminate") {
    vbox {
      indeterminateProgress(halfUnit)
    }
  }

  example("Determinate with ProgressIndicator API") {
    withProgressIndicator(isDeterminate = true) { progressIndicator ->
      val actions = listOf(
        { progressIndicator.run { setState(ProgressState.RUNNING); setCompletedPercent(0f) } },
        { progressIndicator.setCompletedPercent(10f) },
        { progressIndicator.setCompletedPercent(20f) },
        { progressIndicator.setCompletedPercent(40f) },
        { progressIndicator.setCompletedPercent(80f) },
        { progressIndicator.setState(ProgressState.FINISHED) },
        { progressIndicator.setState(ProgressState.NOT_STARTED) },
      )
      val actionNumber = state { 0 }
      launchOnce {
        while (true) {
          delay(1000)
          actionNumber.update { it + 1 }
        }
      }
      actions[actionNumber.read().coerceAtMost(actions.size - 1)].invoke()

      vbox(align = Align.Center) {
        uiText("Computing...")
        gap(height = 2.unit)
        button("Restart", onClick = { actionNumber.update { 0 } })
      }
    }
  }
}
