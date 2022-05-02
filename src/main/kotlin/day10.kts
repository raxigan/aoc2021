val input = "day10.txt".read()

val totalPoints = input.mapNotNull { parse(it) }.map { it.count() }.sum()
println(totalPoints)

val closingLines =
    input.mapNotNull { incomplete(it) }.map { it.map { it.toString().closing() }.reversed().joinToString("") }

val pointsList = closingLines.map { score(it) }.sorted()
println(pointsList[pointsList.size / 2 + 1])

fun parse(line: String, id: Int = 0, soFar: List<String> = listOf()): String? {

    if (id == line.length) return null
    val me = line[id].toString()

    if (me.isOpening()) {
        return parse(line, id + 1, soFar + me)
    } else {
        if (soFar.isEmpty()) return null
        if (me != soFar.last().closing()) return me
        else return parse(line, id + 1, soFar.withIndex().filter { it.index != soFar.size - 1 }.map { it.value })
    }
}

fun incomplete(line: String, id: Int = 0, soFar: List<String> = listOf()): String? {

    if (id == line.length) return soFar.joinToString("")
    val me = line[id].toString()

    if (me.isOpening()) {
        return incomplete(line, id + 1, soFar + me)
    } else {
        if (soFar.isEmpty()) return null
        if (me != soFar.last().closing()) return null
        else return incomplete(line, id + 1, soFar.withIndex().filter { it.index != soFar.size - 1 }.map { it.value })
    }
}

fun String.isOpening(): Boolean {
    return this == "(" || this == "[" || this == "{" || this == "<"
}

fun String.closing(): String {
    return when (this) {
        "(" -> ")"
        "[" -> "]"
        "{" -> "}"
        "<" -> ">"
        else -> ""
    }
}

fun String.count(): Long {
    return when (this) {
        ")" -> 3
        "]" -> 57
        "}" -> 1197
        ">" -> 25137
        else -> throw java.lang.RuntimeException()
    }
}

fun Char.count2(): Long {
    return when (this) {
        ')' -> 1
        ']' -> 2
        '}' -> 3
        '>' -> 4
        else -> throw java.lang.RuntimeException()
    }
}

fun score(line: String, id: Int = 0, soFar: Long = 0): Long {

    if (id == line.length) return soFar

    val sc = (soFar * 5) + line[id].count2()

    return score(line, id + 1, sc)
}