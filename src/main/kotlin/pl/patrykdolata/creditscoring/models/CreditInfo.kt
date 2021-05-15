package pl.patrykdolata.creditscoring.models

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import tornadofx.ItemViewModel

class CreditInfo(amountValue: Int, interestValue: Double, val installmentsNumber: Int) {
    val amount = SimpleIntegerProperty(this, "amount", amountValue)
    val interest = SimpleDoubleProperty(this, "interest", interestValue)
}

class CreditInfoModel : ItemViewModel<CreditInfo>() {
    val amount = bind(CreditInfo::amount, autocommit = true)
    val interest = bind(CreditInfo::interest, autocommit = true)
    val installmentsNumber = bind(CreditInfo::installmentsNumber, autocommit = true)
}
