fun main() {
    fun MutableList<IntArray>.fillContainers(containers: List<Int>, liters: Int, filledContainers: IntArray = IntArray(0)) {
        when {
            liters == 0 -> {
                add(filledContainers)
            }

            containers.isEmpty() -> {
            }

            else -> {
                val container = containers.first()
                val remainingContainers = containers.drop(1)
                fillContainers(remainingContainers, liters, filledContainers)
                if (container <= liters) {
                    fillContainers(remainingContainers, liters - container, filledContainers + container)
                }
            }
        }
    }

    fun part1(input: List<String>, liters: Int): Int {
        val containers = input.map { it.toInt() }.sortedDescending()
        return buildList { fillContainers(containers, liters) }.size
    }

    fun part2(input: List<String>, liters: Int): Int {
        val containers = input.map { it.toInt() }.sortedDescending()
        val filledContainers = buildList { fillContainers(containers, liters) }
        val minContainersSize = filledContainers.minOf { it.size }
        return filledContainers.count { it.size == minContainersSize }
    }

    val testInput = """
        20
        15
        10
        5
        5
    """.trimIndent().split('\n')
    check(part1(testInput, 25) == 4)
    val input = readInput("Day17")
    part1(input, 150).println()
    check(part2(testInput, 25) == 3)
    part2(input, 150).println()
}
