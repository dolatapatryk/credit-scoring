package pl.patrykdolata.creditscoring.view

import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.control.ProgressIndicator
import javafx.scene.image.ImageView
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import pl.patrykdolata.creditscoring.fuzzy.*
import pl.patrykdolata.creditscoring.models.BasicClientInfoModel
import pl.patrykdolata.creditscoring.models.CreditInfoModel
import pl.patrykdolata.creditscoring.models.FinancialClientInfoModel
import pl.patrykdolata.creditscoring.round
import tornadofx.*
import kotlin.concurrent.thread

class ResultDialogView : View("Rezultat") {

    companion object {
        const val DELAY: Long = 20
    }

    private val basicClientInfo: BasicClientInfoModel by inject()
    private val financialClientInfo: FinancialClientInfoModel by inject()
    private val creditInfo: CreditInfoModel by inject()
    private val fuzzyInferenceSystem = FuzzyInferenceSystem()

    private lateinit var progressIndicator: ProgressIndicator
    private lateinit var textLabel: Text
    private lateinit var image: ImageView
    private lateinit var resultText: Text

    override val root = form {
        prefHeight = 300.0
        prefWidth = 345.0
        alignment = Pos.TOP_CENTER
        fieldset(title) {
            hbox {
                alignment = Pos.CENTER
                textLabel = text("Analiza jakościowa...") {
                    style {
                        fontWeight = FontWeight.BOLD
                    }
                }
                progressIndicator = progressindicator {
                    style {
                        paddingVertical = 10
                    }
                }
                field {
                    hbox {
                        alignment = Pos.CENTER
                        image = imageview("green.png") {
                            hide()
                            fitHeight = 100.0
                            fitWidth = 100.0
                        }
                        resultText = text("result") { hide() }
                    }
                }
            }

        }

    }

    fun startCalculations() {
        resetView()
        thread {
            val qualitativeAnalysis = doQualitativeAnalysis()
            dummyDelay()

            Platform.runLater { textLabel.text = "Analiza finansowa..." }
            val financialAnalysis = doFinancialAnalysis()
            dummyDelay()

            Platform.runLater { textLabel.text = "Obliczanie wyniku..." }
            val creditScore = doFinalAnalysis(qualitativeAnalysis, financialAnalysis)
            dummyDelay()

            Platform.runLater {
                progressIndicator.hide()
                textLabel.text = "Twój wynik to: ${creditScore.score.round(2)} / 100"
                val imageName = getImageName(creditScore.score)
                image = imageview(imageName) {
                    fitHeight = 100.0
                    fitWidth = 100.0
                }
                prepareResultText(creditScore)
            }
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
        return fuzzyInferenceSystem.process(creditScore, true)
    }

    private fun resetView() {
        image.hide()
        progressIndicator.show()
        textLabel.text = "Analiza jakościowa..."
        resultText.hide()
    }

    private fun getImageName(score: Double): String {
        return if (score < 45) "red2.png" else if (score >= 45 && score < 75) "warning.png" else "green.png"
    }

    private fun dummyDelay() {
        for (i in 1..33) {
            Thread.sleep(DELAY)
        }
    }

    private fun prepareResultText(score: FuzzyResult) {
        val text = """
            |
            |${getResultText(score.score)}
            |
            |Stopień przynależności do klas:
            |wynik Niski: ${score.memberships["low"]?.round(2)}
            |wynik Średni: ${score.memberships["medium"]?.round(2)}
            |wynik Wysoki: ${score.memberships["high"]?.round(2)}
        """.trimMargin()
        resultText = text(text)
    }

    private fun getResultText(score: Double): String {
        return if (score < 45) "Niestety. Bardzo mało prawdopodobne, żebyś\notrzymał/a wybrany kredyt."
        else if (score >= 45 && score < 75) "Twój wynik jest średni. Rozważ zmianę parametrów\nkredytu by zwiększyć szansę na jego uzyskanie."
        else "Gratulacje! Bardzo prawdopodobne, że\notrzymasz wybrany kredyt."
    }
}
