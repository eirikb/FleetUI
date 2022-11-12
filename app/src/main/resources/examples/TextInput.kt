package noria.ui.components.examples

import noria.state
import noria.ui.components.*
import noria.ui.components.gallery.gallery
import noria.ui.components.modifiers.Modifier
import noria.ui.components.modifiers.stretchHorizontally
import noria.ui.theme.ThemeKeys

private const val exampleInputDefaultWidth = 64
private const val loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

fun textInputExamples() = gallery("TextInput", "TextInput.kt") {
  example("One-Line TextInput") {
    vbox {
      hbox {
        constrain(preferredWidth = exampleInputDefaultWidth.unit) {
          textInput(loremIpsum)
        }

        gap(width = 4.unit)

        constrain(preferredWidth = exampleInputDefaultWidth.unit) {
          textInput(textInputModel(), placeholderText = "Some placeholder")
        }

        gap(width = 4.unit)

        constrain(preferredWidth = exampleInputDefaultWidth.unit) {
          textInput(textInputModel(state { createTextInputData(enabled = false) }), placeholderText = "Disabled input with placeholder")
        }

        gap(width = 4.unit)

        constrain(preferredWidth = exampleInputDefaultWidth.unit) {
          textInput(textInputModel(state { createTextInputData("Disabled input with text", enabled = false) }))
        }
      }

      gap(height = 2.unit)

      hbox {
        constrain(preferredWidth = exampleInputDefaultWidth.unit) {
          textInput(textInputModel(), ThemeKeys.SearchIcon, placeholderText = "Input with icon")
        }

        gap(width = 4.unit)

        constrain(preferredWidth = exampleInputDefaultWidth.unit) {
          textInput(textInputModel(password = true), placeholderText = "Password input")
        }

        gap(width = 4.unit)

        constrain(preferredWidth = exampleInputDefaultWidth.unit) {
          textInput(textInputModel(),
                    style = defaultTextInputStyle().copy(textStyle = TextInputTextStyle.Code),
                    placeholderText = "Code input")
        }

        gap(width = 4.unit)

        constrain(preferredWidth = exampleInputDefaultWidth.unit) {
          textInput(textInputModel(state { createTextInputData(enabled = false) }), ThemeKeys.SearchIcon,
                    placeholderText = "Disabled input with icon")
        }
      }
    }
  }

  example("Large TextInput") {
    vbox {
      hbox {
        constrain(preferredWidth = exampleInputDefaultWidth.unit) {
          textInput(loremIpsum, style = largeTextInputStyle())
        }

        gap(width = 4.unit)

        constrain(preferredWidth = exampleInputDefaultWidth.unit) {
          textInput(textInputModel(), style = largeTextInputStyle(), placeholderText = "Large input with placeholder")
        }

        gap(width = 4.unit)

        constrain(preferredWidth = exampleInputDefaultWidth.unit) {
          textInput(textInputModel(state { createTextInputData(enabled = false) }),
                    placeholderText = "Disabled large input with placeholder",
                    style = largeTextInputStyle())
        }
      }

      gap(height = 2.unit)

      hbox {
        constrain(preferredWidth = exampleInputDefaultWidth.unit) {
          textInput(textInputModel(), ThemeKeys.SearchIcon, placeholderText = "Input with icon", style = largeTextInputStyle())
        }

        gap(width = 4.unit)

        constrain(preferredWidth = exampleInputDefaultWidth.unit) {
          textInput(textInputModel(password = true), style = largeTextInputStyle(), placeholderText = "Password input")
        }

        gap(width = 4.unit)

        constrain(preferredWidth = exampleInputDefaultWidth.unit) {
          textInput(textInputModel(state { createTextInputData(enabled = false) }),
                    ThemeKeys.SearchIcon,
                    placeholderText = "Disabled large input with icon",
                    style = largeTextInputStyle())
        }
      }
    }
  }

  example("TextInput with Error") {
    vbox {
      hbox {
        constrain(preferredWidth = exampleInputDefaultWidth.unit) {
          textInput(loremIpsum, style = errorTextInputStyle())
        }

        gap(width = 4.unit)

        constrain(preferredWidth = exampleInputDefaultWidth.unit) {
          textInput(loremIpsum, style = largeErrorTextInputStyle())
        }
      }
    }
  }

  example("Inner TextInput") {
    vbox {
      hbox {
        constrain(preferredWidth = exampleInputDefaultWidth.unit) {
          box(backgroundColor = theme[ThemeKeys.SelectionFill]) {
            padding(1.unit, 0.25.unit) {
              hbox(Align.Center, Modifier.stretchHorizontally()) {
                icon(ThemeKeys.ChevronRightIcon)
                gap(1.unit)
                flexible {
                  textInput(loremIpsum, style = innerTextInputStyle())
                }
              }
            }
          }
        }

        gap(width = 4.unit)

        constrain(preferredWidth = exampleInputDefaultWidth.unit) {
          box(backgroundColor = theme[ThemeKeys.SelectionFill]) {
            padding(1.unit, 0.25.unit) {
              hbox(Align.Center, Modifier.stretchHorizontally()) {
                icon(ThemeKeys.ChevronRightIcon)
                gap(1.unit)
                flexible {
                  textInput(textInputModel(state { createTextInputData(enabled = false) }),
                            style = innerTextInputStyle(),
                            placeholderText = "Disabled inner input")
                }
              }
            }
          }
        }
      }
    }
  }

  example("Borderless TextInput") {
    vbox {
      hbox {
        constrain(preferredWidth = exampleInputDefaultWidth.unit) {
          box(backgroundColor = theme[ThemeKeys.ToolFill], uniformBorderRadius = 1.unit, border = Border(theme[ThemeKeys.Border])) {
            padding(0.unit, 0.unit, 0.unit, 0.5.unit) {
              hbox(Align.Center, Modifier.stretchHorizontally()) {
                iconButton(ThemeKeys.LeftIcon) {}
                gap(1.unit)
                flexible {
                  textInput(loremIpsum, style = borderlessTextInputStyle())
                }
              }
            }
          }
        }
      }
    }
  }

  example("Multiline TextInput with Scroll") {
    hbox {
      constrain(preferredWidth = exampleInputDefaultWidth.unit) {
        textInput(textInputModel(loremIpsum, textInputMode = TextInputMode.Multiline(minLines = 3)))
      }

      gap(width = 4.unit)

      constrain(preferredWidth = exampleInputDefaultWidth.unit) {
        textInput(
          textInputModel(textInputMode = TextInputMode.Multiline(minLines = 3)),
          placeholderText = "MultiLine\nPlaceholder",
        )
      }
    }
  }


  example("Multiline TextInput with Wrap") {
    hbox {
      constrain(preferredWidth = exampleInputDefaultWidth.unit) {
        textInput(
          textInputModel(loremIpsum,
                         softWrapEnabled = true,
                         textInputMode = TextInputMode.Multiline(minLines = 3))
        )
      }

      gap(width = 4.unit)

      constrain(preferredWidth = exampleInputDefaultWidth.unit) {
        textInput(
          textInputModel(softWrapEnabled = true,
                         textInputMode = TextInputMode.Multiline(minLines = 3)),
          placeholderText = "Placeholder that wraps! ".repeat(20),
        )
      }

    }
  }

  example("TextInput with Fixed Number of Lines") {
    hbox {
      constrain(preferredWidth = exampleInputDefaultWidth.unit) {
        textInput(textInputModel(loremIpsum,
                                 softWrapEnabled = true,
                                 textInputMode = TextInputMode.Multiline(minLines = 3, maxLines = 3)))
      }

      gap(width = 4.unit)

      constrain(preferredWidth = exampleInputDefaultWidth.unit) {
        textInput(textInputModel(softWrapEnabled = true,
                                 textInputMode = TextInputMode.Multiline(minLines = 3, maxLines = 3)),
                  placeholderText = "Placeholder that wraps and scrolls! ".repeat(20))
      }
    }
  }

  example("Growing TextInput") {
    // we need to relax width constraints
    align {
      textInput(textInputModel("Type here...", textInputMode = TextInputMode.OneLine))
    }
  }
}
