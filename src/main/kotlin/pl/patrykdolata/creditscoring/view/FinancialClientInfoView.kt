package pl.patrykdolata.creditscoring.view

import pl.patrykdolata.creditscoring.app.Styles
import pl.patrykdolata.creditscoring.integerFilter
import pl.patrykdolata.creditscoring.integerValidator
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
                    filterInput { integerFilter(it) }
                    validator { integerValidator(it, this) }
                }
                text("zł")
            }
            field("Miesięczne wydatki (czynsz, opłaty itp.)") {
                textfield(financialClientInfo.expenses) {
                    filterInput { integerFilter(it) }
                    validator { integerValidator(it, this) }
                }
                text("zł")
            }
            field("Faktyczny przychód") {
                textfield(financialClientInfo.income - financialClientInfo.expenses) {
                    isDisable = true
                }
                text("zł")
            }
            field("Forma zatrudnienia") {
                combobox(financialClientInfo.employmentType, EmploymentType.values().toList())
            }
            field("Umowa do:\n(czas nieokreślony - zostaw puste)") {
                datepicker(financialClientInfo.contractEndDate) {
                    value = null
                    isShowWeekNumbers = false
                }
                text("✕") {
                    onLeftClick { financialClientInfo.contractEndDate.value = null }
                    addClass(Styles.clearButton)
                }
            }
        }
    }
}
