package noria.ui.components.examples

import noria.ui.components.gallery.Gallery

fun noriaExamples(): List<Gallery> = listOf(
  layoutExamples(),
  textExamples(),
  buttonExamples(),
  checkboxExamples(),
  hoverableExamples(),
  textInputExamples(),
  reactivityExamples(),
  componentsExamples(),
  popupExamples(),
  contextMenuTestExamples(),
  animationExamples(),
  progressExamples(),
  gradientExamples(),
  shadowExamples(),
  borderExamples(),
  lazyColumnGallery(),
  treeViewGallery(),
  editorViewGallery(),
  splitExamples(),
  lazyConstraint(),
  tooltipExamples(),
  scrollPreserverExamples(),
  focusTraversalExamples(),
  renderPerformanceExamples()
)