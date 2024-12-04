private typealias Input24 = List<Long>

fun <T> List<T>.dropAt(index: Int): List<T> = filterIndexed { i, t -> index != i }

private fun <T> select(items: List<T>, size: Int): List<List<T>> {
    fun MutableList<List<T>>.recur(index: Int, include: List<T>) {
        if (include.size == size) {
            add(include)
            return
        }

        (index..<items.size).forEach { t ->
            recur(t + 1, include + items[t])
        }
    }
    return buildList {
        recur(0, emptyList())
    }
}

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

    fun search(packageWeights: List<Long>, packageCountInCabin: Int, trunks: Int): Long? {
        val packageWeightsTotal = packageWeights.total()
        return select(packageWeights, packageCountInCabin)
            .filter { it.total() * (trunks + 1) == packageWeightsTotal }
            .sortedBy { it.quantumEntanglement() }
            .firstOrNull { (packageWeights - it).balance(it.total()) }
            ?.quantumEntanglement()
    }

    fun Input24.part1(): Long {
        (1..size).forEach { i ->
            search(packageWeights = this, packageCountInCabin = i, trunks = 2)?.run { return@part1 this }
        }
        throw IllegalStateException()
    }

    fun Input24.part2(): Long {
        (1..size).forEach { i ->
            search(packageWeights = this, packageCountInCabin = i, trunks = 3)?.run { return@part2 this }
        }
        throw IllegalStateException()
    }
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
