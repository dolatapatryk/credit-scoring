package pl.patrykdolata.creditscoring.view

import javafx.beans.binding.DoubleBinding
import pl.patrykdolata.creditscoring.integerFilter
import pl.patrykdolata.creditscoring.integerValidator
import pl.patrykdolata.creditscoring.models.CreditInfoModel
import pl.patrykdolata.creditscoring.round
import tornadofx.*

class CreditInfoView : View("Informacje o kredycie") {
    private val creditInfo: CreditInfoModel by inject()

    override val root = form {
        fieldset(title) {
            text("Wprowadź informację jaki kredyt Cię interesuje")
            field("Kwota kredytu") {
                textfield(creditInfo.amount) {
                    filterInput { integerFilter(it) }
                    validator { integerValidator(it, this) }
                }
                text("zł")
            }
            field("Oprocentowanie") {
                textfield(creditInfo.interest) {  }
                text("%")
            }
            field("Liczba rat") {
                textfield(creditInfo.installmentsNumber) {
                    filterInput { integerFilter(it) }
                    validator { integerValidator(it, this, "Wprowadź prawidłową liczbę rat") }
                }
            }
            field("Rata kredytu") {
                textfield(creditInstallmentBinding()) {
                    isDisable = true
                }
                text("zł")
            }
        }

    }

    private fun creditInstallmentBinding(): DoubleBinding {
        return object : DoubleBinding() {
            init {
                super.bind(creditInfo.amount, creditInfo.interest, creditInfo.installmentsNumber)
            }

            override fun computeValue(): Double {
                if (creditInfo.installmentsNumber.value == 0) return 0.0
                val multiplier = 1 + (creditInfo.interest.value/100.0)
                val result = (creditInfo.amount.value * multiplier) / creditInfo.installmentsNumber.value
                return result.round(2)
            }

            override fun dispose() {
                super.unbind(creditInfo.amount, creditInfo.interest, creditInfo.installmentsNumber)
            }
        }
    }
}
