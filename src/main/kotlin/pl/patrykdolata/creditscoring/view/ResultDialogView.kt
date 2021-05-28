package pl.patrykdolata.creditscoring.view

import javafx.application.Platform
import javafx.scene.control.ProgressIndicator
import javafx.scene.text.Text
import pl.patrykdolata.creditscoring.fuzzy.*
import pl.patrykdolata.creditscoring.models.BasicClientInfoModel
import pl.patrykdolata.creditscoring.models.CreditInfoModel
import pl.patrykdolata.creditscoring.models.FinancialClientInfoModel
import tornadofx.View
import tornadofx.progressindicator
import tornadofx.text
import tornadofx.vbox
import kotlin.concurrent.thread

class ResultDialogView : View("Rezultat") {
    private val basicClientInfo: BasicClientInfoModel by inject()
    private val financialClientInfo: FinancialClientInfoModel by inject()
    private val creditInfo: CreditInfoModel by inject()
    private val fuzzyInferenceSystem = FuzzyInferenceSystem()

    private lateinit var progressIndicator: ProgressIndicator
    private lateinit var textLabel: Text

    override val root = vbox {
        minWidth = 300.0
        minHeight = 300.0
        textLabel = text("Analiza jakościowa...")
        progressIndicator = progressindicator()
    }

    fun startCalculations() {
//        thread {
//            for (i in 1..100) {
//                println(i)
//                Platform.runLater { progressIndicator.progress = i.toDouble() / 100.0 }
//                Thread.sleep(20)
//            }
//        }
        val qualitativeAnalysis = doQualitativeAnalysis()
        val financialAnalysis = doFinancialAnalysis()
        val creditScore = doFinalAnalysis(qualitativeAnalysis, financialAnalysis)
        print(creditScore.score)
        print(creditScore.memberships)
    }

    private fun doQualitativeAnalysis(): FuzzyResult {
        basicClientInfo.commit()
        val qualitativeAnalysis = QualitativeAnalysis(basicClientInfo.item)
        return fuzzyInferenceSystem.process(qualitativeAnalysis)
    }

    private fun doFinancialAnalysis(): FuzzyResult {
        financialClientInfo.commit()
        val financialAnalysis = FinancialAnalysis(financialClientInfo.item)
        return fuzzyInferenceSystem.process(financialAnalysis)
    }

    private fun doFinalAnalysis(
        qualitativeAnalysis: FuzzyResult,
        financialAnalysis: FuzzyResult
    ): FuzzyResult {
        creditInfo.commit()
        val creditScore = CreditScore(qualitativeAnalysis, financialAnalysis, creditInfo.item)
        return fuzzyInferenceSystem.process(creditScore)
    }
}
