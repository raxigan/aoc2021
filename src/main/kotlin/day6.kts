val input = "day6.txt".read()

val fishes = input[0].split(",").map { it.toInt() }
//val fishes = sequenceOf(3, 4, 3, 1, 2)

val days = 256
val fMap: Map<Int, Long> =
    (0..8).associateWith { 0L } + fishes.groupingBy { it }.eachCount().mapValues { it.value.toLong() }

println(afterDays())

fun afterDays(f: Map<Int, Long> = fMap, day: Int = 0): Long {

//    println(if(day == 0) "Initial state: $f" else "Day ${day}: $f")
    if (day == days) {
        return f.values.fold(0L) { acc, it -> acc + it }
    }

    val newF = 8 to f[0]!!

    val afterThis = f.mapValues {
        when (it.key) {
            8 -> f[0]!!
            6 -> f[7]!! + f[0]!!
            else -> f[it.key + 1]!!
        }
    }

    return afterDays(afterThis + newF, day + 1)
}