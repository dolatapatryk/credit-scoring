package pl.patrykdolata.creditscoring

import javafx.scene.control.TextFormatter
import tornadofx.ValidationContext
import tornadofx.ValidationMessage
import tornadofx.isInt

fun integerFilter(text: TextFormatter.Change): Boolean =
    text.controlNewText.isInt() && text.controlNewText.toInt() > 0

fun integerValidator(text: String?, context: ValidationContext, message: String = "Wprowadź prawidłową kwotę")
        : ValidationMessage? {
    val amount = text?.toIntOrNull()
    return if (amount == null || amount == 0) context.error(message) else null
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10}
    return kotlin.math.round(this * multiplier) / multiplier
}
