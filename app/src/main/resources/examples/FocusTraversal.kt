package noria.ui.components.examples

import noria.cell
import noria.memo
import noria.state
import noria.ui.components.*
import noria.ui.components.gallery.gallery
import noria.ui.core.*
import noria.ui.text.link

fun focusTraversalExamples() = gallery("Focus Traversal", "FocusTraversal.kt") {
  example("Simple") {
    var focusRef: FocusNode? = null
    vbox {
      button("Focus") { focusRef!!.requestFocus() }
      gap(height = 4.unit)
      focusRef = focusTraversalRoot {
        vbox {
          // todo[unterhofer] this causes flickering when focusing something else! The ascendant transparent nodes seem to be unstable
          memo {
            focusNode.requestFocus()
          }
          textInput("1")
          gap(height = 2.unit)
          textInput("2")
          gap(height = 2.unit)
          textInput("3")
        }
        focusNode
      }
    }
  }

  /**
   * Use the priority parameter to control the order of both focus traversal and population of the actionContext
   */
  example("Ordered") {
    var focusRef: FocusNode? = null
    vbox {
      button("Focus") { focusRef!!.requestFocus() }
      gap(height = 4.unit)
      focusRef = focusTraversalRoot {
        vbox {
          focusable(priority = 4) { textInput("4") }
          gap(height = 2.unit)
          focusable(priority = 2) { textInput("2") }
          gap(height = 2.unit)
          focusable(priority = 1) { textInput("1") }
          gap(height = 2.unit)
          focusable(priority = 3) { textInput("3") }
        }
        focusNode
      }
    }
  }

  /**
   * UI Components bring their own built-in focus targets
   */
  example("Components") {
    var focusRef: FocusNode? = null
    vbox {
      button("Focus") { focusRef!!.requestFocus() }
      gap(height = 4.unit)
      focusRef = focusTraversalRoot {
        vbox {
          textInput("1")
          gap(height = 2.unit)
          button("2") { }
          gap(height = 2.unit)
          link("3") { }
          gap(height = 2.unit)
          dangerousButton("4") { }
          gap(height = 2.unit)
          vbox {
            smallPrimaryButton("5") { }
            gap(height = 2.unit)
            smallButton("6") { }
          }
          gap(height = 2.unit)
          toggleButton("7")
          gap(height = 2.unit)
          checkbox(state { false }, "8")
        }
        focusNode
      }
    }
  }

  /**
   * Assign the same key to multiple focus targets with the same parent to share focus between them
   */
  example("Shared Focus") {
    var focusRef: FocusNode? = null
    vbox {
      button("Focus") { focusRef!!.requestFocus() }
      gap(height = 4.unit)
      focusRef = focusTraversalRoot {
        vbox(Align.Stretch) {
          hbox {
            ratio(0.5f) {
              focusable(key = 1) {
                textInput("1")
              }
            }
            gap(2.unit)
            ratio(0.5f) {
              focusable(key = 1) {
                button("Also 1") {}
              }
            }
          }
          gap(height = 2.unit)
          textInput("2")
          gap(height = 2.unit)
          textInput("3")
        }
        focusNode
      }
    }
  }

  /**
   * A focus target with multiple child focus targets becomes an implicit focus selector - only one child at a time will be selected
   */
  example("Tree") {
    var focusRef: FocusNode? = null
    vbox {
      button("Focus") { focusRef!!.requestFocus() }
      gap(height = 4.unit)
      focusRef = focusTraversalRoot {
        vbox(Align.Stretch) {
          focusable {
            hbox {
              ratio(0.33f) {
                textInput("1.1")
              }
              gap(2.unit)
              ratio(0.33f) {
                textInput("1.2")
              }
              gap(2.unit)
              ratio(0.34f) {
                textInput("1.3")
              }
            }
          }
          gap(height = 2.unit)
          textInput("2")
          gap(height = 2.unit)
          textInput("3")
        }
        focusNode
      }
    }
  }

  example("Tree With Inner Root") {
    var focusRef: FocusNode? = null
    vbox {
      button("Focus") { focusRef!!.requestFocus() }
      gap(height = 4.unit)
      focusRef = focusTraversalRoot {
        vbox(Align.Stretch) {
          textInput("1")
          gap(height = 2.unit)
          focusTraversalRoot {
            hbox {
              ratio(0.33f) {
                textInput("2.1")
              }
              gap(2.unit)
              ratio(0.33f) {
                textInput("2.2")
              }
              gap(2.unit)
              ratio(0.34f) {
                textInput("2.3")
              }
            }
          }
          gap(height = 2.unit)
          textInput("3")
        }
        focusNode
      }
    }
  }

  /**
   * Focus traversal skips all disabled nodes and their descendants, even if the descendants are enabled
   */
  example("Disabled Subtree") {
    focusTraversalRoot {
      vbox(Align.Stretch) {
        textInput("1")
        gap(height = 2.unit)
        focusable(enabled = cell { false }) {
          hbox {
            ratio(0.33f) {
              button("2.1") {}
            }
            gap(2.unit)
            ratio(0.33f) {
              button("2.2") {}
            }
            gap(2.unit)
            ratio(0.34f) {
              button("2.3") {}
            }
          }
        }
        gap(height = 2.unit)
        textInput("3")
      }
    }
  }

  /**
   * Focus traversal skips the whole subtree when all its leaves are disabled
   */
  example("Exclusively Disabled Leaves in Subtree") {
    focusTraversalRoot {
      vbox(Align.Stretch) {
        textInput("1")
        gap(height = 2.unit)
        focusable {
          hbox {
            ratio(0.33f) {
              button("2.1", enabled = false) {}
            }
            gap(2.unit)
            ratio(0.33f) {
              button("2.2", enabled = false) {}
            }
            gap(2.unit)
            ratio(0.34f) {
              button("2.3", enabled = false) {}
            }
          }
        }
        gap(height = 2.unit)
        textInput("3")
      }
    }
  }
}