package pl.patrykdolata.creditscoring.fuzzy

interface FuzzyModel {

    fun getFuzzyInputVariables(): Map<String, Double>
}
