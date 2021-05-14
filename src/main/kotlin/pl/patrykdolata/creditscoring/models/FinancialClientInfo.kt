package pl.patrykdolata.creditscoring.models

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ItemViewModel
import java.time.LocalDate

class FinancialClientInfo(incomeValue: Int, expensesValue: Int, val employmentType: EmploymentType,
                          endOfContract: LocalDate? = null) {
    val income = SimpleIntegerProperty(this, "income", incomeValue)
    val expenses = SimpleIntegerProperty(this, "expenses", expensesValue)
    val contractEndDate = SimpleObjectProperty<LocalDate>()
}

class FinancialClientInfoModel : ItemViewModel<FinancialClientInfo>() {
    val income = bind(FinancialClientInfo::income, autocommit = true)
    val expenses = bind(FinancialClientInfo::expenses, autocommit = true)
    val employmentType = bind(FinancialClientInfo::employmentType, autocommit = true)
    val contractEndDate = bind(FinancialClientInfo::contractEndDate, autocommit = true)
}

enum class EmploymentType(private val type: String) {
    PERMANENT("Umowa o pracę"),
    B2B("B2B"),
    CONTRACT_OF_MANDATE("Umowa zlecenie");

    override fun toString(): String = type
}

enum class EmploymentPeriod(private val monthPeriod: Int) {

}
