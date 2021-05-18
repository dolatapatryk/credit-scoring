package pl.patrykdolata.creditscoring.app

import javafx.scene.Cursor
import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()
        val clearButton by cssclass()
        val w100 by cssclass()
    }

    init {
        label and heading {
            padding = box(10.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }
        clearButton {
            cursor = Cursor.HAND
        }
        w100 {
            minWidth = 250.px
        }
    }
}
