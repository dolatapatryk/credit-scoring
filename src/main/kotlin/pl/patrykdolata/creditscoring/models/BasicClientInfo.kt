package pl.patrykdolata.creditscoring.models

import tornadofx.ItemViewModel

class BasicClientInfo(val name: String, val surname: String, val age: String, val sex: Sex)

class BasicClientInfoModel : ItemViewModel<BasicClientInfo>() {
    val name = bind(BasicClientInfo::name, autocommit = true)
    val surname = bind(BasicClientInfo::surname, autocommit = true)
    val age = bind(BasicClientInfo::age, autocommit = true)
    val sex = bind(BasicClientInfo::sex, autocommit = true)
}

enum class Sex(private val sex: String) {
    MALE("Mężczyzna"), FEMALE("Kobieta");

    override fun toString(): String = sex
}