package ru.otus.highload


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class ChatApplicationKt

 fun main(args: Array<String>) {
    runApplication<ChatApplicationKt>(*args)
}
