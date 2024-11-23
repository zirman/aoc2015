data class Pos(val row: Int, val col: Int)

fun main() {
    fun MutableSet<Pos>.foo(moves: String): MutableSet<Pos> {
        var row = 0
        var col = 0
        moves.forEach { c ->
            when (c) {
                '>' -> {
                    col++
                }

                '<' -> {
                    col--
                }

                '^' -> {
                    row--
                }

                'v' -> {
                    row++
                }
            }
            add(Pos(row, col))
        }
        return this
    }

    fun part1(input: List<String>): Int {
        return mutableSetOf(Pos(0, 0)).foo(input[0]).size
    }

    fun part2(input: List<String>): Int {
        return mutableSetOf(Pos(0, 0))
            .foo(input[0].filterIndexed { index, _ -> index.mod(2) == 0 })
            .foo(input[0].filterIndexed { index, _ -> index.mod(2) != 0 })
            .size
    }
    check(part1(listOf(">")) == 2)
    check(part1(listOf("^>v<")) == 4)
    check(part1(listOf("^v^v^v^v^v")) == 2)
    val input = readInput("Day03")
    part1(input).println()
    check(part2(listOf("^v")) == 3)
    check(part2(listOf("^>v<")) == 3)
    check(part2(listOf("^v^v^v^v^v")) == 11)
    part2(input).println()
}
