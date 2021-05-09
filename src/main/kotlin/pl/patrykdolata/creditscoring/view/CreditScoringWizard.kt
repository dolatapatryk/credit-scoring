package pl.patrykdolata.creditscoring.view

import pl.patrykdolata.creditscoring.models.ClientModel
import tornadofx.Wizard

class CreditScoringWizard : Wizard("Ocena zdolności kredytowej", "Wprowadź informację") {
    val client: ClientModel by inject()

    init {
        add(BasicClientInfoView::class)
        showSteps = true
    }
}