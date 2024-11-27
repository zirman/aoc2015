fun main() {
    val width = 100
    operator fun BooleanArray.get(row: Int, col: Int): Boolean {
        return this[row * width + col]
    }

    operator fun BooleanArray.set(row: Int, col: Int, value: Boolean) {
        this[row * width + col] = value
    }

    fun BooleanArray.countLitNeighbors(row: Int, col: Int): Int {
        var lit = 0
        ((row - 1).coerceAtLeast(0)..(row + 1).coerceAtMost(width - 1)).forEach { r ->
            ((col - 1).coerceAtLeast(0)..(col + 1).coerceAtMost(width - 1)).forEach { c ->
                if ((r != row || c != col) && this[r, c]) {
                    lit++
                }
            }
        }
        return lit
    }

    fun BooleanArray.print() {
        buildString {
            (0..<width).forEach { r ->
                (0..<width).forEach { c ->
                    append(if (this@print[r, c]) '#' else '.')
                }
                appendLine()
            }
        }.println()
    }

    fun BooleanArray.next(): BooleanArray {
        val ba = BooleanArray(width * width)
        (0..<width).forEach { row ->
            (0..<width).forEach { col ->
                ba[row, col] = when {
                    this[row, col] -> when (countLitNeighbors(row, col)) {
                        2, 3 -> true
                        else -> false
                    }

                    else -> when (countLitNeighbors(row, col)) {
                        3 -> true
                        else -> false
                    }
                }
            }
        }
        return ba
    }

    fun BooleanArray.next2(): BooleanArray {
        val ba = BooleanArray(width * width)
        (0..<width).forEach { row ->
            (0..<width).forEach { col ->
                ba[row, col] =
                    if ((row == 0 && col == 0) ||
                        (row == width - 1 && col == 0) ||
                        (row == 0 && col == width - 1) ||
                        (row == width - 1 && col == width - 1)
                    ) {
                        true
                    } else {
                        when {
                            this[row, col] -> when (countLitNeighbors(row, col)) {
                                2, 3 -> true
                                else -> false
                            }

                            else -> when (countLitNeighbors(row, col)) {
                                3 -> true
                                else -> false
                            }
                        }
                    }
            }
        }
        return ba
    }

    fun part1(input: List<String>): Int {
        var lights = BooleanArray(width * width)
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, c ->
                lights[row, col] = c == '#'
            }
        }
        repeat(100) {
            lights = lights.next()
        }
        return lights.count { it }
    }

    fun part2(input: List<String>): Int {
        var lights = BooleanArray(width * width)
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, c ->
                lights[row, col] = c == '#'
            }
        }
        lights[0, 0] = true
        lights[width - 1, 0] = true
        lights[0, width - 1] = true
        lights[width - 1, width - 1] = true
        repeat(100) {
            lights = lights.next2()
        }
        return lights.count { it }
    }

    val input = readInput("Day18")
    part1(input).println()
    part2(input).println()
}
