package pl.patrykdolata.creditscoring.models

import tornadofx.ItemViewModel

class BasicClientInfo(val name: String, val surname: String, val age: String, val sex: Sex,
                      val maritalStatus: MaritalStatus, val education: Education)

class BasicClientInfoModel : ItemViewModel<BasicClientInfo>() {
    val name = bind(BasicClientInfo::name, autocommit = true)
    val surname = bind(BasicClientInfo::surname, autocommit = true)
    val age = bind(BasicClientInfo::age, autocommit = true)
    val sex = bind(BasicClientInfo::sex, autocommit = true)
    val maritalStatus = bind(BasicClientInfo::maritalStatus, autocommit = true)
    val education = bind(BasicClientInfo::education, autocommit = true)
}

enum class Sex(private val sex: String, private val fuzzyValue: Int) {
    MALE("Mężczyzna", 0),
    FEMALE("Kobieta", 1);

    override fun toString(): String = sex
}

enum class MaritalStatus(private val status: String, private val fuzzyValue: Int) {
    SINGLE("Singiel/ka", 0),
    MARRIED("Zamężny/a", 1),
    DIVORCED("Rozwiedziony/a", 2),
    WIDOWER("Wdowiec/Wdowa", 3);

    override fun toString(): String = status
}

enum class Education(private val education: String, private val fuzzyValue: Int) {
    BASIC("Podstawowe", 0),
    VOCATIONAL("Zawodowe", 1),
    MEDIUM("Średnie", 2),
    HIGH("Wyższe", 3);

    override fun toString(): String = education
}
