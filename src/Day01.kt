fun main() {

    fun processInputIntoLists(input: List<String>): List<List<Int>> {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()
        input.forEach {
            val temp = it.split("  ")
            list1.add(temp[0].trim().toInt())
            list2.add(temp[1].trim().toInt())
        }
        return listOf(list1, list2)
    }

    //////////////////////////////////////

    fun part1(input: List<String>): Int {
        val list = processInputIntoLists(input)
        val l1 = list[0].sorted()
        val l2 = list[1].sorted()

        return l1.zip(l2).map {  Math.abs(it.first - it.second) }.sum()
    }

    fun part2(input: List<String>): Int {
        val list = processInputIntoLists(input)
        val list1 = list[0]
        val list2 = list[1]

        val listCount = list2
            .groupingBy(Int::toInt)
            .eachCount()
        return list1.map { it * (listCount[it] ?: 0) }.sum()
    }


    // Read the input from the `src/Data/` directory
    val input = readInput("Data/input_01_01")

    // Call functions with duration measurements
    val startTime: Long = getTimeInMSec()

    part1(input).println()
    val part1Time = getTimeInMSec()

    part2(input).println()
    val part2Time = getTimeInMSec()

    printDurations(startTime, part1Time, part2Time)
}

