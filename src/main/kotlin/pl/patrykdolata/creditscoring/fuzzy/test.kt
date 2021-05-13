package pl.patrykdolata.creditscoring.fuzzy

import org.springframework.stereotype.Component

interface BaseService {
    fun getMessage(): String
}

interface FirstService : BaseService

interface SecondService : BaseService

@Component
internal class FirstServiceImpl : FirstService {
    override fun getMessage(): String {
        return "first"
    }
}

@Component
internal class SecondServiceImpl : SecondService {
    override fun getMessage(): String {
        return "second"
    }
}