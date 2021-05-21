package pl.patrykdolata.creditscoring.view

import pl.patrykdolata.creditscoring.app.Styles
import pl.patrykdolata.creditscoring.fuzzy.FuzzyInferenceSystem
import pl.patrykdolata.creditscoring.fuzzy.QualitativeAnalysis
import pl.patrykdolata.creditscoring.integerFilter
import pl.patrykdolata.creditscoring.integerValidator
import pl.patrykdolata.creditscoring.models.BasicClientInfoModel
import pl.patrykdolata.creditscoring.models.Education
import pl.patrykdolata.creditscoring.models.MaritalStatus
import tornadofx.*

class BasicClientInfoView : View("Podstawowe informacje") {
    private val basicClientInfo: BasicClientInfoModel by inject()

    private val fuzzyInferenceSystem: FuzzyInferenceSystem = FuzzyInferenceSystem()

    override val root = form {
        fieldset(title) {
            field("Wiek") {
                textfield(basicClientInfo.age) {
                    filterInput { integerFilter(it) }
                    validator { integerValidator(it, this, "Wprowadź prawidłowy wiek") }
                }
            }
            field("Stan cywilny") {
                combobox(basicClientInfo.maritalStatus, MaritalStatus.values().toList()).required()
                addClass(Styles.w100)
            }
            field("Wykształcenie") {
                combobox(basicClientInfo.education, Education.values().toList()).required()
            }
            field("Staż pracy (w pełnych latach)") {
                textfield(basicClientInfo.workYears) {
                    filterInput { integerFilter(it) }
                    validator { integerValidator(it, this, "Wprowadź prawidłowy staż pracy") }
                }
            }
            button("xd") {
                action {
                    basicClientInfo.commit()
                    if (basicClientInfo.item != null) {
                        val qualitativeAnalysis = QualitativeAnalysis(basicClientInfo.item)
                        fuzzyInferenceSystem.process(qualitativeAnalysis)
                    }
                }
            }
            button("dialog") {
                action {
                    find<ResultDialogView> {
                        openModal()
                    }
                }
            }
        }
    }
}
