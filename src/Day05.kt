fun main() {
    val vowels = "aeiou".toSet()
    val forbidden = setOf("ab", "cd", "pq", "xy")
    fun part1(input: List<String>): Int {
        return input.count { string ->
            val hasVowels = string.count { it in vowels } >= 3
            val hasRepeat = string.windowed(2).any { it[0] == it[1] }
            val hasForbidden = string.windowed(2).any { it in forbidden }
            hasVowels && hasRepeat && hasForbidden.not()
        }
    }

    fun part2(input: List<String>): Int {
        fun String.hasDouble(): Boolean {
            for (i in 0..<length - 1) {
                val x = substring(i, i + 2)
                if (substring(i + 2).windowed(2).any { it == x }) {
                    return true
                }
            }
            return false
        }
        fun String.hasRepeat(): Boolean {
            return windowed(3).any { it[0] == it[2] }
        }
        return input.count { string ->
            string.hasDouble() && string.hasRepeat()
        }
    }
    check(part1(listOf("ugknbfddgicrmopn")) == 1)
    check(part1(listOf("aaa")) == 1)
    check(part1(listOf("jchzalrnumimnmhp")) == 0)
    check(part1(listOf("haegwjzuvuyypxyu")) == 0)
    check(part1(listOf("dvszwmarrgswjxmb")) == 0)
    val input = readInput("Day05")
    part1(input).println()
    check(part2(listOf("xyxy")) == 1)
    check(part2(listOf("aabcdefgaaxyx")) == 1)
    check(part2(listOf("aaa")) == 0)
    check(part2(listOf("xyx")) == 0)
    check(part2(listOf("abcdefeghi")) == 0)
    part2(input).println()
}
