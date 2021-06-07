package pl.patrykdolata.creditscoring.view

import javafx.beans.binding.BooleanExpression
import javafx.beans.binding.DoubleBinding
import javafx.beans.binding.IntegerBinding
import pl.patrykdolata.creditscoring.*
import pl.patrykdolata.creditscoring.models.CreditInfoModel
import tornadofx.*

class CreditInfoView : View("Informacje o kredycie") {
    private val creditInfo: CreditInfoModel by inject()

//    override val complete: BooleanExpression = creditInfo.valid(
//        creditInfo.amount,
//        creditInfo.ownContribution,
//        creditInfo.interest,
//        creditInfo.installmentsNumber
//    )

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
            field("Wkład własny") {
                textfield(creditInfo.ownContribution) {
                    filterInput { integerFilterWithZero(it) }
                    validator { integerValidatorWithZero(it, this) }
                }
                text("zł")
            }
            field("Kwota do spłaty") {
                textfield(amountToPayBinding()) {
                    isDisable = true
                }
                text("zł")
            }
            field("Oprocentowanie") {
                textfield(creditInfo.interest) { }
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

    private fun amountToPayBinding(): IntegerBinding {
        return object : IntegerBinding() {
            init {
                super.bind(creditInfo.amount, creditInfo.ownContribution)
            }

            override fun computeValue(): Int {
                return creditInfo.amount.value - creditInfo.ownContribution.value
            }

            override fun dispose() {
                super.unbind(creditInfo.amount, creditInfo.ownContribution)
            }
        }
    }

    private fun creditInstallmentBinding(): DoubleBinding {
        return object : DoubleBinding() {
            init {
                super.bind(creditInfo.amount, creditInfo.interest, creditInfo.installmentsNumber)
            }

            override fun computeValue(): Double {
                return creditInstallmentAmount(
                    creditInfo.amount.value,
                    creditInfo.ownContribution.value,
                    creditInfo.interest.value,
                    creditInfo.installmentsNumber.value
                )
            }

            override fun dispose() {
                super.unbind(creditInfo.amount, creditInfo.interest, creditInfo.installmentsNumber)
            }
        }
    }
}
