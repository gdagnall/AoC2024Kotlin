fun main() {

    fun List<String>.matchUp(start: Pair<Int, Int>, word2Match: String):  Int {
        if ((start.first + (word2Match.length - 1)) >= size) { return 0 }

        // Straight match
        var sum = word2Match.mapIndexed { index, c ->
            c == this[start.first + index][start.second]
        }.all { it }.compareTo(false)

        // Left tilt match
        if ((start.second - (word2Match.length - 1)) >= 0) {
            sum += word2Match.mapIndexed { index, c ->
                c == this[start.first + index][start.second - index]
            }.all { it }.compareTo(false)
        }

        // Right tilt match
        if ((start.second + (word2Match.length - 1)) <= (this[0].length-1)) {
            sum += word2Match.mapIndexed { index, c ->
                c == this[start.first + index][start.second + index]
            }.all { it }.compareTo(false)
        }
        return sum
    }


    fun List<String>.matchDown(start: Pair<Int, Int>, word2Match: String): Int {
        if ((start.first - (word2Match.length - 1)) < 0) { return 0 }

        // Straight match
        var sum = word2Match.mapIndexed { index, c ->
            c == this[start.first - index][start.second]
        }.all { it }.compareTo(false)

        // Left tilt match
        if ((start.second - (word2Match.length - 1)) >= 0) {
            sum += word2Match.mapIndexed { index, c ->
                c == this[start.first - index][start.second - index]
            }.all { it }.compareTo(false)
        }

        // Right tilt match
        if ((start.second + (word2Match.length - 1)) <= (this[0].length - 1)) {
            sum += word2Match.mapIndexed { index, c ->
                c == this[start.first - index][start.second + index]
            }.all { it }.compareTo(false)
        }
        return sum
    }

    fun part1(input: List<String>): Int {
        val word2Match = "XMAS"
        val regexForward = Regex(word2Match)
        val regexReverse = Regex("${word2Match.reversed()}")
        val regexX = Regex("X")

        var sum = 0
        input.forEachIndexed { index, line ->
            sum += regexForward.findAll(line).count()
            sum += regexReverse.findAll(line).count()
            regexX.findAll(line).forEach {
                sum += input.matchUp(Pair(index, it.range.start), word2Match)
                sum += input.matchDown(Pair(index, it.range.start), word2Match)
            }
        }
        return sum
    }


    fun List<String>.matchCrossDiagonalMAS(start: Pair<Int, Int>):  Int {
        // Assume matched letter is in center of word - A in MAS
        val letters2Match = listOf('M', 'S')

        if (((start.first + 1) >= size) || ((start.first - 1) < 0)) { return 0 }
        if (((start.second + 1) >= this[0].length) || ((start.second - 1) < 0)) { return 0 }

        if (((letters2Match[0] == this[start.first - 1][start.second - 1]) &&
                    (letters2Match[1] == this[start.first + 1][start.second + 1])) ||
            ((letters2Match[1] == this[start.first - 1][start.second - 1]) &&
                    (letters2Match[0] == this[start.first + 1][start.second +1])) ) {

            if (((letters2Match[0] == this[start.first - 1][start.second + 1]) &&
                        (letters2Match[1] == this[start.first + 1][start.second - 1])) ||
                ((letters2Match[1] == this[start.first - 1][start.second + 1]) &&
                        (letters2Match[0] == this[start.first + 1][start.second - 1])) ) {
                // Found a match
                return 1
            }
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        return input.mapIndexed { index, line ->
            Regex("A").findAll(line).map {
                input.matchCrossDiagonalMAS(Pair(index, it.range.start))
            }.sum()
        }.sum()
    }


    // Read the input from the `src/Data/` directory
    val input = readInput("Data/input_04_01")


    // Call functions with duration measurements
    val startTime: Long = getTimeInMSec()

    part1(input).println()
    val part1Time = getTimeInMSec()

    part2(input).println()
    val part2Time = getTimeInMSec()

    printDurations(startTime, part1Time, part2Time)
}

