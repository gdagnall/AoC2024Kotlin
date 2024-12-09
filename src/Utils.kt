import java.math.BigInteger
import java.security.MessageDigest
import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

val isDebugging = false
val isLogging = true

fun Any.log(message: () -> String) {
    println("In log for message = $message")
    if (isLogging || isDebugging) {
        println("${this::class.java.simpleName} $message")
    }
}

fun getTimeInMSec(): Long =  Calendar.getInstance().getTimeInMillis()

fun printDurations(startTime: Long, part1Time: Long, part2Time: Long) {
    if (isLogging) {
        val duration1 = part1Time - startTime
        println()
        println("Time for part 1 = $duration1 milliseconds")
        println("Time for part 2 = ${part2Time - part1Time} milliseconds")
        println("Total time = ${part2Time - startTime} milliseconds")
    }
}
