fun main() {
    fun List<String>.toAunt(): Map<String, Int> = associate { prop ->
        val (name, number) = prop.split(':')
        Pair(name.trim(), number.trim().toInt())
    }

    val auntSue = """
            children: 3
            cats: 7
            samoyeds: 2
            pomeranians: 3
            akitas: 0
            vizslas: 0
            goldfish: 5
            trees: 3
            cars: 2
            perfumes: 1
        """.trimIndent().split('\n').toAunt()

    fun part1(input: List<String>): Int {
        val aunts = input.map { line ->
            line.substring(line.indexOf(':') + 1).split(',').toAunt()
        }
        return 1 + aunts.indexOfFirst { a ->
            a.all { (prop, value) -> auntSue[prop] == value }
        }
    }

    fun part2(input: List<String>): Int {
        val aunts = input.map { line ->
            line.substring(line.indexOf(':') + 1).split(',').toAunt()
        }
        return 1 + aunts.indexOfFirst { a ->
            a.all { (prop, value) ->
                when (prop) {
                    "cats", "trees" -> auntSue[prop]!! < value
                    "pomeranians", "goldfish" -> auntSue[prop]!! > value
                    else -> auntSue[prop] == value
                }
            }
        }
    }

    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}
