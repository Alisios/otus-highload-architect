package ru.otus.highload.users.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.*

const val USER_EXECUTOR_NAME = "users-executor"

@Configuration
class UserAsyncExecutorConfig(
    private val userAsyncExecutorSettings: UserAsyncExecutorSettings
) {

    @Bean(USER_EXECUTOR_NAME)
    fun userExecutor(): Executor =
        ThreadPoolTaskExecutor().apply {
            setThreadNamePrefix("Async-users-")
            // минимальное количество потоков, которое реализация будет пытаться поддерживать, даже если нет задач для выполнения
            // и не будет создавать новые потоки больше этого числа, если рабочая очередь не заполнена
            corePoolSize = userAsyncExecutorSettings.corePoolSize
            // верхняя граница числа потоков пула, которые могут быть активны одновременно
            maxPoolSize = userAsyncExecutorSettings.maxPoolSize
            // используется SynchronousQueue в случае нуля
            queueCapacity = 0
            // период поддержания потока в активном состоянии
            // поток, который простаивал дольше этого числа будет терминирован, ЕСЛИ текущий размер пула превысит ядерный размер
            keepAliveSeconds = userAsyncExecutorSettings.keepAliveSeconds
            // обязательный флаг, вызывающий метод awaitTermination при завершении работы сервиса
            setWaitForTasksToCompleteOnShutdown(true)
            // таймаут для завершения всех потоков
            setAwaitTerminationSeconds(userAsyncExecutorSettings.awaitTerminationTimeSeconds)
            initialize()
        }
}


