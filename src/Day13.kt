fun main() {
    val gainRegex = """([a-zA-Z]+) would gain (\d+) happiness units by sitting next to ([a-zA-Z]+)\.""".toRegex()
    val loseRegex = """([a-zA-Z]+) would lose (\d+) happiness units by sitting next to ([a-zA-Z]+)\.""".toRegex()
    fun part1(input: List<String>): Int {
        val relation = mutableMapOf<Pair<String, String>, Int>()
        input.forEach { line ->
            val gainMatch = gainRegex.matchEntire(line)
            if (gainMatch != null) {
                val (name, gain, by) = gainMatch.destructured
                relation[Pair(name, by)] = gain.toInt()
                return@forEach
            }
            val loseMatch = loseRegex.matchEntire(line)
            if (loseMatch != null) {
                val (name, gain, by) = loseMatch.destructured
                relation[Pair(name, by)] = -gain.toInt()
                return@forEach
            }
            throw IllegalStateException("No matching regex (${line})")
        }
        val names = relation.keys.flatMap { (a, b) -> listOf(a, b) }.toSet().toList()
        return names.permute().maxOf { arrangement ->
            arrangement.indices.sumOf { index ->
                relation[Pair(arrangement[index], arrangement[(index + 1).mod(arrangement.size)])]!! +
                        relation[Pair(arrangement[index], arrangement[(index - 1).mod(arrangement.size)])]!!
            }
        }
    }

    fun part2(input: List<String>): Int {
        val relation = mutableMapOf<Pair<String, String>, Int>()
        input.forEach { line ->
            val gainMatch = gainRegex.matchEntire(line)
            if (gainMatch != null) {
                val (name, gain, by) = gainMatch.destructured
                relation[Pair(name, by)] = gain.toInt()
                return@forEach
            }
            val loseMatch = loseRegex.matchEntire(line)
            if (loseMatch != null) {
                val (name, gain, by) = loseMatch.destructured
                relation[Pair(name, by)] = -gain.toInt()
                return@forEach
            }
            throw IllegalStateException("No matching regex (${line})")
        }
        val names = relation.keys.flatMap { (a, b) -> listOf(a, b) }.toSet().toList().plus("_")
        return names.permute().maxOf { arrangement ->
            arrangement.indices.sumOf { index ->
                if (arrangement[index] == "_") {
                    0
                } else {
                    var a = 0
                    val right = arrangement[(index + 1).mod(arrangement.size)]
                    if (right != "_") {
                        a += relation[Pair(arrangement[index], right)]!!
                    }
                    val left = arrangement[(index - 1).mod(arrangement.size)]
                    if (left != "_") {
                        a += relation[Pair(arrangement[index], left)]!!
                    }
                    a
                }
            }
        }
    }
    check(
        part1(
            """
                Alice would gain 54 happiness units by sitting next to Bob.
                Alice would lose 79 happiness units by sitting next to Carol.
                Alice would lose 2 happiness units by sitting next to David.
                Bob would gain 83 happiness units by sitting next to Alice.
                Bob would lose 7 happiness units by sitting next to Carol.
                Bob would lose 63 happiness units by sitting next to David.
                Carol would lose 62 happiness units by sitting next to Alice.
                Carol would gain 60 happiness units by sitting next to Bob.
                Carol would gain 55 happiness units by sitting next to David.
                David would gain 46 happiness units by sitting next to Alice.
                David would lose 7 happiness units by sitting next to Bob.
                David would gain 41 happiness units by sitting next to Carol.
            """.trimIndent().split('\n'),
        ) == 330,
    )
    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}
