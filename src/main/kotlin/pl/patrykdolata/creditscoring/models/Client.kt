package pl.patrykdolata.creditscoring.models

import javafx.beans.property.SimpleIntegerProperty
import tornadofx.ItemViewModel

class Client(val name: String, val surname: String, age: Int) {
    val ageProperty = SimpleIntegerProperty(this, "age", age)
}

class ClientModel : ItemViewModel<Client>() {
    val name = bind(Client::name, autocommit = true)
    val surname = bind(Client::surname, autocommit = true)
    val age = bind(Client::ageProperty, autocommit = true)
}