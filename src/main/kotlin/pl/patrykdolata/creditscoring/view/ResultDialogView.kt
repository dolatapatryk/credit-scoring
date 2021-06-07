package pl.patrykdolata.creditscoring.view

import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.control.ProgressIndicator
import javafx.scene.image.ImageView
import javafx.scene.text.Text
import pl.patrykdolata.creditscoring.app.Styles
import pl.patrykdolata.creditscoring.fuzzy.*
import pl.patrykdolata.creditscoring.models.BasicClientInfoModel
import pl.patrykdolata.creditscoring.models.CreditInfoModel
import pl.patrykdolata.creditscoring.models.FinancialClientInfoModel
import pl.patrykdolata.creditscoring.round
import tornadofx.*
import kotlin.concurrent.thread

class ResultDialogView : View("Rezultat") {
    private val basicClientInfo: BasicClientInfoModel by inject()
    private val financialClientInfo: FinancialClientInfoModel by inject()
    private val creditInfo: CreditInfoModel by inject()
    private val fuzzyInferenceSystem = FuzzyInferenceSystem()

    private lateinit var progressIndicator: ProgressIndicator
    private lateinit var textLabel: Text
    private lateinit var image: ImageView

//    override val root = vbox {
//        minWidth = 300.0
//        minHeight = 300.0
//        textLabel = text("Analiza jakościowa...") {
//            addClass(Styles.heading)
//        }
//        progressIndicator = progressindicator()
//        imageview("t.jpg") {
//            scaleX = .3
//            scaleY = .3
//        }
//    }

    override val root = form {
        prefHeight = 300.0
        prefWidth = 300.0
        alignment = Pos.TOP_CENTER
        fieldset(title) {
            hbox {
                alignment = Pos.CENTER
                textLabel = text("Analiza jakościowa...") {
                    addClass(Styles.heading)
                }
                progressIndicator = progressindicator()
                field {
                    hbox {
                        alignment = Pos.CENTER
                        image = imageview("green.png") {
                            hide()
                            fitHeight = 100.0
                            fitWidth = 100.0
                        }
                    }
                }
            }

        }

    }

    fun startCalculations() {
        thread {
            val qualitativeAnalysis = doQualitativeAnalysis()
            for (i in 1..33) {
                Thread.sleep(30)
            }

            Platform.runLater { textLabel.text = "Analiza finansowa..." }
            val financialAnalysis = doFinancialAnalysis()
            for (i in 34..66) {
                Thread.sleep(30)
            }

            Platform.runLater { textLabel.text = "Obliczanie wyniku..." }
            val creditScore = doFinalAnalysis(qualitativeAnalysis, financialAnalysis)
            for (i in 67..100) {
                Thread.sleep(30)
            }
            Platform.runLater {
                progressIndicator.hide()
                textLabel.text = "Twój wynik to: ${creditScore.score.round(2)}"
                if (creditScore.score < 45) {
                    image = imageview("red2.png") {
                        fitHeight = 100.0
                        fitWidth = 100.0
                    }
                } else if (creditScore.score >= 45 && creditScore.score < 75) {
                    image = imageview("warning.png") {
                        fitHeight = 100.0
                        fitWidth = 100.0
                    }
                } else if (creditScore.score >= 75) {
                    image = imageview("green.png") {
                        fitHeight = 100.0
                        fitWidth = 100.0
                    }
                }
            }

            print(creditScore.score)
            print(creditScore.memberships)
        }
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
