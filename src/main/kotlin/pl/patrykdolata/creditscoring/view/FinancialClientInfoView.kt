package pl.patrykdolata.creditscoring.view

import pl.patrykdolata.creditscoring.models.EmploymentType
import pl.patrykdolata.creditscoring.models.FinancialClientInfoModel
import tornadofx.*

class FinancialClientInfoView : View("Zatrudnienie i finanse") {
    private val financialClientInfo: FinancialClientInfoModel by inject()

    override val root = form {
        fieldset(title) {
            field("Dochód miesięczny (netto)") {
                textfield(financialClientInfo.income) {
                    filterInput {
                        it.controlNewText.isDouble() && it.controlNewText.toDouble() > 0.0
                    }
                    validator {
                        val income = it?.toDoubleOrNull()
                        if (income == null || income == 0.0) error("Wprowadź niezerowy dochód") else null
                    }
                }
                text("zł")
            }
            field("Rodzaj zatrudnienia") {
                combobox(financialClientInfo.employmentType, EmploymentType.values().toList())
            }
        }
    }
}