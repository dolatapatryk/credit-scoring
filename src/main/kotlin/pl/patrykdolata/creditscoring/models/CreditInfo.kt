package pl.patrykdolata.creditscoring.models

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import tornadofx.ItemViewModel

class CreditInfo(val amountValue: Int, val ownContributionValue: Int, val interestValue: Double, val installmentsNumber: Int) {
    val amount = SimpleIntegerProperty(this, "amount", amountValue)
    val ownContribution = SimpleIntegerProperty(this, "ownContribution", ownContributionValue)
    val interest = SimpleDoubleProperty(this, "interest", interestValue)
}

class CreditInfoModel : ItemViewModel<CreditInfo>() {
    val amount = bind(CreditInfo::amount, autocommit = true)
    val ownContribution = bind(CreditInfo::ownContribution, autocommit = true)
    val interest = bind(CreditInfo::interest, autocommit = true)
    val installmentsNumber = bind(CreditInfo::installmentsNumber, autocommit = true)

    override fun onCommit() {
        item = CreditInfo(amount.value, ownContribution.value, interest.value, installmentsNumber.value)
    }
}
