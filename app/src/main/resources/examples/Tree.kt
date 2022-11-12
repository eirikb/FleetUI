package noria.ui.components.examples

import noria.ID
import noria.model.dimensions.Color
import noria.model.dimensions.Constraints
import noria.model.dimensions.Point
import noria.model.dimensions.px
import noria.ui.components.border
import noria.ui.components.constrain
import noria.ui.components.gallery.Gallery
import noria.ui.components.gallery.gallery
import noria.ui.components.list.SpeedSearchOptions
import noria.ui.components.tree.*
import noria.ui.core.*
import noria.ui.text.paragraphWithHighlights
import noria.ui.text.uiText

fun treeViewGallery(): Gallery {
  return gallery("Tree View", "Tree.kt") {
    example("Tree") {
      fun emitNode(key: Int): TreeItem<Int> {
        return TreeItem(
          key = key,
          item = key,
          expandable = true,
          children = {
            (1..5).map { i -> emitNode(i) }
          }
        ) { path, opts ->
          treeCellRenderer(path, opts) { _, treeItemOpts ->
            border(color = Color.red) {
              val checkbox = collectLayoutBuilder { uiText("X") }
              val checkboxThunk = performLayout(checkbox, Constraints.NONE)
              withOverlay(Overlay(ID(), TreeHostKey) { overlayData ->
                listOf(Placement(checkboxThunk, Point(overlayData.hostSize.width - checkboxThunk.width!!,
                                                      overlayData.anchorBounds.top)))
              }) {
                uiText(key.toString())
              }
            }
          }
        }
      }

      constrain(preferredHeight = 500.px, preferredWidth = 500.px) {
        treeView(treeModel(emitNode(0)))
      }
    }

    example("Tree without automatic Cursor Selection") {
      fun emitNode(key: Int): TreeItem<Int> {
        return TreeItem(
          key = key,
          item = key,
          expandable = true,
          children = {
            (1..5).map { i -> emitNode(i) }
          }
        ) { path, opts ->
          treeCellRenderer(path, opts, showBorder = true) { _, treeItemOpts ->
            border(color = Color.red) {
              val checkbox = collectLayoutBuilder { uiText("X") }
              val checkboxThunk = performLayout(checkbox, Constraints.NONE)
              withOverlay(Overlay(ID(), TreeHostKey) { overlayData ->
                listOf(Placement(checkboxThunk, Point(overlayData.hostSize.width - checkboxThunk.width!!,
                                                      overlayData.anchorBounds.top)))
              }) {
                uiText(key.toString())
              }
            }
          }
        }
      }

      constrain(preferredHeight = 500.px, preferredWidth = 500.px) {
        treeView(treeModel(emitNode(0), TreeViewOptions(updateSelectionWithCursor = false)))
      }
    }

    /**
     * to allow that provide height key to items
     */
    example("Tree with items of different height") {
      fun emitNode(key: Int): TreeItem<Int> {
        val even = key % 2 == 0
        return TreeItem(
          key = key,
          item = key,
          heightKey = even,
          expandable = true,
          children = {
            (1..5).map { i -> emitNode(i) }
          }
        ) { path, opts ->
          treeCellRenderer(path, opts) { _, treeItemOpts ->

            border(color = Color.red) {
              val checkbox = collectLayoutBuilder { uiText("X") }
              val checkboxThunk = performLayout(checkbox, Constraints.NONE)
              withOverlay(Overlay(ID(), TreeHostKey) { overlayData ->
                listOf(Placement(checkboxThunk, Point(overlayData.hostSize.width - checkboxThunk.width!!,
                                                      overlayData.anchorBounds.top)))
              }) {

                val height = if (even) {
                  40.px
                }
                else {
                  20.px
                }

                constrain(preferredHeight = height) {
                  uiText(key.toString())
                }
              }
            }
          }
        }
      }

      constrain(preferredHeight = 500.px, preferredWidth = 500.px) {
        treeView(treeModel(emitNode(0)))
      }
    }

    example("Tree with SpeedSearch") {
      fun emitNode(key: Int): TreeItem<Int> {
        return TreeItem(
          key = key,
          item = key,
          expandable = true,
          textToMatch = key.toString(),
          children = {
            (1..5).map { i -> emitNode(i) }
          }
        ) { path, opts ->
          treeCellRenderer(path, opts) { _, treeItemOpts ->
            border(color = Color.red) {
              val checkbox = collectLayoutBuilder { uiText("X") }
              val checkboxThunk = performLayout(checkbox, Constraints.NONE)
              withOverlay(Overlay(ID(), TreeHostKey) { overlayData ->
                listOf(Placement(checkboxThunk, Point(overlayData.hostSize.width - checkboxThunk.width!!,
                                                      overlayData.anchorBounds.top)))
              }) {
                paragraphWithHighlights(key.toString(), matcher = opts.matcher)
              }
            }
          }
        }
      }

      constrain(preferredHeight = 500.px, preferredWidth = 500.px) {
        treeView(treeModel(emitNode(0), options = TreeViewOptions(speedSearchOptions = SpeedSearchOptions.Default())))
      }
    }


  }
}

