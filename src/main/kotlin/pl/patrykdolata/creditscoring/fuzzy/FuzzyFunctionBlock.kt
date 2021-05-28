package pl.patrykdolata.creditscoring.fuzzy

import pl.patrykdolata.creditscoring.models.BasicClientInfo
import pl.patrykdolata.creditscoring.models.FinancialClientInfo
import java.time.LocalDate
import java.time.temporal.ChronoUnit

sealed class FuzzyFunctionBlock {

    abstract fun inputVariables(): Map<String, Double>;

    abstract fun filename(): String

    abstract fun outputVariableName(): String
}

class QualitativeAnalysis(private val model: BasicClientInfo) : FuzzyFunctionBlock() {

    override fun filename() = "./fcl/qualitative_analysis.fcl"

    override fun outputVariableName() = "qualitative_score"

    override fun inputVariables(): Map<String, Double> {
        return mapOf(
            "age" to model.age.toDouble(),
            "marital_status" to model.maritalStatus.fuzzyValue().toDouble(),
            "education" to model.education.fuzzyValue().toDouble(),
            "work_years" to model.workYears.toDouble(),
        )
    }
}

class FinancialAnalysis(private val model: FinancialClientInfo) :
    FuzzyFunctionBlock() {

    override fun filename() = "./fcl/financial_analysis.fcl"

    override fun outputVariableName() = "financial_score"

    override fun inputVariables(): Map<String, Double> {
        val incomeOutcomeDiff = model.income.value - model.expenses.value
        val actualIncome: Int = if (incomeOutcomeDiff < 0) 0 else incomeOutcomeDiff
        val contractExpiration =
            if (model.contractEndDate.value == null) 48
            else ChronoUnit.MONTHS.between(LocalDate.now(), model.contractEndDate.value)
        return mapOf(
            "actual_income" to actualIncome.toDouble(),
            "employment_type" to model.employmentType.fuzzyValue().toDouble(),
            "contract_expiration" to contractExpiration.toDouble()
        )
    }
}
