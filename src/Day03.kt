fun main() {

    fun part1(input: List<String>): Int {
        val mulPattern = "mul\\((\\d+),(\\d+)\\)"
        val regex = Regex(mulPattern)

        var sum = 0
        for (line in input) {
            sum += regex.findAll(line).sumOf { it.groups.get(1)?.value?.toInt()!!.times(it.groups.get(2)?.value?.toInt()!!) }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val mulPattern =  "mul\\((\\d+),(\\d+)\\)"
        val pairPattern = "(^|do\\(\\)).*?(don't\\(\\)|$)"

        val regexMul = Regex(mulPattern)
        val regexPair = Regex(pairPattern)

        val sb = input.joinToString("")

         return regexPair.findAll(sb).sumOf {
            regexMul.findAll(it.value).sumOf {
                it.groups.get(1)?.value?.toInt()!!.times(it.groups.get(2)?.value?.toInt()!!)
            }
        }
    }


    // Read the input from the `src/Data/` directory
    val input = readInput("Data/input_03_01")


    // Call functions with duration measurements
    val startTime: Long = getTimeInMSec()

    part1(input).println()
    val part1Time = getTimeInMSec()

    part2(input).println()
    val part2Time = getTimeInMSec()

    printDurations(startTime, part1Time, part2Time)
}

