package pl.patrykdolata.creditscoring.fuzzy

import pl.patrykdolata.creditscoring.creditInstallmentAmount
import pl.patrykdolata.creditscoring.models.BasicClientInfo
import pl.patrykdolata.creditscoring.models.CreditInfo
import pl.patrykdolata.creditscoring.models.FinancialClientInfo
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class FuzzyResult(val score: Double, val memberships: Map<String, Double>, val inputVariables: Map<String, Double>)

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

class CreditScore(
    private val qualitativeAnalysis: FuzzyResult,
    private val financialAnalysis: FuzzyResult,
    private val creditInfo: CreditInfo
) : FuzzyFunctionBlock() {

    override fun filename() = "./fcl/credit_score.fcl"

    override fun outputVariableName() = "credit_score"

    override fun inputVariables(): Map<String, Double> {
        val creditInstallmentAmount = creditInstallmentAmount(
            creditInfo.amountValue,
            creditInfo.ownContributionValue,
            creditInfo.interestValue,
            creditInfo.installmentsNumber
        )
        val incomeInstallmentDiff = financialAnalysis.inputVariables["actual_income"]!! - creditInstallmentAmount
        return mapOf(
            "qualitative_score" to qualitativeAnalysis.score,
            "financial_score" to financialAnalysis.score,
            "income_installment_difference" to incomeInstallmentDiff
        )
    }
}
