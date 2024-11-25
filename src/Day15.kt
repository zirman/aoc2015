private data class Ingredient(
    val name: String,
    val capacity: Int,
    val durability: Int,
    val flavor: Int,
    val texture: Int,
    val calories: Int,
)

private fun makeScoops(count: Int, spoons: Int): List<List<Int>> = when (count) {
    0 -> {
        emptyList()
    }

    1 -> {
        listOf(listOf(spoons))
    }

    else -> {
        (0..spoons).flatMap { scoops ->
            makeScoops(count - 1, spoons - scoops).map { tail ->
                buildList {
                    add(scoops)
                    addAll(tail)
                }
            }
        }
    }
}

fun main() {
    val ingredientRegex =
        """([a-zA-Z]+): capacity (-?\d+), durability (-?\d+), flavor (-?\d+), texture (-?\d+), calories (-?\d+)"""
            .toRegex()

    fun part1(input: List<String>): Int {
        val ingredients = input.map { line ->
            val (name, capacity, durability, flavor, texture, calories) = ingredientRegex.matchEntire(line)!!.destructured
            Ingredient(name, capacity.toInt(), durability.toInt(), flavor.toInt(), texture.toInt(), calories.toInt())
        }
        return makeScoops(ingredients.size, 100).map { scoops ->
            val capacity = ingredients.mapIndexed { index, ingredient ->
                scoops[index] * ingredient.capacity
            }.sum().coerceAtLeast(0)
            val durability = ingredients.mapIndexed { index, ingredient ->
                scoops[index] * ingredient.durability
            }.sum().coerceAtLeast(0)
            val flavor = ingredients.mapIndexed { index, ingredient ->
                scoops[index] * ingredient.flavor
            }.sum().coerceAtLeast(0)
            val texture = ingredients.mapIndexed { index, ingredient ->
                scoops[index] * ingredient.texture
            }.sum().coerceAtLeast(0)
            capacity * durability * flavor * texture
        }.max()
    }

    fun part2(input: List<String>): Int {
        val ingredients = input.map { line ->
            val (name, capacity, durability, flavor, texture, calories) = ingredientRegex.matchEntire(line)!!.destructured
            Ingredient(name, capacity.toInt(), durability.toInt(), flavor.toInt(), texture.toInt(), calories.toInt())
        }
        return makeScoops(ingredients.size, 100).map { scoops ->
            val capacity = ingredients.mapIndexed { index, ingredient ->
                scoops[index] * ingredient.capacity
            }.sum().coerceAtLeast(0)
            val durability = ingredients.mapIndexed { index, ingredient ->
                scoops[index] * ingredient.durability
            }.sum().coerceAtLeast(0)
            val flavor = ingredients.mapIndexed { index, ingredient ->
                scoops[index] * ingredient.flavor
            }.sum().coerceAtLeast(0)
            val texture = ingredients.mapIndexed { index, ingredient ->
                scoops[index] * ingredient.texture
            }.sum().coerceAtLeast(0)
            val calories = ingredients.mapIndexed { index, ingredient ->
                scoops[index] * ingredient.calories
            }.sum().coerceAtLeast(0)
            if (calories == 500) {
                capacity * durability * flavor * texture
            } else {
                0
            }
        }.max()
    }
    check(
        part1(
            """
                Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
                Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
            """.trimIndent().split('\n'),
        ) == 62842880,
    )
    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}
