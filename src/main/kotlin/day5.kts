import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

val input = "day5.txt".read()

class Line(
    val x1: Int,
    val y1: Int,
    val x2: Int,
    val y2: Int
) {
    fun isDiagonal() = abs(x1 - x2) == abs(y1 - y2)
}

val lines = input.map {

    val k = it.split("->")

    Line(
        k[0].split(",")[0].trim().toInt(),
        k[0].split(",")[1].trim().toInt(),
        k[1].split(",")[0].trim().toInt(),
        k[1].split(",")[1].trim().toInt()
    )

}

//    .filter { it.x1 == it.x2 || it.y1 == it.y2 } // PART 1
    .filter { (it.x1 == it.x2 || it.y1 == it.y2) || (abs(it.x1 - it.x2) == abs(it.y1 - it.y2)) }

fun perms(line: Line): List<Pair<Int, Int>> {

    return if (!line.isDiagonal()) {
        val r1 = min(line.x1, line.x2)..max(line.x1, line.x2)
        val r2 = min(line.y1, line.y2)..max(line.y1, line.y2)

        val pairs = r1.map { i -> r2.map { j -> i to j } }
            .flatten()

        pairs.toList()
    } else {
        val r1 = safeRange(line.x1, line.x2)
        val r2 = safeRange(line.y1, line.y2)

        r1.indices.map { r1[it] to r2[it] }
    }
}

fun safeRange(start:Int, end:Int) = if (start<= end) (start..end).toList() else (end..start).toList().reversed()

val grouped = lines.map { perms(it) }.flatten().groupBy { it }
val lappedTwice = grouped.values.filter { it.size >= 2 }

println(lappedTwice.size)