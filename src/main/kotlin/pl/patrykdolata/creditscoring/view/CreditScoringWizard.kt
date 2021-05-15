package pl.patrykdolata.creditscoring.view

import tornadofx.Wizard

class CreditScoringWizard : Wizard("Ocena zdolności kredytowej", "Wprowadź informację") {

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
        finishButtonTextProperty.value = "Zakończ"
    }
}
