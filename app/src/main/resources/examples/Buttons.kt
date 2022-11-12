package noria.ui.components.examples

import noria.StateCell
import noria.windowManagement.api.ModifiersState
import noria.windowManagement.extensions.Keys
import noria.model.ThemeKey
import noria.scope
import noria.state
import noria.ui.components.*
import noria.ui.components.gallery.gallery
import noria.ui.core.UIContext
import noria.ui.events.HandlerScope
import noria.ui.events.Input
import noria.ui.text.link
import noria.ui.text.uiText
import noria.ui.theme.ThemeKeys
import noria.windowManagement.extensions.COMMAND_WINDOWS_SUPER_ONLY

private val hintTextOptions = listOf(null, Input.Keystroke(Keys.RightBracket, ModifiersState.COMMAND_WINDOWS_SUPER_ONLY).presentableName)
private val iconOptions = listOf(null, ThemeKeys.RightIcon)

fun buttonExamples() = gallery("Buttons", "Buttons.kt") {
  example("Primary") {
    placeButtonCombinations(UIContext::primaryButton, UIContext::smallPrimaryButton)
  }

  example("Secondary") {
    placeButtonCombinations(UIContext::button, UIContext::smallButton)
  }

  example("Dangerous") {
    placeButtonCombinations(UIContext::dangerousButton, UIContext::smallDangerousButton)
  }

  example("Toggle Button") {
    hbox(Align.Center) {
      for (icon in iconOptions) {
        for (hintText in hintTextOptions) {
          padding(1.unit) {
            scope(icon to hintText) {
              vbox(Align.Center) {
                val selectedState = state { false }
                toggleButton("Text", selectedState, icon, hintText)
                gap(height = 1.unit)
                toggleButton("Text", StateCell.Constant(false), icon, hintText, enabled = false)
                gap(height = 1.unit)
                toggleButton("Text", StateCell.Constant(true), icon, hintText, enabled = false)
              }
            }
          }
        }
      }
    }
  }

  example("Icon Button") {
    padding(1.unit) {
      vbox(Align.Center) {
        iconButton(ThemeKeys.RightIcon) {}
        gap(height = 1.unit)
        iconButton(ThemeKeys.RightIcon, enabled = false) {}
      }
    }
  }

  example("Icon Toggle Button") {
    hbox {
      padding(1.unit) {
        vbox {
          val selectedState = state { false }
          iconToggleButton(ThemeKeys.RightIcon, selectedState)
          gap(height = 1.unit)
          iconToggleButton(ThemeKeys.RightIcon, StateCell.Constant(false), enabled = false)
          gap(height = 1.unit)
          iconToggleButton(ThemeKeys.RightIcon, StateCell.Constant(true), enabled = false)
        }
      }
    }
  }

  example("Link") {
    hbox(Align.Center) {
      for (external in listOf(false, true)) {
        padding(1.unit) {
          scope(external) {
            vbox(Align.Center) {
              hbox {
                uiText("Some text with a ")
                link("link", external = external) {}
                uiText(" included in it")
              }
              hbox {
                gap(height = 1.unit)
                uiText("Some text with a ")
                link("link", external = external, enabled = false) {}
                uiText(" included in it")
              }
            }
          }
        }
      }
    }
  }
}

private fun UIContext.placeButtonCombinations(vararg builders: UIContext.(String, ThemeKey<String>?, String?, Boolean, HandlerScope.() -> Unit) -> Unit) {
  hbox(Align.Center) {
    for (builder in builders) {
      for (icon in iconOptions) {
        for (hintText in hintTextOptions) {
          padding(1.unit) {
            scope(Triple(builder, icon, hintText)) {
              vbox(Align.Center) {
                builder("Text", icon, hintText, true) {}
                gap(height = 1.unit)
                builder("Text", icon, hintText, false) {}
              }
            }
          }
        }
      }
    }
  }
}