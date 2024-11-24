fun main() {
    val hexRegex = """\\x[0-9a-f]{2}""".toRegex()
    val escapeRegex = """\\.""".toRegex()
    val quoteRegex = """"""".toRegex()
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            var i = 0
            var c = 0
            while (i < line.length) {
                val hexMatch = hexRegex.matchAt(line, i)
                if (hexMatch != null) {
                    i = hexMatch.range.last + 1
                    c++
                    continue
                }
                val escapeMatch = escapeRegex.matchAt(line, i)
                if (escapeMatch != null) {
                    i = escapeMatch.range.last + 1
                    c++
                    continue
                }
                val quoteMatch = quoteRegex.matchAt(line, i)
                if (quoteMatch != null) {
                    i = quoteMatch.range.last + 1
                    continue
                }
                i++
                c++
            }
            line.length - c
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            var i = 0
            var c = 2
            line.forEach {
                when (it) {
                    '"' -> {
                        c += 2
                    }

                    '\\' -> {
                        c += 2
                    }

                    else -> {
                        c++
                    }
                }
            }
            c - line.length
        }
    }
    check(part1(listOf("""""""")) == 2)
    check(part1(listOf(""""abc"""")) == 2)
    check(part1(listOf(""""aaa\"aaa"""")) == 3)
    check(part1(listOf(""""\x27"""")) == 5)
    check(
        part1(
            listOf(
                """""""",
                """"abc"""",
                """"aaa\"aaa"""",
                """"\x27"""",
            )
        ) == 12
    )
    val input = readInput("Day08")
    part1(input).println()
    check(part2(listOf("""""""")) == 4)
    check(part2(listOf(""""abc"""")) == 4)
    check(part2(listOf(""""aaa\"aaa"""")) == 6)
    check(part2(listOf(""""\x27"""")) == 5)
    part2(input).println()
}
