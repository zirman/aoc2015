private data class Reindeer(val name: String, val speed: Int, val moveSeconds: Int, val restSeconds: Int)

fun main() {
    val reindeerRegex =
        """([a-zA-Z]+) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds\.""".toRegex()

    fun part1(input: List<String>, time: Int): Int {
        val reindeer = input.map { line ->
            val (name, speed, moveSeconds, restSeconds) = reindeerRegex.matchEntire(line)!!.destructured
            Reindeer(name, speed.toInt(), moveSeconds.toInt(), restSeconds.toInt())
        }
        return reindeer.map { r ->
            val cycleDistance = (time / (r.moveSeconds + r.restSeconds)) * (r.speed * r.moveSeconds)
            val remainder = time % (r.moveSeconds + r.restSeconds)
            cycleDistance + (remainder.coerceAtMost(r.moveSeconds) * r.speed)
        }.max()
    }

    fun part2(input: List<String>, time: Int): Int {
        val reindeer = input.map { line ->
            val (name, speed, moveSeconds, restSeconds) = reindeerRegex.matchEntire(line)!!.destructured
            Reindeer(name, speed.toInt(), moveSeconds.toInt(), restSeconds.toInt())
        }
        val distances = IntArray(reindeer.size)
        val scores = IntArray(reindeer.size)
        var i = 0
        while (i < time) {
            reindeer.forEachIndexed { index, r ->
                if (i % (r.moveSeconds + r.restSeconds) < r.moveSeconds) {
                    distances[index] += r.speed
                }
            }
            val m = distances.max()
            distances.forEachIndexed { index, d ->
                if (d == m) {
                    scores[index]++
                }
            }
            i++
        }
        return scores.max()
    }

    val testInput = """
        Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
        Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
    """.trimIndent().split('\n')
    check(part1(testInput, 1_000) == 1120)
    val input = readInput("Day14")
    part1(input, 2503).println()
    check(part2(testInput, 1_000) == 689)
    part2(input, 2503).println()
}
