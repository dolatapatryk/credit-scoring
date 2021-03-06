package pl.patrykdolata.creditscoring.fuzzy

import net.sourceforge.jFuzzyLogic.FIS
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart

class FuzzyInferenceSystem {

    fun process(fuzzyFunctionBlock: FuzzyFunctionBlock, showCharts: Boolean = false): FuzzyResult {
        val fuzzySystem = FIS.load(fuzzyFunctionBlock.filename(), true)

        val functionBlock = fuzzySystem.getFunctionBlock(null)
        if (showCharts) JFuzzyChart.get().chart(functionBlock)

        // set variables
        for ((name, value) in fuzzyFunctionBlock.inputVariables()) {
            fuzzySystem.setVariable(name, value)
        }

        fuzzySystem.evaluate()

        val outVariableName = fuzzyFunctionBlock.outputVariableName()
        val outVariable = functionBlock.getVariable(outVariableName)
        if (showCharts) JFuzzyChart.get().chart(outVariable, outVariable.defuzzifier, true)

        println(outVariable.value)
        println("Membership low: ${outVariable.getMembership("low")}")
        println("Membership medium: ${outVariable.getMembership("medium")}")
        println("Membership high: ${outVariable.getMembership("high")}")
        val memberships: Map<String, Double> = mapOf(
            "low" to outVariable.getMembership("low"),
            "medium" to outVariable.getMembership("medium"),
            "high" to outVariable.getMembership("high")
        )
        return FuzzyResult(outVariable.value, memberships, fuzzyFunctionBlock.inputVariables())
    }
}
