private sealed interface Expr {
    data class Lit(val uShort: UShort) : Expr
    data class And(val left: String, val right: String) : Expr
    data class And2(val left: UShort, val right: String) : Expr
    data class Or(val left: String, val right: String) : Expr
    data class LeftShift(val name: String, val shift: Int) : Expr
    data class RightShift(val name: String, val shift: Int) : Expr
    data class Not(val name: String) : Expr
    data class Alias(val name: String) : Expr
}

fun main() {
    val litRegex = """\d+""".toRegex()
    val andRegex = """([a-z]+) AND ([a-z]+)""".toRegex()
    val and2Regex = """(\d+) AND ([a-z]+)""".toRegex()
    val orRegex = """([a-z]+) OR ([a-z]+)""".toRegex()
    val lShiftRegex = """([a-z]+) LSHIFT (\d+)""".toRegex()
    val rShiftRegex = """([a-z]+) RSHIFT (\d+)""".toRegex()
    val notRegex = """NOT ([a-z]+)""".toRegex()
    val aliasRegex = """[a-z]+""".toRegex()

    fun List<String>.parse(): Map<String, Expr> = buildMap {
        this@parse.forEach { line ->
            val (expression, variable) = line.split(" -> ")
            val litMatch = litRegex.matchEntire(expression)
            if (litMatch != null) {
                put(variable, Expr.Lit(litMatch.value.toUShort()))
                return@forEach
            }
            val andMatch = andRegex.matchEntire(expression)
            if (andMatch != null) {
                val (l, r) = andMatch.destructured
                put(variable, Expr.And(l, r))
                return@forEach
            }
            val and2Match = and2Regex.matchEntire(expression)
            if (and2Match != null) {
                val (l, r) = and2Match.destructured
                put(variable, Expr.And2(l.toUShort(), r))
                return@forEach
            }
            val orMatch = orRegex.matchEntire(expression)
            if (orMatch != null) {
                val (l, r) = orMatch.destructured
                put(variable, Expr.Or(l, r))
                return@forEach
            }
            val lShiftMatch = lShiftRegex.matchEntire(expression)
            if (lShiftMatch != null) {
                val (l, r) = lShiftMatch.destructured
                put(variable, Expr.LeftShift(l, r.toInt()))
                return@forEach
            }
            val rShiftMatch = rShiftRegex.matchEntire(expression)
            if (rShiftMatch != null) {
                val (l, r) = rShiftMatch.destructured
                put(variable, Expr.RightShift(l, r.toInt()))
                return@forEach
            }
            val notMatch = notRegex.matchEntire(expression)
            if (notMatch != null) {
                val (l) = notMatch.destructured
                put(variable, Expr.Not(l))
                return@forEach
            }
            val aliasMatch = aliasRegex.matchEntire(expression)
            if (aliasMatch != null) {
                put(variable, Expr.Alias(aliasMatch.value))
                return@forEach
            }
            throw IllegalStateException("No match for line ($line)")
        }
    }

    fun MutableMap<String, UShort>.eval(expressions: Map<String, Expr>, name: String): UShort = getOrPut(name) {
        when (val expr = expressions[name]) {
            is Expr.And -> eval(expressions, expr.left) and eval(expressions, expr.right)
            is Expr.And2 -> expr.left and eval(expressions, expr.right)
            is Expr.LeftShift -> (eval(expressions, expr.name).toInt() shl expr.shift).toUShort()
            is Expr.Lit -> expr.uShort
            is Expr.Not -> eval(expressions, expr.name).inv()
            is Expr.Or -> eval(expressions, expr.left) or eval(expressions, expr.right)
            is Expr.RightShift -> (eval(expressions, expr.name).toInt() shr expr.shift).toUShort()
            is Expr.Alias -> eval(expressions, expr.name)
            null -> throw IllegalStateException("Name does not exist ($name)")
        }
    }

    fun part1(input: List<String>): UShort {
        val expressions = input.parse()
        val memo = mutableMapOf<String, UShort>()
        fun eval(name: String): UShort = memo.getOrPut(name) {
            when (val expr = expressions[name]) {
                is Expr.And -> eval(expr.left) and eval(expr.right)
                is Expr.And2 -> expr.left and eval(expr.right)
                is Expr.LeftShift -> (eval(expr.name).toInt() shl expr.shift).toUShort()
                is Expr.Lit -> expr.uShort
                is Expr.Not -> eval(expr.name).inv()
                is Expr.Or -> eval(expr.left) or eval(expr.right)
                is Expr.RightShift -> (eval(expr.name).toInt() shr expr.shift).toUShort()
                is Expr.Alias -> eval(expr.name)
                null -> throw IllegalStateException("Name does not exist ($name)")
            }
        }
        return eval("a")
    }

    fun part2(input: List<String>): UShort {
        val expressions = input.parse()
        val memo = mutableMapOf<String, UShort>()
        val a = memo.eval(expressions, "a")
        memo.clear()
        memo["b"] = a
        return memo.eval(expressions, "a")
    }

    val testInput = readInput("Day07_test")
    val expressions = testInput.parse()
    val memo = mutableMapOf<String, UShort>()
    check(memo.eval(expressions, "d") == 72.toUShort())
    check(memo.eval(expressions, "e") == 507.toUShort())
    check(memo.eval(expressions, "f") == 492.toUShort())
    check(memo.eval(expressions, "g") == 114.toUShort())
    check(memo.eval(expressions, "h") == 65412.toUShort())
    check(memo.eval(expressions, "i") == 65079.toUShort())
    check(memo.eval(expressions, "x") == 123.toUShort())
    check(memo.eval(expressions, "y") == 456.toUShort())
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
