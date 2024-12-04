private typealias Input24 = List<Long>

fun <T> List<T>.dropAt(index: Int): List<T> = filterIndexed { i, t -> index != i }

private data class Compartments(val cabinPackageWeights: List<Long>, val trunkPackageWeights: List<Long>)

fun main() {
    fun List<String>.parse(): Input24 = map { it.toLong() }
    fun Compartments.quantumEntanglement(): Long = cabinPackageWeights.reduce { a, b -> a * b }
    fun List<Long>.weight(): Long = sum()

    fun List<Long>.balance(total: Long): Boolean = when {
        isEmpty() -> false
        first() == total -> true
        first() > total -> drop(1).balance(total)
        else -> drop(1).balance(total - first()) || drop(1).balance(total)
    }

    fun search(packageWeights: List<Long>, packageCountInCabin: Int, compartments: Int = 2): List<Compartments> {
        fun MutableList<Compartments>.recur(packageWeights: List<Long>, cabinPackageWeights: List<Long>) {
            if (cabinPackageWeights.size == packageCountInCabin) {
                if (cabinPackageWeights.weight() * compartments == packageWeights.weight() &&
                    packageWeights.balance(cabinPackageWeights.weight())
                ) {
                    add(Compartments(cabinPackageWeights, packageWeights))
                }
                return
            }
            packageWeights.forEachIndexed { i, t ->
                recur(packageWeights.dropAt(i), cabinPackageWeights + t)
            }
        }
        return buildList {
            recur(packageWeights, emptyList())
        }
    }

    fun Input24.part1(): Long {
        (1..size).forEach { i ->
            search(this, i)
                .minByOrNull { it.quantumEntanglement() }
                ?.run {
                    return@part1 quantumEntanglement()
                }
        }
        throw IllegalStateException()
    }

    fun Input24.part2(): Long {
        (1..size).forEach { i ->
            search(packageWeights = this, packageCountInCabin = i, compartments = 3)
                .minByOrNull { it.quantumEntanglement() }
                ?.run {
                    return@part2 quantumEntanglement()
                }
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
