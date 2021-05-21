package pl.patrykdolata.creditscoring.fuzzy

import pl.patrykdolata.creditscoring.models.BasicClientInfo
import pl.patrykdolata.creditscoring.models.CreditInfo
import pl.patrykdolata.creditscoring.models.FinancialClientInfo

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

class FinancialAnalysis(private val financialInfo: FinancialClientInfo, private val creditInfo: CreditInfo) :
    FuzzyFunctionBlock() {

    override fun filename() = "./fcl/financial_analysis.fcl"

    override fun outputVariableName() = "financial_score"

    override fun inputVariables(): Map<String, Double> {
        return mapOf()
    }
}
