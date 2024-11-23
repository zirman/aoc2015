fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }
    fun part2(input: List<String>): Int {
        return input.size
    }
    check(part1(listOf("test_input")) == 1)
    val testInput = readInput("Day15_test")
    check(part1(testInput) == 1)
    val input = readInput("Day15")
    part1(input).println()
//    check(part2(listOf("test_input")) == 1)
//    part2(input).println()
}
