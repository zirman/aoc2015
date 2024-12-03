fun <T> List<T>.permute(prefix: List<T> = emptyList()): List<List<T>> {
    if (isEmpty()) {
        return listOf(prefix)
    }
    return flatMapIndexed { index, t ->
        buildList(size - 1) {
            this@permute.forEachIndexed { index2, t ->
                if (index != index2) {
                    add(t)
                }
            }
        }.permute(
            buildList(prefix.size + 1) {
                addAll(prefix)
                add(t)
            },
        )
    }
}

fun main() {
    val regex = """([a-zA-Z]+) to ([a-zA-Z]+) = (\d+)""".toRegex()
    fun routeDistances(input: List<String>): List<Int> {
        val distances = buildMap {
            input.forEach { line ->
                val (a, b, distance) = regex.matchEntire(line)!!.destructured
                val d = distance.toInt()
                this[listOf(a, b)] = d
                this[listOf(b, a)] = d
            }
        }
        val locations = distances.keys.flatten().toSet().toList()
        return locations.permute().map { route ->
            if (route.windowed(2).all { hop -> distances[hop] != null }) {
                route.windowed(2).sumOf { hop -> distances[hop]!! }
            } else {
                Int.MAX_VALUE
            }
        }
    }

    fun part1(input: List<String>): Int {
        return routeDistances(input).min()
    }

    fun part2(input: List<String>): Int {
        return routeDistances(input).max()
    }
    check(
        part1(
            """
                London to Dublin = 464
                London to Belfast = 518
                Dublin to Belfast = 141
            """.trimIndent().split('\n'),
        ) == 605,
    )
    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
