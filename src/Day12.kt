import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.intOrNull

private fun sumElement(e: JsonElement): Int = when (e) {
    is JsonArray -> sumArray(e)
    is JsonObject -> sumObject(e)
    is JsonPrimitive -> e.intOrNull ?: 0
}

private fun sumObject(o: JsonObject): Int {
    return o.values.sumOf { item -> sumElement(item) }
}

private fun sumArray(a: JsonArray): Int = a.sumOf { item -> sumElement(item) }

private fun sumElement2(e: JsonElement): Int = when (e) {
    is JsonArray -> sumArray2(e)
    is JsonObject -> sumObject2(e)
    is JsonPrimitive -> e.intOrNull ?: 0
}

private fun sumObject2(o: JsonObject): Int {
    return if (o.values.contains(JsonPrimitive("red"))) {
        0
    } else {
        o.values.sumOf { item ->
            sumElement2(item)
        }
    }
}

private fun sumArray2(a: JsonArray): Int = a.sumOf { item -> sumElement2(item) }

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            sumElement(Json.decodeFromString<JsonElement>(line))
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            sumElement2(Json.decodeFromString<JsonElement>(line))
        }
    }

    check(part1(listOf("[1,2,3]")) == 6)
    check(part1(listOf("{\"a\":2,\"b\":4}")) == 6)
    check(part1(listOf("[[[3]]]")) == 3)
    check(part1(listOf("{\"a\":{\"b\":4},\"c\":-1}")) == 3)
    check(part1(listOf("{\"a\":[-1,1]}")) == 0)
    check(part1(listOf("[-1,{\"a\":1}]")) == 0)
    check(part1(listOf("[]")) == 0)
    check(part1(listOf("{}")) == 0)
    val input = readInput("Day12")
    part1(input).println()
    check(part2(listOf("[1,2,3]")) == 6)
    check(part2(listOf("[1,{\"c\":\"red\",\"b\":2},3]")) == 4)
    check(part2(listOf("{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}")) == 0)
    check(part2(listOf("[1,\"red\",5]")) == 6)
    part2(input).println()
}
