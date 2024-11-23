fun main() {
    val turnOnRegex = """turn on (\d+),(\d+) through (\d+),(\d+)""".toRegex()
    val turnOffRegex = """turn off (\d+),(\d+) through (\d+),(\d+)""".toRegex()
    val toggleRegex = """toggle (\d+),(\d+) through (\d+),(\d+)""".toRegex()
    fun part1(input: List<String>): Int {
        operator fun BooleanArray.set(row: Int, col: Int, value: Boolean) {
            this[row * 1_000 + col] = value
        }

        operator fun BooleanArray.get(row: Int, col: Int): Boolean {
            return this[row * 1_000 + col]
        }

        fun BooleanArray.turnOn(c1: Pos, c2: Pos) {
            for (row in c1.row..c2.row) {
                for (col in c1.col..c2.col) {
                    this[row, col] = true
                }
            }
        }

        fun BooleanArray.turnOff(c1: Pos, c2: Pos) {
            for (row in c1.row..c2.row) {
                for (col in c1.col..c2.col) {
                    this[row, col] = false
                }
            }
        }

        fun BooleanArray.toggle(c1: Pos, c2: Pos) {
            for (row in c1.row..c2.row) {
                for (col in c1.col..c2.col) {
                    this[row, col] = this[row, col].not()
                }
            }
        }

        val lights = BooleanArray(1_000_000)
        input.forEach { line ->
            val turnOnMatchResult = turnOnRegex.matchEntire(line)
            if (turnOnMatchResult != null) {
                val (a, b, c, d) = turnOnMatchResult.destructured
                lights.turnOn(Pos(a.toInt(), b.toInt()), Pos(c.toInt(), d.toInt()))
                return@forEach
            }
            val turnOffMatchResult = turnOffRegex.matchEntire(line)
            if (turnOffMatchResult != null) {
                val (a, b, c, d) = turnOffMatchResult.destructured
                lights.turnOff(Pos(a.toInt(), b.toInt()), Pos(c.toInt(), d.toInt()))
                return@forEach
            }
            val toggleMatchResult = toggleRegex.matchEntire(line)
            if (toggleMatchResult != null) {
                val (a, b, c, d) = toggleMatchResult.destructured
                lights.toggle(Pos(a.toInt(), b.toInt()), Pos(c.toInt(), d.toInt()))
                return@forEach
            }
            throw IllegalStateException("No matching regex \"$line\"")
        }
        return lights.count { it }
    }

    fun part2(input: List<String>): Int {
        operator fun IntArray.set(row: Int, col: Int, value: Int) {
            this[row * 1_000 + col] = value
        }

        operator fun IntArray.get(row: Int, col: Int): Int {
            return this[row * 1_000 + col]
        }

        fun IntArray.turnOn(c1: Pos, c2: Pos) {
            for (row in c1.row..c2.row) {
                for (col in c1.col..c2.col) {
                    this[row, col]++
                }
            }
        }

        fun IntArray.turnOff(c1: Pos, c2: Pos) {
            for (row in c1.row..c2.row) {
                for (col in c1.col..c2.col) {
                    this[row, col] = (this[row, col] - 1).coerceAtLeast(0)
                }
            }
        }

        fun IntArray.toggle(c1: Pos, c2: Pos) {
            for (row in c1.row..c2.row) {
                for (col in c1.col..c2.col) {
                    this[row, col] += 2
                }
            }
        }

        val lights = IntArray(1_000_000)
        input.forEach { line ->
            val turnOnMatchResult = turnOnRegex.matchEntire(line)
            if (turnOnMatchResult != null) {
                val (a, b, c, d) = turnOnMatchResult.destructured
                lights.turnOn(Pos(a.toInt(), b.toInt()), Pos(c.toInt(), d.toInt()))
                return@forEach
            }
            val turnOffMatchResult = turnOffRegex.matchEntire(line)
            if (turnOffMatchResult != null) {
                val (a, b, c, d) = turnOffMatchResult.destructured
                lights.turnOff(Pos(a.toInt(), b.toInt()), Pos(c.toInt(), d.toInt()))
                return@forEach
            }
            val toggleMatchResult = toggleRegex.matchEntire(line)
            if (toggleMatchResult != null) {
                val (a, b, c, d) = toggleMatchResult.destructured
                lights.toggle(Pos(a.toInt(), b.toInt()), Pos(c.toInt(), d.toInt()))
                return@forEach
            }
            throw IllegalStateException("No matching regex \"$line\"")
        }
        return lights.sumOf { it }
    }
    check(part1(listOf("turn on 0,0 through 999,999")) == 1_000_000)
    check(part1(listOf("toggle 0,0 through 999,0")) == 1_000)
    check(part1(listOf("turn off 499,499 through 500,500")) == 0)
    val input = readInput("Day06")
    part1(input).println()
    check(part2(listOf("turn on 0,0 through 0,0")) == 1)
    check(part2(listOf("toggle 0,0 through 999,999")) == 2_000_000)
    part2(input).println()
}
