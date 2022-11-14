package fleetui

import noria.ui.components.examples.noriaExamples
import noria.ui.components.gallery.galleryView

fun main() {
    mainWindow("Gallery") {
        galleryView(noriaExamples())
    }
}
