package pl.patrykdolata.creditscoring.fuzzy

import net.sourceforge.jFuzzyLogic.FIS
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart

class FuzzyInferenceSystem {

    fun process() {
        val filename = "./napiwek.fcl"
        val fis = FIS.load(filename, true)

        val functionBlock = fis.getFunctionBlock(null)
        JFuzzyChart.get().chart(functionBlock)

        // set variables
        // fis.setVariable("var", value)

        fis.evaluate()

        val outVariable = functionBlock.getVariable("out")
        JFuzzyChart.get().chart(outVariable, outVariable.defuzzifier, true)

        val outVariableValue = fis.getVariable("out").value
    }
}
