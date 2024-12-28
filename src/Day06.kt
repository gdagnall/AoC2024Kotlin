enum class MapDirection(val raw: Char) {
    NORTH('^') {
        override fun turnRight() = EAST
    }, EAST('>') {
        override fun turnRight() = SOUTH
    }, SOUTH('v') {
        override fun turnRight() = WEST
    }, WEST('<') {
        override fun turnRight() = NORTH
    };
    companion object {
        fun instance(raw: Char): MapDirection {
            return entries.first { it.raw == raw }
        }
    }
    abstract fun turnRight(): MapDirection
}

data class GuardStep(val x: Int, val y: Int, val direction: MapDirection) {
    val position: Pair<Int, Int> = Pair(x, y)
    fun turnRight(): GuardStep {
        return GuardStep(x, y, direction.turnRight())
    }
}

fun main() {

    fun isInMap(w: Int, h: Int, position: Pair<Int, Int>): Boolean =
        (((position.first >= 0) && (position.first < h)) &&
                ((position.second >= 0) && (position.second < w)))

    fun isInMap(w: Int, h: Int, guardStep: GuardStep): Boolean =
        (((guardStep.x >= 0) && (guardStep.x < h)) &&
                ((guardStep.y >= 0) && (guardStep.y < w)))

    fun nextGuardPosition(direction: MapDirection, position: Pair<Int, Int>): Pair<Int, Int> =
        when (direction) {
            MapDirection.NORTH -> Pair(position.first - 1, position.second)
            MapDirection.EAST -> Pair(position.first, position.second + 1)
            MapDirection.SOUTH -> Pair(position.first + 1, position.second)
            MapDirection.WEST -> Pair(position.first, position.second - 1)
        }

    fun nextGuardPosition(guardStep: GuardStep): GuardStep =
        when (guardStep.direction) {
            MapDirection.NORTH -> GuardStep(guardStep.x - 1, guardStep.y, guardStep.direction)
            MapDirection.EAST -> GuardStep(guardStep.x, guardStep.y + 1, guardStep.direction)
            MapDirection.SOUTH -> GuardStep(guardStep.x + 1, guardStep.y, guardStep.direction)
            MapDirection.WEST -> GuardStep(guardStep.x, guardStep.y - 1, guardStep.direction)
        }



    fun part1(input: List<String>): Int {
        val guard2Match = "[\\^><vV]"
        val regexGuard = Regex(guard2Match)
        val regexObstacle = Regex("#")

        val guardStart = mutableSetOf<Pair<Int, Int>>()
        lateinit var guardDirection: MapDirection
        val obstacles = mutableSetOf<Pair<Int, Int>>()
        input.forEachIndexed { index, line ->
            obstacles.addAll(regexObstacle.findAll(line).map { Pair(index, it.range.first) })
            regexGuard.findAll(line).forEach {
                guardStart.add(Pair(index, it.range.first))
                guardDirection = MapDirection.instance(it.value[0])
                
            }
        }

        // Figure out the starting direction
        val height = input.count()
        val width = input.first().length
        var guardPosition = guardStart.first()
        var futureGuardPosition: Pair<Int, Int>
        val guardSteps = mutableSetOf<Pair<Int, Int>>()

        while (isInMap(width, height, guardPosition)) {
            if (!obstacles.contains(guardPosition)) {
                guardSteps.add(guardPosition)
            }
           futureGuardPosition = nextGuardPosition(guardDirection, guardPosition)
           // Check if the future position is a collision
           if (obstacles.contains(futureGuardPosition)) {
               // Alter direction
               guardDirection = guardDirection.turnRight()
           } else {
               guardPosition = futureGuardPosition
           }
            
       }

        return guardSteps.size  // 5531
    }

    fun part2(input: List<String>): Int {
        val guard2Match = "[\\^><vV]"
        val regexGuard = Regex(guard2Match)
        val regexObstacle = Regex("#")

        val guardStart = mutableSetOf<Pair<Int, Int>>()
        lateinit var guardDirection: MapDirection
        val initialObstacles = mutableSetOf<Pair<Int, Int>>()
        input.forEachIndexed { index, line ->
            initialObstacles.addAll(regexObstacle.findAll(line).map { Pair(index, it.range.first) })
                      
            regexGuard.findAll(line).forEach {
                guardStart.add(Pair(index, it.range.first))
                guardDirection = MapDirection.instance(it.value[0])
                
            }
        }

       //  Figure out the starting direction
        val height = input.count()
        val width = input.first().length
        var loopCount = 0
        // Add obstacles one at a time
        for (i in input.indices) {
            inner@ for (j in 0 until input.first().length) {
                // Skip over the initial guard position
                if ((guardStart.first().first == i) && (guardStart.first().second == j)){
                    
                    continue@inner
                }
                // Skip over any initial obstacles
                if (initialObstacles.contains(Pair(i, j))) {
                    continue@inner
                }

                // Add obstacle
                val obstacles = mutableSetOf<Pair<Int, Int>>()
                obstacles.addAll(initialObstacles)
                obstacles.add(Pair(i, j))

                var guardPosition = GuardStep(guardStart.first().first, guardStart.first().second, guardDirection)
                var futureGuardPosition: GuardStep
                val guardSteps = mutableSetOf<GuardStep>()

                // Check for loop in guardSteps
                while (isInMap(width, height, guardPosition)) {
                    if (!obstacles.contains(guardPosition.position)) {
                        if (guardSteps.contains(guardPosition)){
                            loopCount++
                            continue@inner
                        }
                        guardSteps.add(guardPosition)
                    } else {
                        // PROBLEM: current guard position is on obstacle
                        continue@inner
                    }
                    futureGuardPosition = nextGuardPosition(guardPosition)
                    
                    // Check if the future position is a collision
                    if (obstacles.contains(futureGuardPosition.position)) {
                        // Alter direction
                        guardPosition = guardPosition.turnRight()
                    } else {
                        guardPosition = futureGuardPosition
                    }
                }
            }
        }

        return loopCount  // 2165
    }


    // Read the input from the `src/Data/` directory
    val input = readInput("Data/input_06_01")

    // Call functions with duration measurements
    val startTime: Long = getTimeInMSec()

    part1(input).println()
    val part1Time = getTimeInMSec()

    part2(input).println()
    val part2Time = getTimeInMSec()

    printDurations(startTime, part1Time, part2Time)
}
