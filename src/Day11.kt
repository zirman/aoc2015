fun main() {
    fun nextPassword(password: String): String {
        val codes = password.map { it - 'a' }.asReversed().toMutableList()
        var carry = 1
        for (i in codes.indices) {
            val sum = codes[i] + carry
            codes[i] = sum % 26
            carry = sum / 26
        }
        while (carry > 0) {
            codes.add(carry % 26)
            carry /= 26
        }
        return buildString {
            codes.asReversed().forEach { code ->
                append('a' + code)
            }
        }
    }

    fun hasOneFlush(password: String): Boolean {
        var lastC = '\u0000'
        var lastI = -1
        password.forEachIndexed { index, c ->
            if (c.code != lastC.code + (index - lastI)) {
                lastC = c
                lastI = index
            } else if (index - lastI >= 2) {
                return true
            }
        }
        return false
    }

    fun hasForbiddenCharacters(password: String): Boolean {
        return password.any { it in "iol" }
    }

    fun hasTwoDoubles(password: String): Boolean {
        var double = false
        var i = 0
        while (i + 1 < password.length) {
            if (password[i] == password[i + 1]) {
                i += 2
                while (i + 1 < password.length) {
                    if (password[i] == password[i + 1]) {
                        return true
                    }
                    i++
                }
            }
            i++
        }
        return false
    }

    fun part1(input: String): String {
        var password = input
        while (true) {
            password = nextPassword(password)
            if (hasOneFlush(password) && hasForbiddenCharacters(password).not() && hasTwoDoubles(password)) {
                return password
            }
        }
    }

    fun part2(input: String): String {
        return part1(part1(input))
    }
    check(nextPassword("xx") == "xy")
    check(nextPassword("xy") == "xz")
    check(nextPassword("xz") == "ya")
    check(nextPassword("ya") == "yb")
    check(hasOneFlush("hijklmmn"))
    check(hasOneFlush("abd").not())
    check(hasForbiddenCharacters("i"))
    check(hasForbiddenCharacters("o"))
    check(hasForbiddenCharacters("l"))
    check(hasTwoDoubles("aa").not())
    check(hasTwoDoubles("aaa").not())
    check(hasTwoDoubles("aaaa"))
    check(hasTwoDoubles("aabb"))
    val input = "hxbxwxba"
    part1(input).println()
    part2(input).println()
}
