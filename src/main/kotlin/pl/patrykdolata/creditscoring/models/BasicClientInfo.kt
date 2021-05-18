package pl.patrykdolata.creditscoring.models

import tornadofx.ItemViewModel

class BasicClientInfo(val age: Int, val maritalStatus: MaritalStatus, val education: Education, val workYears: Int)

class BasicClientInfoModel : ItemViewModel<BasicClientInfo>() {
    val age = bind(BasicClientInfo::age, autocommit = true)
    val maritalStatus = bind(BasicClientInfo::maritalStatus, autocommit = true)
    val education = bind(BasicClientInfo::education, autocommit = true)
    val workYears = bind(BasicClientInfo::workYears, autocommit = true)

    override fun onCommit() {
        item = BasicClientInfo(age.value, maritalStatus.value, education.value, workYears.value)
    }
}

enum class MaritalStatus(private val status: String, private val fuzzyValue: Int) {
    SINGLE("Singiel/ka", 0),
    MARRIED("Zamężny/a", 1),
    DIVORCED("Rozwiedziony/a", 2),
    WIDOWER("Wdowiec/Wdowa", 3);

    fun fuzzyValue() = fuzzyValue

    override fun toString(): String = status
}

enum class Education(private val education: String, private val fuzzyValue: Int) {
    BASIC("Podstawowe", 0),
    VOCATIONAL("Zawodowe", 1),
    MEDIUM("Średnie", 2),
    HIGH("Wyższe", 3);

    fun fuzzyValue() = fuzzyValue

    override fun toString(): String = education
}
