package pl.patrykdolata.creditscoring

import pl.patrykdolata.creditscoring.app.Styles
import pl.patrykdolata.creditscoring.view.CreditScoringWizard
import tornadofx.App

class CreditScoringApp: App(CreditScoringWizard::class, Styles::class)