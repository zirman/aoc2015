import java.util.PriorityQueue

data class MoleculeSearch(val molecule: String, val steps: Int)

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
        val q =
            PriorityQueue<MoleculeSearch>(compareBy<MoleculeSearch> { t -> t.molecule.length }.thenBy { value -> value.steps })
        q.add(MoleculeSearch(input.drop(input.indexOf("") + 1).first(), 0))
        while (true) {
            val next = q.remove()
            if (next.molecule == "e") {
                return next.steps
            }
            val molecule = next.molecule
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
                    q.add(MoleculeSearch(m, next.steps + 1))
                    i = k + 1
                }
            }
        }
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
    part2(input).println()
}
