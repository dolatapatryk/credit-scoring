package pl.patrykdolata.creditscoring.view

import javafx.scene.control.TextFormatter
import pl.patrykdolata.creditscoring.models.EmploymentType
import pl.patrykdolata.creditscoring.models.FinancialClientInfoModel
import tornadofx.*

class FinancialClientInfoView : View("Zatrudnienie i finanse") {
    private val financialClientInfo: FinancialClientInfoModel by inject()

    override val root = form {
        fieldset(title) {
            text("Kwoty podawaj do pełnych złotówek")
            field("Dochód miesięczny (netto)") {
                textfield(financialClientInfo.income) {
                    filterInput { amountFilter(it) }
                    validator { amountValidator(it, this) }
                }
                text("zł")
            }
            field("Miesięczne wydatki") {
                textfield(financialClientInfo.expenses) {
                    filterInput { amountFilter(it) }
                    validator { amountValidator(it, this) }
                }
                text("zł")
            }
            field("Faktyczny przychód") {
                textfield(financialClientInfo.income - financialClientInfo.expenses) {
                    isDisable = true
                }
                text("zł")
            }
            field("Rodzaj zatrudnienia") {
                combobox(financialClientInfo.employmentType, EmploymentType.values().toList())
            }
        }
    }

    private fun amountFilter(text: TextFormatter.Change): Boolean =
        text.controlNewText.isInt() && text.controlNewText.toInt() > 0

    private fun amountValidator(text: String?, context: ValidationContext): ValidationMessage? {
        val amount = text?.toIntOrNull()
        return if (amount == null || amount == 0) context.error("Wprowadź prawidłową kwotę") else null
    }

    fun xd() {

    }
}