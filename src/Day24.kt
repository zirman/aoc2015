private typealias Input24 = List<Long>

fun main() {
    fun List<String>.parse(): Input24 = map { it.toLong() }
    fun List<Long>.quantumEntanglement(): Long = reduce { a, b -> a * b }
    fun List<Long>.total(): Long = sum()

    fun List<Long>.balance(total: Long): Boolean = when {
        isEmpty() -> false
        first() == total -> true
        first() > total -> drop(1).balance(total)
        else -> drop(1).balance(total - first()) || drop(1).balance(total)
    }

    fun search(packageWeights: List<Long>, trunks: Int): Long {
        (1..packageWeights.size).forEach { packageCountInCabin ->
            val packageWeightsTotal = packageWeights.total()
            packageWeights.combination(packageCountInCabin)
                .filter { it.total() * (trunks + 1) == packageWeightsTotal }
                .sortedBy { it.quantumEntanglement() }
                .firstOrNull { (packageWeights - it).balance(it.total()) }
                ?.quantumEntanglement()
                ?.run { return this }
        }
        throw IllegalStateException()
    }

    fun Input24.part1(): Long = search(packageWeights = this, trunks = 2)
    fun Input24.part2(): Long = search(packageWeights = this, trunks = 3)
    check(
        """
            1
            2
            3
            4
            5
            7
            8
            9
            10
            11
        """.trimIndent().split('\n').parse().part1() == 99L,
    )
    val input = readInput("Day24").parse()
    input.part1().println()
    input.part2().println()
}
