import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.time.Duration

fun <T> Flow<T>.throttleFirst(duration: Duration): Flow<T> {
    var job: Job = Job().apply { complete() }

    return onCompletion { job.cancel() }.run {
        flow {
            coroutineScope {
                collect { value ->
                    if (!job.isActive) {
                        emit(value)
                        job = launch { delay(duration.inWholeMilliseconds) }
                    }
                }
            }
        }
    }
}

fun <T> Flow<T>.throttleLatest(duration: Duration): Flow<T> = this
    .conflate()
    .transform {
        emit(it)
        delay(duration.inWholeMilliseconds)
    }