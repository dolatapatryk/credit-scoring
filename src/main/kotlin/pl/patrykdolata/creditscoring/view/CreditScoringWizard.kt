package pl.patrykdolata.creditscoring.view

import javafx.beans.binding.BooleanExpression
import tornadofx.Wizard

class CreditScoringWizard : Wizard("Ocena zdolności kredytowej", "Wprowadź informację") {

    override val canGoNext: BooleanExpression = currentPageComplete
    override val canFinish: BooleanExpression = allPagesComplete

    override fun onSave() {
        println("onSave")
        find<ResultDialogView> {
            openModal()
            startCalculations()
        }
    }

    init {
        add(BasicClientInfoView::class)
        add(FinancialClientInfoView::class)
        add(CreditInfoView::class)
        showSteps = true
        enableStepLinks = true
        stepsTextProperty.value = "Kroki"
        backButtonTextProperty.value = "Cofnij"
        cancelButtonTextProperty.value = "Anuluj"
        nextButtonTextProperty.value = "Dalej"
        finishButtonTextProperty.value = "Oblicz"
    }
}
