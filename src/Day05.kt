fun main() {

    fun part1(input: List<String>): Int {
        var splitStrIndex: Int = -1
        for (i in 0 until input.size) {
            if (input[i].trim().isEmpty()) {
                splitStrIndex = i
                break
            }
        }

        val rulesStr = input.subList(0, splitStrIndex-1)
        val rules = rulesStr.map { it.split("|") }.map { Pair(it[0].toInt(), it[1].toInt()) }

        val pagesStr = input.subList(splitStrIndex+1, input.size)
        val pages: List<List<Int>> = pagesStr.map { it.split(",") }.map{ it.map { value -> value.toInt() } }

        val midValues = pages.map { pageList ->
            val temp = pageList.mapIndexed { index, i ->
                val before = mutableSetOf<Int>()
                val after = mutableSetOf<Int>()
                val afterValues = mutableSetOf<Int>()
                val beforeValues = mutableSetOf<Int>()
                if (index > 0) {
                    beforeValues.addAll(pageList.subList(0, index - 1))
                }
                // Find all rules that should be before
                rules.filter { i == it.second }.forEach { before.add(it.first) }
                afterValues.addAll(pageList.subList(index + 1, pageList.size))
                // Find all rules that should be after
                rules.filter { i == it.first }.forEach { after.add(it.second) }
                before.intersect(afterValues).isEmpty() && after.intersect(beforeValues).isEmpty()
            }.all { it }

            if (temp) {
                pageList[Math.floorDiv(pageList.size, 2)]
            } else {
                0
            }
        }.sum()

        return midValues  // 4790
    }

    fun part2(input: List<String>): Int {
        var splitStrIndex: Int = -1
        for (i in 0 until input.size) {
            if (input[i].trim().isEmpty()) {
                splitStrIndex = i
                break
            }
        }

        val rulesStr = input.subList(0, splitStrIndex-1)
        val rules = rulesStr.map { it.split("|") }.map { Pair(it[0].toInt(), it[1].toInt()) }

        val pagesStr = input.subList(splitStrIndex+1, input.size)
        val pages: List<List<Int>> = pagesStr.map { it.split(",") }.map{ it.map { value -> value.toInt() } }

        val badPages = mutableListOf<List<Int>>()
        pages.map { pageList ->
            val temp = pageList.mapIndexed { index, i ->
                val before = mutableSetOf<Int>()
                val after = mutableSetOf<Int>()
                val afterValues = mutableSetOf<Int>()
                val beforeValues = mutableSetOf<Int>()
                if (index > 0) {
                    beforeValues.addAll(pageList.subList(0, index - 1))
                }
                // Find all rules that should be before
                rules.filter { i == it.second }.forEach { before.add(it.first) }
                afterValues.addAll(pageList.subList(index + 1, pageList.size))
                // Find all rules that should be after
                rules.filter { i == it.first }.forEach { after.add(it.second) }
                before.intersect(afterValues).isEmpty() && after.intersect(beforeValues).isEmpty()
            }.all { it }
            if (!temp) {
                badPages.add(pageList)
            }
        }

        val midValues = badPages.map { pageList ->
            var updatedList = pageList
            for (index in pageList.indices) {
                do {
                    var listUpdated = false
                    val i = updatedList[index]
                    val before = mutableSetOf<Int>()
                    val after = mutableSetOf<Int>()
                    val afterListValues = updatedList.subList(index + 1, updatedList.size)
                    val beforeListValues = if (index > 0) {
                        updatedList.subList(0, index)
                    } else {
                        listOf()
                    }

                    // Find all rules that should be before
                    rules.filter { i == it.second }.forEach { before.add(it.first) }
                    // Find all rules that should be after
                    rules.filter { i == it.first }.forEach { after.add(it.second) }
                    if (before.intersect(afterListValues).isNotEmpty()) {
                        listUpdated = true
                        val matchedValues = before.intersect(afterListValues.toSet())
                        val swapIndex = matchedValues.maxOfOrNull { updatedList.indexOf(it) }
                        val swapValue = updatedList[swapIndex!!]
                        updatedList = updatedList.mapIndexed { indexSwap, iSwap ->
                            if (indexSwap == swapIndex) {
                                i
                            } else if (indexSwap == index) {
                                swapValue
                            } else iSwap
                        }
                    }
                    if (after.intersect(beforeListValues).isNotEmpty()) {
                        listUpdated = true
                        val matchedValues = after.intersect(beforeListValues.toSet())
                        val swapIndex = matchedValues.minOfOrNull { updatedList.indexOf(it) }
                        val swapValue = updatedList[swapIndex!!]
                        updatedList = updatedList.mapIndexed { indexSwap, iSwap ->
                            if (indexSwap == swapIndex) {
                                i
                            } else if (indexSwap == index) {
                                swapValue
                            } else iSwap
                        }
                    }
                } while (listUpdated)
            }
            updatedList
        }.sumOf { it[Math.floorDiv(it.size, 2)] }

        return midValues // 6319
    }


    // Read the input from the `src/Data/` directory
    val input = readInput("Data/input_05_01")

    // Call functions with duration measurements
    val startTime: Long = getTimeInMSec()

    part1(input).println()
    val part1Time = getTimeInMSec()

    part2(input).println()
    val part2Time = getTimeInMSec()

    printDurations(startTime, part1Time, part2Time)

}
