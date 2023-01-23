package ru.otus.highload

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class SocialNetworkDemoApplication

 fun main(args: Array<String>) {
    runApplication<SocialNetworkDemoApplication>(*args)
}
