package noria.ui.components.examples

import noria.ui.components.*
import noria.ui.components.gallery.gallery
import noria.ui.core.UIContext
import noria.ui.text.uiText
import noria.ui.theme.TextStyleKeys
import noria.ui.theme.ThemeKeys

fun contextMenuTestExamples() = gallery("Context Menus", "ContextMenus.kt") {
  example("Submenu Delay Comparison") {

    val itemsBuilder: UIContext.() -> List<MenuItem> = {
      listOf(
        MenuItem.Header("Menu"),
        MenuItem.Action("New File") { },
        MenuItem.Action("New Folder") { },
        MenuItem.Separator,
        MenuItem.Group(
          "Refactor",
          MenuItem.Action("Rename") { },
          MenuItem.Action("Delete") { },
          MenuItem.Action("Move to Folder...") { },
          MenuItem.Action("Move to Package...") { },
        ),
        MenuItem.Group(
          "Show in",
          MenuItem.Action("Finder") { },
          MenuItem.Action("Terminal") { },
          MenuItem.Action("Tabs") { },
        ),
        MenuItem.Group(
          "Git",
          MenuItem.Action("Add") { },
          MenuItem.Action("Remove") { },
          MenuItem.Action("Add to .gitigonre") { },
        ),
        MenuItem.Action("Copy") { },
        MenuItem.Action("Copy Path") { },
        MenuItem.Action("Copy Relative Path") { }
      )
    }

    vbox {
      uiText("Right click:", textStyleKey = TextStyleKeys.Header3)

      gap(height = 2.unit)

      hbox {
        withMenu(itemsBuilder, 133) {
          box(backgroundColor = theme[ThemeKeys.ButtonFill],
              border = Border(theme[ThemeKeys.ButtonBorder]),
          ) {
            uiText("133 ms (current)")
          }
        }

        gap(width = 4.unit)


        withMenu(itemsBuilder, 250) {
          box(backgroundColor = theme[ThemeKeys.ButtonFill],
              border = Border(theme[ThemeKeys.ButtonBorder]),
          ) {
            uiText("250 ms")
          }
        }

        gap(width = 4.unit)

        withMenu(itemsBuilder, 60) {
          box(backgroundColor = theme[ThemeKeys.ButtonFill],
              border = Border(theme[ThemeKeys.ButtonBorder]),
          ) {
            uiText("60 ms")
          }
        }

        gap(width = 4.unit)

        withMenu(itemsBuilder, 33) {
          box(backgroundColor = theme[ThemeKeys.ButtonFill],
              border = Border(theme[ThemeKeys.ButtonBorder]),
          ) {
            uiText("30 ms (~2 frames)")
          }
        }

        gap(width = 4.unit)

        withMenu(itemsBuilder, 17) {
          box(backgroundColor = theme[ThemeKeys.ButtonFill],
              border = Border(theme[ThemeKeys.ButtonBorder]),
          ) {
            uiText("17 ms (~1 frame)")
          }
        }

        gap(width = 4.unit)

        withMenu(itemsBuilder, 6) {
          box(backgroundColor = theme[ThemeKeys.ButtonFill],
              border = Border(theme[ThemeKeys.ButtonBorder]),
          ) {
            uiText("6 ms")
          }
        }

        gap(width = 4.unit)

        withMenu(itemsBuilder, 0) {
          box(backgroundColor = theme[ThemeKeys.ButtonFill],
              border = Border(theme[ThemeKeys.ButtonBorder]),
          ) {
            uiText("0 ms")
          }
        }

        gap(width = 4.unit)
      }
    }
  }
}