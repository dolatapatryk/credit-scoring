package pl.patrykdolata.creditscoring.fuzzy

import pl.patrykdolata.creditscoring.models.BasicClientInfo

sealed class FuzzyFunctionBlock(private val model: FuzzyModel) {

    fun inputVariables(): Map<String, Double> = model.getFuzzyInputVariables()

    abstract fun filename(): String

    abstract fun outputVariableName(): String
}

class QualitativeAnalysis(model: BasicClientInfo) : FuzzyFunctionBlock(model) {

    override fun filename() = "./fcl/qualitative_analysis.fcl"

    override fun outputVariableName() = "qualitative_score"
}
