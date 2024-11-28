fun main() {
    fun part1(input: List<String>): Int {
        val replacements = input.take(input.indexOf("")).map { line ->
            val (from, to) = line.split("=>")
            Pair(from.trim(), to.trim())
        }
        val molecule = input.drop(input.indexOf("") + 1).first()
        return buildSet {
            replacements.forEach { (from, to) ->
                var i = 0
                while (i < molecule.length - 1) {
                    val k = molecule.indexOf(from, i)
                    if (k == -1) {
                        break
                    }
                    add(
                        buildString {
                            append(molecule, 0, k)
                            append(to)
                            append(molecule, k + from.length, molecule.length)
                        },
                    )
                    i = k + 1
                }
            }
        }.count()
    }

    fun part2(input: List<String>): Int {
        val replacements = input.take(input.indexOf("")).map { line ->
            val (from, to) = line.split("=>")
            Pair(from.trim(), to.trim())
        }
        var molecules = input.drop(input.indexOf("") + 1).toSet()
        val visited = mutableSetOf<String>()
        for (h in 0..Int.MAX_VALUE) {
            if (molecules.contains("e")) {
                return h
            }
            molecules = buildSet {
                molecules.forEach { molecule ->
                    replacements.forEach { (to, from) ->
                        var i = 0
                        while (i < molecule.length) {
                            val k = molecule.indexOf(from, i)
                            if (k == -1) {
                                break
                            }
                            val m = buildString {
                                append(molecule, 0, k)
                                append(to)
                                append(molecule, k + from.length, molecule.length)
                            }
                            if (m == "e") {
                                return h + 1
                            } else if (m.contains('e').not() && visited.contains(m).not()) {
                                this@buildSet.add(m)
                                visited.add(m)
                            }

                            i = k + 1
                        }
                    }
                }
            }
        }
        throw IllegalStateException()
    }

    val testInput = """
        e => H
        e => O
        H => HO
        H => OH
        O => HH
        
        HOH
    """.trimIndent().split('\n')
    check(part1(testInput) == 4)
    val input = readInput("Day19")
    part1(input).println()
    val testInput2 = """
        e => H
        e => O
        H => HO
        H => OH
        O => HH
        
        HOH
    """.trimIndent().split('\n')
    check(part2(testInput2) == 3)
    val testInput3 = """
        e => H
        e => O
        H => HO
        H => OH
        O => HH

        HOHOHO
    """.trimIndent().split('\n')
    check(part2(testInput3) == 6)
//    part2(input).println()
}
