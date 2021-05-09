package pl.patrykdolata.creditscoring.app

import tornadofx.*

class BasicData : View("Basic data") {
    val customer: CustomerModel by inject()

    override val root = form {
        fieldset(title) {
            field("Type") {
                combobox(customer.type, CustomerType.values().toList())
            }

            field("Name") {
                textfield(customer.name).required()
            }
        }
    }
}

class AddressInput : View("Address") {
    val customer: CustomerModel by inject()

    override val root = form {
        fieldset(title) {
            field("Zip/City") {
                textfield(customer.zip) {
                    prefColumnCount = 5
                    required()
                }
                textfield(customer.city).required()
            }
        }
    }
}