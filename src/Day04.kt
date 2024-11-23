@file:OptIn(ExperimentalStdlibApi::class)

import java.security.MessageDigest

fun main() {
    val messageDigest = MessageDigest.getInstance("MD5")
    fun part1(input: List<String>): Int {
        var i = 0
        while (true) {
            if (messageDigest.digest((input[0] + i.toString()).toByteArray()).toHexString().startsWith("00000")) {
                return i
            }
            i++
        }
    }

    fun part2(input: List<String>): Int {
        var i = 0
        while (true) {
            if (messageDigest.digest((input[0] + i.toString()).toByteArray()).toHexString().startsWith("000000")) {
                return i
            }
            i++
        }
    }
    messageDigest.digest("abcdef609043".toByteArray())
    check(part1(listOf("abcdef")) == 609043)
    check(part1(listOf("pqrstuv")) == 1048970)
    val input = listOf("bgvyzdsv")
    part1(input).println()
    part2(input).println()
}
