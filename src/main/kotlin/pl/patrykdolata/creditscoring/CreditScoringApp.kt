package pl.patrykdolata.creditscoring

import pl.patrykdolata.creditscoring.app.Styles
import pl.patrykdolata.creditscoring.view.CreditScoringWizard
import tornadofx.App
import tornadofx.reloadStylesheetsOnFocus
import java.util.*

class CreditScoringApp : App(CreditScoringWizard::class, Styles::class) {

    override fun init() {
        Locale.setDefault(Locale("pl", "PL"))
        reloadStylesheetsOnFocus()
    }
}
