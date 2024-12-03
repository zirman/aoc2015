private typealias Input24 = List<Long>

fun <T> List<T>.dropAt(index: Int): List<T> = filterIndexed { i, t -> index != i }

fun main() {
    fun List<String>.parse(): Input24 = map { it.toLong() }
    fun List<Long>.quantumEntanglement(): Long = reduce { a, b -> a * b }
    fun List<Long>.weight(): Long = sum()

    fun List<Long>.balance(total: Long): Boolean = when {
        isEmpty() -> false
        first() == total -> true
        first() > total -> drop(1).balance(total)
        else -> drop(1).balance(total - first()) || drop(1).balance(total)
    }

    fun search(items: List<Long>, count: Int, foo: Int = 2): List<Pair<List<Long>, List<Long>>> {
        fun MutableList<Pair<List<Long>, List<Long>>>.recur(packages: List<Long>, passengerPackages: List<Long>) {
            if (passengerPackages.size == count) {
                if (passengerPackages.weight() * foo == packages.weight() &&
                    packages.balance(passengerPackages.weight())
                ) {
                    add(Pair(passengerPackages, packages))
                }
                return
            }
            packages.forEachIndexed { i, t ->
                recur(packages.dropAt(i), passengerPackages + t)
            }
        }
        return buildList {
            recur(items, emptyList())
        }
    }

    fun Input24.part1(): Long {
        (1..size).forEach { i ->
            search(this, i)
                .minByOrNull { (a, _) -> a.quantumEntanglement() }
                ?.run {
                    return@part1 first.quantumEntanglement()
                }
        }
        throw IllegalStateException()
    }

    fun Input24.part2(): Long {
        (1..size).forEach { i ->
            search(items = this, count = i, foo = 3)
                .minByOrNull { (a, _) -> a.quantumEntanglement() }
                ?.run {
                    return@part2 first.quantumEntanglement()
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
