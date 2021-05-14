package pl.patrykdolata.creditscoring

import javafx.application.Application
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ConfigurableApplicationContext
import pl.patrykdolata.creditscoring.app.Styles
import pl.patrykdolata.creditscoring.view.CreditScoringWizard
import tornadofx.App
import tornadofx.DIContainer
import tornadofx.FX
import java.util.*
import kotlin.reflect.KClass

@SpringBootApplication
open class CreditScoringApp: App(CreditScoringWizard::class, Styles::class) {
    private lateinit var context: ConfigurableApplicationContext

    override fun init() {
        context = SpringApplication.run(this.javaClass)
        context.autowireCapableBeanFactory.autowireBean(this)

        FX.dicontainer = object : DIContainer {
            override fun <T : Any> getInstance(type: KClass<T>): T = context.getBean(type.java)
            override fun <T : Any> getInstance(type: KClass<T>, name: String): T = context.getBean(name, type.java)
        }
        Locale.setDefault(Locale("pl", "PL"))
    }

    override fun stop() {
        super.stop()
        context.close()
    }
}

fun main(args: Array<String>) {
    Application.launch(CreditScoringApp::class.java, *args)
}
