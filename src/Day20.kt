fun main() {
    fun part1(input: Int): Int {
        fun sum(t: Int): Long {
            val t2 = t.toLong()
            return (((t2 * t2) + t2) / 2) * 10
        }

        val t = input
        top@ for (i in (1..Int.MAX_VALUE).first { i -> sum(i) >= t }..Int.MAX_VALUE) {
            var b = sum(i)
            var k = i
            while (k >= 1) {
                if (i % k != 0) {
                    b -= k * 10
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

    fun part2(input: Int): Int {
        fun sum(t: Int): Long {
            val t2 = t.toLong()
            return ((((t2 * t2) + t2) / 2) * 11)
        }

        val t = input
        top@ for (i in (51..Int.MAX_VALUE).first { i -> sum(i) >= t }..Int.MAX_VALUE) {
            var firstElf = i / 50
            var b = sum(i) - sum(firstElf - 1)
            var k = i
            while (k >= firstElf) {
                if (i % k != 0) {
                    b -= k * 11
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
    check(part1(10) == 1)
    check(part1(20) == 2)
    check(part1(30) == 2)
    check(part1(40) == 3)
    check(part1(50) == 4)
    check(part1(60) == 4)
    check(part1(70) == 4)
    check(part1(80) == 6)
    check(part1(90) == 6)
    check(part1(100) == 6)
    check(part1(110) == 6)
    check(part1(120) == 6)
    check(part1(130) == 8)
    check(part1(140) == 8)
    check(part1(150) == 8)
    part1(34000000).println()
    part2(34000000).println()
}
