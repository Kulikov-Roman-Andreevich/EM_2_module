import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.milliseconds

fun main(args: Array<String>): Unit = runBlocking {
    flowOf(1, 2, 3, 4, 5).onEach { delay(300) }
        .throttleFirst(500.milliseconds)
        .flowOn(Dispatchers.IO)
        .onEach { println(it) }
        .launchIn(this)

    flowOf(1, 2, 3, 4, 5).onEach { delay(300) }
        .throttleLatest(500.milliseconds)
        .flowOn(Dispatchers.IO)
        .onEach { println(it) }
        .launchIn(this)
}