package ru.otus.highload.posts.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.*

const val POST_EXECUTOR_NAME = "posts-executor"

@Configuration
class PostAsyncExecutorConfig(
    private val postAsyncExecutorSettings: PostAsyncExecutorSettings
) {

    @Bean(POST_EXECUTOR_NAME)
    fun postExecutor(): Executor =
        ThreadPoolTaskExecutor().apply {
            setThreadNamePrefix("Async-posts-")
            // минимальное количество потоков, которое реализация будет пытаться поддерживать, даже если нет задач для выполнения
            // и не будет создавать новые потоки больше этого числа, если рабочая очередь не заполнена
            corePoolSize = postAsyncExecutorSettings.corePoolSize
            // верхняя граница числа потоков пула, которые могут быть активны одновременно
            maxPoolSize = postAsyncExecutorSettings.maxPoolSize
            // используется SynchronousQueue в случае нуля
            queueCapacity = 0
            // период поддержания потока в активном состоянии
            // поток, который простаивал дольше этого числа будет терминирован, ЕСЛИ текущий размер пула превысит ядерный размер
            keepAliveSeconds = postAsyncExecutorSettings.keepAliveSeconds
            // обязательный флаг, вызывающий метод awaitTermination при завершении работы сервиса
            setWaitForTasksToCompleteOnShutdown(true)
            // таймаут для завершения всех потоков
            setAwaitTerminationSeconds(postAsyncExecutorSettings.awaitTerminationTimeSeconds)
            initialize()
        }
}


