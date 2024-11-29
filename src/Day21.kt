private data class Equipment(val name: String, val cost: Int, val damage: Int, val armor: Int)
private data class Attributes(
    val cost: Int,
    val damage: Int,
    val armor: Int,
)

fun main() {
    val (weaponsInput, armorInput, ringsInput) =
        """
            Weapons:    Cost  Damage  Armor
            Dagger        8     4       0
            Shortsword   10     5       0
            Warhammer    25     6       0
            Longsword    40     7       0
            Greataxe     74     8       0

            Armor:      Cost  Damage  Armor
            Leather      13     0       1
            Chainmail    31     0       2
            Splintmail   53     0       3
            Bandedmail   75     0       4
            Platemail   102     0       5

            Rings:      Cost  Damage  Armor
            Damage +1    25     1       0
            Damage +2    50     2       0
            Damage +3   100     3       0
            Defense +1   20     0       1
            Defense +2   40     0       2
            Defense +3   80     0       3            
        """.trimIndent().split("\n\n")
    val splitRegex = """\s+""".toRegex()
    val weapons = weaponsInput.split('\n').drop(1).map { line ->
        val (name, costInput, damageInput, armorInput) = line.split(splitRegex)
        Equipment(name, costInput.toInt(), damageInput.toInt(), armorInput.toInt())
    }
    val armor = armorInput.split('\n').drop(1).map { line ->
        val (name, costInput, damageInput, armorInput) = line.split(splitRegex)
        Equipment(name, costInput.toInt(), damageInput.toInt(), armorInput.toInt())
    } + Equipment("", 0, 0, 0)
    val rings = ringsInput.split('\n').drop(1).map { line ->
        val (name, foo, costInput, damageInput, armorInput) = line.split(splitRegex)
        Equipment("$name $foo", costInput.toInt(), damageInput.toInt(), armorInput.toInt())
    } + Equipment("", 0, 0, 0)
    val p = weapons.flatMap { w ->
        armor.flatMap { a ->
            rings.flatMap { r1 ->
                rings.map { r2 ->
                    if (r1 == r2) {
                        Attributes(
                            cost = w.cost + a.cost + r1.cost,
                            damage = w.damage + a.damage + r1.damage,
                            armor = w.armor + a.armor + r1.armor,
                        )
                    } else {
                        Attributes(
                            cost = w.cost + a.cost + r1.cost + r2.cost,
                            damage = w.damage + a.damage + r1.damage + r2.damage,
                            armor = w.armor + a.armor + r1.armor + r2.armor,
                        )
                    }
                }
            }
        }
    }.sortedBy { it.cost }

    fun turnsUntilDead(damage: Int, armor: Int, hp: Int): Int {
        val dpt = (damage - armor).coerceAtLeast(1)
        return (hp + dpt - 1) / dpt
    }

    fun doesPlayerWin(playerDamage: Int, playerArmor: Int, bossDamage: Int, bossArmor: Int, bossHp: Int): Boolean {
        val turnsToWin = turnsUntilDead(playerDamage, bossArmor, bossHp)
        val turnsToLose = turnsUntilDead(bossDamage, playerArmor, 100)
        return turnsToWin <= turnsToLose
    }

    fun part1(input: List<String>): Int {
        val bossHp = input[0].split(": ")[1].toInt()
        val bossDamage = input[1].split(": ")[1].toInt()
        val bossArmor = input[2].split(": ")[1].toInt()
        return p.first { (_, damage, armor) ->
            doesPlayerWin(
                playerDamage = damage,
                playerArmor = armor,
                bossDamage = bossDamage,
                bossArmor = bossArmor,
                bossHp = bossHp,
            )
        }.cost
    }

    fun part2(input: List<String>): Int {
        val bossHp = input[0].split(": ")[1].toInt()
        val bossDamage = input[1].split(": ")[1].toInt()
        val bossArmor = input[2].split(": ")[1].toInt()
        return p.asReversed().first { (_, damage, armor) ->
            doesPlayerWin(
                playerDamage = damage,
                playerArmor = armor,
                bossDamage = bossDamage,
                bossArmor = bossArmor,
                bossHp = bossHp,
            ).not()
        }.cost
    }

    val input = """
        Hit Points: 109
        Damage: 8
        Armor: 2
    """.trimIndent().split('\n')
    part1(input).println()
    part2(input).println()
}
