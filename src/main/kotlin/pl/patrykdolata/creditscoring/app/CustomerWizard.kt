package pl.patrykdolata.creditscoring.app

import tornadofx.Wizard

class CustomerWizard : Wizard("Create customer", "Provide customer information") {
    val customer: CustomerModel by inject()

    init {
        add(BasicData::class)
        add(AddressInput::class)
    }
}