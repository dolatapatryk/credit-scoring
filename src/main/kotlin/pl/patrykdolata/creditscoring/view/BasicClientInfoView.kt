package pl.patrykdolata.creditscoring.view

import pl.patrykdolata.creditscoring.models.ClientModel
import tornadofx.*

class BasicClientInfoView : View("Podstawowe informacje") {
    val client: ClientModel by inject()

    override val root = form {
        fieldset(title) {
            field("Imię") {
                textfield(client.name).required()
            }
            field("Nazwisko") {
                textfield(client.surname).required()
            }
            field("Wiek") {
                textfield(client.age)
            }
        }
    }
}