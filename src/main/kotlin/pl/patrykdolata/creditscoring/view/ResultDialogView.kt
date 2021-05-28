package pl.patrykdolata.creditscoring.view

import javafx.application.Platform
import javafx.scene.control.ProgressIndicator
import javafx.scene.text.Text
import pl.patrykdolata.creditscoring.models.BasicClientInfoModel
import pl.patrykdolata.creditscoring.models.CreditInfoModel
import pl.patrykdolata.creditscoring.models.FinancialClientInfoModel
import tornadofx.*
import kotlin.concurrent.thread

class ResultDialogView : View("Rezultat") {
    private val basicClientInfo: BasicClientInfoModel by inject()
    private val financialClientInfoModel: FinancialClientInfoModel by inject()
    private val creditInfo: CreditInfoModel by inject()
    private lateinit var progressIndicator: ProgressIndicator
    private lateinit var textLabel: Text

    override val root = vbox {
        minWidth = 300.0
        minHeight = 300.0
        textLabel = text("Analiza jakościowa...")
        progressIndicator = progressindicator()
    }

    fun startCalculations() {
        thread {
            for (i in 1..100) {
                println(i)
                Platform.runLater { progressIndicator.progress = i.toDouble() / 100.0 }
                Thread.sleep(20)
            }
        }
    }
}
