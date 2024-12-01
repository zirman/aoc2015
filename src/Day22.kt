import java.util.PriorityQueue

data class WizSim(
    var bossHp: Int,
    var wizardsTurn: Boolean = true,
    var hp: Int = 50,
    var mana: Int = 500,
    var spent: Int = 0,
    var shieldTimer: Int = 0,
    var poisonTimer: Int = 0,
    var rechargeTimer: Int = 0,
)

fun WizSim.isGameOver(): Boolean = isWin() || isLoss()
fun WizSim.isWin(): Boolean = bossHp <= 0
fun WizSim.isLoss(): Boolean = hp <= 0

private fun WizSim.iterate(queue: PriorityQueue<WizSim>, bossDamage: Int) {
    copy().run {
        if (wizardsTurn) {
            wizardsTurn = false
            // passive effects
            if (shieldTimer > 0) {
                shieldTimer--
            }
            if (rechargeTimer > 0) {
                rechargeTimer--
                mana += 101
            }
            if (poisonTimer > 0) {
                poisonTimer--
                bossHp -= 3
            }
            // check for boss death
            if (isGameOver()) {
                queue.add(copy())
                return@run
            }
            // cast spells
            // magic missile
            if (mana >= 53) {
                queue.add(
                    copy().apply {
                        bossHp -= 4
                        mana -= 53
                        spent += 53
                    }
                )
            }
            // cast drain
            if (mana >= 73) {
                queue.add(
                    copy().apply {
                        bossHp -= 2
                        hp += 2
                        mana -= 73
                        spent += 73
                    }
                )
            }
            // cast shield
            if (shieldTimer == 0 && mana >= 113) {
                queue.add(
                    copy().apply {
                        shieldTimer = 6
                        mana -= 113
                        spent += 113
                    }
                )
            }
            // cast poison
            if (poisonTimer == 0 && mana >= 173) {
                queue.add(
                    copy().apply {
                        poisonTimer = 6
                        mana -= 173
                        spent += 173
                    }
                )
            }
            // cast recharge
            if (rechargeTimer == 0 && mana >= 229) {
                queue.add(
                    copy().apply {
                        rechargeTimer = 5
                        mana -= 229
                        spent += 229
                    }
                )
            }
        } else {
            wizardsTurn = true
            // passive effects
            if (rechargeTimer > 0) {
                rechargeTimer--
                mana += 101
            }
            if (poisonTimer > 0) {
                poisonTimer--
                bossHp -= 3
            }
            val adjustedBossDamage = if (shieldTimer > 0) {
                shieldTimer--
                (bossDamage - 7).coerceAtLeast(1)
            } else {
                bossDamage
            }
            // check for boss death
            if (isGameOver()) {
                queue.add(copy())
            } else {
                hp -= adjustedBossDamage
                queue.add(copy())
            }
        }
    }
}

fun main() {
    fun part1(bossHp: Int, bossDamage: Int): Int {
        val q = PriorityQueue<WizSim>(compareBy<WizSim> { it.spent }.thenBy { it.bossHp })
        q.add(WizSim(bossHp = bossHp))
        while (true) {
            val ws = q.remove()
            if (ws.isWin()) {
                return ws.spent
            }
            if (ws.isLoss()) {
                continue
            }
            ws.iterate(q, bossDamage)
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val (bossHp, bossDamage) = """
        Hit Points: 55
        Damage: 8
    """.trimIndent().split('\n').map { line -> line.split(": ")[1].toInt() }
    part1(bossHp, bossDamage).println()
//    part2(input).println()
}
