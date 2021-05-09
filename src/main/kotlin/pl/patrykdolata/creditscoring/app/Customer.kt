package pl.patrykdolata.creditscoring.app

import tornadofx.ItemViewModel

enum class CustomerType {
    PERSON, COMPANY
}

data class Customer(var name: String, var zip: String, var city: String, var type: CustomerType) {

}

class CustomerModel : ItemViewModel<Customer>() {
    val name = bind(Customer::name, autocommit = true)
    val zip = bind(Customer:: zip, autocommit = true)
    val city = bind(Customer::city, autocommit = true)
    val type = bind(Customer::type, autocommit = true)
}