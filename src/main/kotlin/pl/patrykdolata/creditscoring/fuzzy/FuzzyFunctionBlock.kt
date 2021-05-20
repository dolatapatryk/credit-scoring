package pl.patrykdolata.creditscoring.fuzzy

import pl.patrykdolata.creditscoring.models.BasicClientInfo
import tornadofx.ItemViewModel

sealed class FuzzyFunctionBlock<T>(model: ItemViewModel<T>) {
    val inputVariables: MutableMap<String, Double> = mutableMapOf()

    init {
        setVariableValues(model)
    }

    private fun setVariableValues(model: ItemViewModel<T>) {
        setInputVariableValues(model)
    }

    protected abstract fun setInputVariableValues(model: ItemViewModel<T>);

    abstract fun filename(): String

    abstract fun outputVariableName(): String
}

class QualitativeAnalysis(model: ItemViewModel<BasicClientInfo>) : FuzzyFunctionBlock<BasicClientInfo>(model) {

    override fun setInputVariableValues(model: ItemViewModel<BasicClientInfo>) {
        with(model.item) {
            inputVariables["age"] = age.toDouble()
            inputVariables["marital_status"] = maritalStatus.fuzzyValue().toDouble()
            inputVariables["education"] = education.fuzzyValue().toDouble()
            inputVariables["work_years"] = workYears.toDouble()
        }
    }

    override fun filename() = "./fcl/qualitative_analysis.fcl"

    override fun outputVariableName() = "qualitative_score"
}
