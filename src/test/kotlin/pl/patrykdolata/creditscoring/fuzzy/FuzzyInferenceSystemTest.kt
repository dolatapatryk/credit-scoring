package pl.patrykdolata.creditscoring.fuzzy

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import pl.patrykdolata.creditscoring.models.*
import java.time.LocalDate

internal class FuzzyInferenceSystemTest {

    val age = 17
    val maritalStatus = MaritalStatus.MARRIED
    val education = Education.MEDIUM
    val workYears = 1

    val income = 16000
    val expenses = 3500
    val contractExpiration: LocalDate? = null
    val employmentType = EmploymentType.PERMANENT

    val creditAmount = 320000
    val ownContribution = 60000
    val interest = 10.0
    val installmentsNumber = 1

    private val fuzzyInferenceSystem = FuzzyInferenceSystem()

    @Test
    fun creditScoringTest() {
        val clientInfo = BasicClientInfo(age, maritalStatus, education, workYears)
        val financialInfo = FinancialClientInfo(income, expenses, employmentType, contractExpiration)
        val creditInfo = CreditInfo(creditAmount, ownContribution, interest, installmentsNumber)

        val qualitativeAnalysis = QualitativeAnalysis(clientInfo)
        val qualitativeResult = fuzzyInferenceSystem.process(qualitativeAnalysis)

        val financialAnalysis = FinancialAnalysis(financialInfo)
        val financialResult = fuzzyInferenceSystem.process(financialAnalysis)

        val creditScoreAnalysis = CreditScore(qualitativeResult, financialResult, creditInfo)
        val creditScore = fuzzyInferenceSystem.process(creditScoreAnalysis)
        println(creditScore.score)
        println(creditScore.memberships)
        println(creditScore.inputVariables)
    }
}
