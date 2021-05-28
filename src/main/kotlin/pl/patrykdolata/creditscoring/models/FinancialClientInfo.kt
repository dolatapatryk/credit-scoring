package pl.patrykdolata.creditscoring.models

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ItemViewModel
import java.time.LocalDate

class FinancialClientInfo(val incomeValue: Int, val expensesValue: Int, val employmentType: EmploymentType,
                          val endOfContract: LocalDate? = null) {
    val income = SimpleIntegerProperty(this, "income", incomeValue)
    val expenses = SimpleIntegerProperty(this, "expenses", expensesValue)
    val contractEndDate = SimpleObjectProperty<LocalDate>(this, "contractEndDate", endOfContract)
}

class FinancialClientInfoModel : ItemViewModel<FinancialClientInfo>() {
    val income = bind(FinancialClientInfo::income, autocommit = true)
    val expenses = bind(FinancialClientInfo::expenses, autocommit = true)
    val employmentType = bind(FinancialClientInfo::employmentType, autocommit = true)
    val contractEndDate = bind(FinancialClientInfo::contractEndDate, autocommit = true)

    override fun onCommit() {
        item = FinancialClientInfo(income.value, expenses.value, employmentType.value, contractEndDate.value)
    }
}

enum class EmploymentType(private val type: String, private val fuzzyValue: Int) {
    PERMANENT("Umowa o pracę", 0),
    B2B("B2B", 1),
    CONTRACT_OF_MANDATE("Umowa zlecenie", 2);

    fun fuzzyValue() = fuzzyValue

    override fun toString(): String = type
}
