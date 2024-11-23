fun main() {
    fun part1(input: List<String>): Int {
        return input[0].count { it == '(' } - input[0].count { it == ')' }
    }

    fun part2(input: List<String>): Int {
        val steps = input[0]
        var floor = 0
        for (i in steps.indices) {
            when (steps[i]) {
                '(' -> floor++
                ')' -> {
                    floor--
                    if (floor == -1) {
                        return i + 1
                    }
                }
            }
        }
        throw IllegalStateException("Never entered the basement")
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("(())")) == 0)
    check(part1(listOf("()()")) == 0)
    check(part1(listOf("(((")) == 3)
    check(part1(listOf("(()(()(")) == 3)
    check(part1(listOf("))(((((")) == 3)
    check(part1(listOf("())")) == -1)
    check(part1(listOf("))(")) == -1)
    check(part1(listOf(")))")) == -3)
    check(part1(listOf(")())())")) == -3)

    val input = readInput("Day01")
    part1(input).println()

    check(part2(listOf(")")) == 1)
    check(part2(listOf("()())")) == 5)
    part2(input).println()
}
