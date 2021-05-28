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

enum class EmploymentType(private val type: String, private val fuzzyValue: Int) {
    PERMANENT("Umowa o pracę", 0),
    B2B("B2B", 1),
    CONTRACT_OF_MANDATE("Umowa zlecenie", 2);

    fun fuzzyValue() = fuzzyValue

    override fun toString(): String = type
}
