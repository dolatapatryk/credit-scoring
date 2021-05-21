package pl.patrykdolata.creditscoring.fuzzy

import net.sourceforge.jFuzzyLogic.FIS
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart

class FuzzyInferenceSystem {

    fun process(fuzzyFunctionBlock: FuzzyFunctionBlock) {
        val fuzzySystem = FIS.load(fuzzyFunctionBlock.filename(), true)

        val functionBlock = fuzzySystem.getFunctionBlock(null)
//        JFuzzyChart.get().chart(functionBlock)

        // set variables
        for ((name, value) in fuzzyFunctionBlock.inputVariables()) {
            fuzzySystem.setVariable(name, value)
        }

        fuzzySystem.evaluate()

        val outVariableName = fuzzyFunctionBlock.outputVariableName()
        val outVariable = functionBlock.getVariable(outVariableName)
        JFuzzyChart.get().chart(outVariable, outVariable.defuzzifier, true)

        println(outVariable.value)
        println("Membership low: ${outVariable.getMembership("low")}")
        println("Membership medium: ${outVariable.getMembership("medium")}")
        println("Membership high: ${outVariable.getMembership("high")}")
    }
}
