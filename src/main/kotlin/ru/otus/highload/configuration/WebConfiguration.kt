package ru.otus.highload.configuration

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.time.format.DateTimeFormatter

private const val DATE_FORMAT = "yyyy-MM-dd"
private const val DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS"

@Configuration
class WebConfiguration : WebMvcConfigurer {

    @Bean
    fun localeResolver(): LocaleResolver = AcceptHeaderLocaleResolver().apply {
        defaultLocale = AdditionalLocale.RUSSIAN
    }

    @Bean
    fun jackson2ObjectMapperBuilder() = Jackson2ObjectMapperBuilderCustomizer { builder ->
        with(builder) {
            featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            featuresToEnable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
            serializationInclusion(JsonInclude.Include.NON_NULL)
            simpleDateFormat(DATE_TIME_FORMAT)
            serializers(
                LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
            )
        }
    }
}
