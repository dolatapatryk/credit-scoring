package pl.patrykdolata.creditscoring.view

import pl.patrykdolata.creditscoring.models.BasicClientInfoModel
import pl.patrykdolata.creditscoring.models.Education
import pl.patrykdolata.creditscoring.models.MaritalStatus
import pl.patrykdolata.creditscoring.models.Sex
import tornadofx.*

class BasicClientInfoView : View("Podstawowe informacje") {
    private val basicClientInfo: BasicClientInfoModel by inject()

    override val root = form {
        fieldset(title) {
            field("Imię") {
                textfield(basicClientInfo.name).required(message = "Wprowadź imię")
            }
            field("Nazwisko") {
                textfield(basicClientInfo.surname).required(message = "Wprowadź nazwisko")
            }
            field("Wiek") {
                textfield(basicClientInfo.age) {
                    filterInput {
                        it.controlNewText.isInt() && it.controlNewText.toInt() > 0 && it.controlNewText.toInt() < 120
                    }
                    validator {
                        val age = it?.toIntOrNull()
                        if (age == null || age == 0) error("Wprowadź prawidłowy wiek") else null
                    }
                }
            }
            field("Płeć") {
                combobox(basicClientInfo.sex, Sex.values().toList()).required()
            }
            field("Stan cywilny") {
                combobox(basicClientInfo.maritalStatus, MaritalStatus.values().toList()).required()
            }
            field("Wykształcenie") {
                combobox(basicClientInfo.education, Education.values().toList()).required()
            }
        }
    }
}
