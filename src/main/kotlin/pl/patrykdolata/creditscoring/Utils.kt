package pl.patrykdolata.creditscoring

import javafx.scene.control.TextFormatter
import tornadofx.ValidationContext
import tornadofx.ValidationMessage
import tornadofx.isInt

fun integerFilter(text: TextFormatter.Change): Boolean =
    text.controlNewText.isInt() && text.controlNewText.toInt() > 0

fun integerFilterWithZero(text: TextFormatter.Change): Boolean =
    text.controlNewText.isInt()

fun integerValidator(text: String?, context: ValidationContext, message: String = "Wprowadź prawidłową kwotę")
        : ValidationMessage? {
    val amount = text?.toIntOrNull()
    return if (amount == null || amount == 0) context.error(message) else null
}

fun integerValidatorWithZero(text: String?, context: ValidationContext, message: String = "Wprowadź prawidłową kwotę")
        : ValidationMessage? {
    val amount = text?.toIntOrNull()
    return if (amount == null) context.error(message) else null
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}

fun creditInstallmentAmount(
    creditAmount: Int,
    ownContribution: Int,
    interest: Double,
    installmentsNumber: Int
): Double {
    val multiplier = 1 + (interest / 100.0)
    val actualCreditAmount = (creditAmount - ownContribution) * multiplier
    if (installmentsNumber == 0) return actualCreditAmount
    val result = actualCreditAmount / installmentsNumber
    return result.round(2)
}
