fun main() {
    val firstCode = 20151125L
    fun Long.next(): Long = (this * 252533) % 33554393

    fun part1(row: Int, col: Int): Long {
        var r = 0
        var c = 0
        var i = firstCode
        while (r != row - 1 || c != col - 1) {
            i = i.next()
            if (r == 0) {
                r = c + 1
                c = 0
            } else {
                r--
                c++
            }
        }
        return i
    }

    check(firstCode.next() == 31916031L)
    check(part1(row = 2, col = 6) == 4041754L)
    part1(row = 3010, col = 3019).println()
}
