package pl.patrykdolata.creditscoring.models

import javafx.beans.property.SimpleIntegerProperty
import tornadofx.ItemViewModel

class FinancialClientInfo(incomeValue: Int, expensesValue: Int, val employmentType: EmploymentType) {
    val income = SimpleIntegerProperty(this, "income", incomeValue)
    val expenses = SimpleIntegerProperty(this, "expenses", expensesValue)
}

class FinancialClientInfoModel : ItemViewModel<FinancialClientInfo>() {
    val income = bind(FinancialClientInfo::income, autocommit = true)
    val expenses = bind(FinancialClientInfo::expenses, autocommit = true)
    val employmentType = bind(FinancialClientInfo::employmentType, autocommit = true)
}

enum class EmploymentType(private val type: String) {
    PERMANENT("Umowa o pracę"),
    B2B("B2B"),
    CONTRACT_OF_MANDATE("Umowa zlecenie");

    override fun toString(): String = type
}