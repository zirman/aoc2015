private enum class Register {
    RegA,
    RegB,
}

private sealed interface Inst
private data class HLF(val reg: Register) : Inst
private data class TPL(val reg: Register) : Inst
private data class INC(val reg: Register) : Inst
private data class JMP(val offset: Int) : Inst
private data class JIE(val reg: Register, val offset: Int) : Inst
private data class JIO(val reg: Register, val offset: Int) : Inst

fun main() {
    val instRegex = """([a-z]+) ([a-z0-9\\+\-]+)(?:, ([a-z0-9\\+\-]+))?""".toRegex()
    fun String.toRegister(): Register = when (this) {
        "a" -> Register.RegA
        "b" -> Register.RegB
        else -> throw IllegalStateException()
    }

    fun String.parseNumber(): Int = removePrefix("+").toInt()

    fun parse(input: List<String>): List<Inst> = input.map { line ->
        val (inst, reg, dat) = instRegex.matchEntire(line)!!.destructured
        when (inst) {
            "hlf" -> HLF(reg.toRegister())
            "tpl" -> TPL(reg.toRegister())
            "inc" -> INC(reg.toRegister())
            "jmp" -> JMP(reg.parseNumber())
            "jie" -> JIE(reg.toRegister(), dat.parseNumber())
            "jio" -> JIO(reg.toRegister(), dat.parseNumber())
            else -> throw IllegalStateException()
        }
    }

    fun List<String>.part1(): Int {
        val insts = parse(this)
        var a = 0
        var b = 0
        var ip = 0
        while (ip in insts.indices) {
            when (val inst = insts[ip]) {
                is HLF -> {
                    when (inst.reg) {
                        Register.RegA -> a /= 2
                        Register.RegB -> b /= 2
                    }
                    ip++
                }

                is INC -> {
                    when (inst.reg) {
                        Register.RegA -> a++
                        Register.RegB -> b++
                    }
                    ip++
                }

                is JIE -> when (inst.reg) {
                    Register.RegA -> {
                        if (a % 2 == 0) {
                            ip += inst.offset
                        } else {
                            ip++
                        }
                    }

                    Register.RegB -> {
                        if (b % 2 == 0) {
                            ip += inst.offset
                        } else {
                            ip++
                        }
                    }
                }

                is JIO -> when (inst.reg) {
                    Register.RegA -> {
                        if (a == 1) {
                            ip += inst.offset
                        } else {
                            ip++
                        }
                    }

                    Register.RegB -> {
                        if (b == 1) {
                            ip += inst.offset
                        } else {
                            ip++
                        }
                    }
                }

                is JMP -> {
                    ip += inst.offset
                }

                is TPL -> {
                    when (inst.reg) {
                        Register.RegA -> a *= 3
                        Register.RegB -> b *= 3
                    }
                    ip++
                }
            }
        }
        return b
    }

    // manually decompiled bytecode to this
    fun part2(): Long {
        var a = 113383L
        var b = 0L
        while (true) {
            if (a == 1L) {
                return b
            }
            b++
            if (a % 2 == 0L) {
                a /= 2
            } else {
                a *= 3
                a++
            }
        }
    }
    readInput("Day23").part1().println()
    part2().println()
}
