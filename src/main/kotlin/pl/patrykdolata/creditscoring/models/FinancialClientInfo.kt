package pl.patrykdolata.creditscoring.models

import javafx.beans.property.SimpleDoubleProperty
import tornadofx.ItemViewModel

class FinancialClientInfo(incomeValue: Double, val employmentType: EmploymentType) {
    val income = SimpleDoubleProperty(this, "income", incomeValue)
}

class FinancialClientInfoModel : ItemViewModel<FinancialClientInfo>() {
    val income = bind(FinancialClientInfo::income, autocommit = true)
    val employmentType = bind(FinancialClientInfo::employmentType, autocommit = true)
}

enum class EmploymentType(private val type: String) {
    PERMANENT("Umowa o pracę"),
    B2B("B2B"),
    CONTRACT_OF_MANDATE("Umowa zlecenie");

    override fun toString(): String = type
}