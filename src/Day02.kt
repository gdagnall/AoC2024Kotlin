fun main() {
    fun List<Int>.isListSafe(): Boolean {
        val pairs = this.zipWithNext()
        val allIncreasing = pairs.all { pair ->
            (pair.second - pair.first) in 1 ..3
        }
        val allDecreasing = pairs.all { pair ->
            (pair.second - pair.first) in -3 ..-1
        }
        return allIncreasing || allDecreasing
    }

    fun part1(input: List<String>): Int {
        val list = input.map { it.split(" ").map({ it.toInt() }) }
        return list.map {
            it.isListSafe().compareTo(false)
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val list = input.map { it.split(" ").map({ it.toInt() }) }

        // Brute force solution from https://www.youtube.com/watch?v=ANZ8xEvuYqk
        var result = 0
        list.forEach {
            var isSafe = false
            for (i in 0..it.lastIndex) {
                isSafe = it.toMutableList().apply { removeAt(i) }.isListSafe()
                if (isSafe) break
            }
            if (isSafe) { result++ }
        }
        return result
    }

    // Read the input from the `src/Data/` directory
    val input = readInput("Data/input_02_01")

    // Call functions with duration measurements
    val startTime: Long = getTimeInMSec()

    part1(input).println()
    val part1Time = getTimeInMSec()

    part2(input).println()
    val part2Time = getTimeInMSec()

    printDurations(startTime, part1Time, part2Time)
}

