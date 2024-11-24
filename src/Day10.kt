fun main() {
    fun expand(s: String): String {
        return buildString {
            var i = 0
            while (i < s.length) {
                var k = i + 1
                while (true) {
                    if (k >= s.length || s[k] != s[i]) {
                        this@buildString.append(k - i)
                        break
                    }
                    k++
                }
                this@buildString.append(s[i])
                i = k
            }
        }
    }

    fun part1(input: String): Int {
        var i = input
        repeat(40) {
            i = expand(i)
        }
        return i.length
    }

    fun part2(input: String): Int {
        var i = input
        repeat(50) {
            i = expand(i)
        }
        return i.length
    }
    check(expand("1") == "11")
    check(expand("11") == "21")
    check(expand("21") == "1211")
    check(expand("1211") == "111221")
    check(expand("111221") == "312211")
    val input = "1321131112"
    part1(input).println()
    part2(input).println()
}
