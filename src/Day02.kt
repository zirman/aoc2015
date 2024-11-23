fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val (l, w, h) = it.split('x')
            val li = l.toInt()
            val wi = w.toInt()
            val hi = h.toInt()
            val x = li * wi
            val y = li * hi
            val z = wi * hi
            ((x + y + z) * 2) + x.coerceAtMost(y).coerceAtMost(z)
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val (l, w, h) = it.split('x')
            val li = l.toInt()
            val wi = w.toInt()
            val hi = h.toInt()
            (((li + wi + hi) * 2) - (li.coerceAtLeast(wi).coerceAtLeast(hi) * 2)) + (li * wi * hi)
        }
    }
    check(part1(listOf("2x3x4")) == 58)
    check(part1(listOf("1x1x10")) == 43)
    val input = readInput("Day02")
    part1(input).println()
    check(part2(listOf("2x3x4")) == 34)
    check(part2(listOf("1x1x10")) == 14)
    part2(input).println()
}
