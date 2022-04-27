import kotlin.math.abs

val input = "day7.txt".read()[0].split(",").map { it.toInt() }

//val minFuel = (0..input.maxOrNull()!!).map { input.map { i -> abs(i - it) }.sum() }.minOrNull()!!
val minFuel = (0..input.maxOrNull()!!).map { input.map { i -> (0..abs(i - it)).reduce { acc, it -> acc + it } }.sum() }
    .minOrNull()!!

println(minFuel)