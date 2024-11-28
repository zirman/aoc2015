fun main() {
    fun part1(input: Long): Long {
        fun sum(t: Long): Long = ((t * t) + t) / 2
        val t = input / 10
        top@for (i in (1..Long.MAX_VALUE).first { i -> sum(i) >= t }..Long.MAX_VALUE) {
            if (i % 1_000L == 0L) println(i)
            var b = sum(i)
            var k = i
            while (k >= 1L) {
                if (i % k != 0L) {
                    b -= k
                    if (b < t) {
                        continue@top
                    }
                }
                k--
            }
            return i
        }
        throw IllegalStateException()
    }

    fun part2(input: List<String>): Long {
        return input.size.toLong()
    }
    check(part1(10) == 1L)
    check(part1(20) == 2L)
    check(part1(30) == 2L)
    check(part1(40) == 3L)
    check(part1(50) == 4L)
    check(part1(60) == 4L)
    check(part1(70) == 4L)
    check(part1(80) == 6L)
    check(part1(90) == 6L)
    check(part1(100) == 6L)
    check(part1(110) == 6L)
    check(part1(120) == 6L)
    check(part1(130) == 8L)
    check(part1(140) == 8L)
    check(part1(150) == 8L)
    part1(34000000).println()
//    check(part2(listOf("test_input")) == 1)
//    part2(input).println()
}
