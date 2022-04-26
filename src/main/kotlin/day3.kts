val input = "day3.txt".read()

val result = parseReport()

//println(result.first)
//println(result.second)

println(result.first.toInt(2) * result.second.toInt(2))

fun parseReport(byteNo: Int = 0, gamma: String = "", epsilon: String = ""): Pair<String, String> {

    if (byteNo == input[0].length) return gamma to epsilon

    val most = input.map { it[byteNo] }.groupBy { it }

    if ((most['1']?.size ?: 0) > (most['0']?.size ?: 0)) {
        return parseReport(byteNo + 1, gamma + "1", epsilon + "0")
    } else {
        return parseReport(byteNo + 1, gamma + "0", epsilon + "1")
    }
}

// PART 2

val result2 = find()
val ox = result2.first
val co2 = result2.second

//println(ox)
//println(co2)

println(ox.toInt(2) * co2.toInt(2))

fun find(byteNo: Int = 0, remainMost: List<String> = input, remainLeast: List<String> = input): Pair<String, String> {

    if (remainMost.size == 1 && remainLeast.size == 1) return remainMost[0] to remainLeast[0]

    val groupedOxy = remainMost.map { it[byteNo] }.groupBy { it }
    val groupedCO2 = remainLeast.map { it[byteNo] }.groupBy { it }

    val most = if ((groupedOxy['1']?.size ?: 0) >= (groupedOxy['0']?.size ?: 0)) '1' else '0'
    val least = if ((groupedCO2['0']?.size ?: 0) <= (groupedCO2['1']?.size ?: 0)) '0' else '1'

    val r1 = if (remainMost.size == 1) remainMost else remainMost.filter { it[byteNo] == most }
    val r2 = if (remainLeast.size == 1) remainLeast else remainLeast.filter { it[byteNo] == least }

    return find(byteNo + 1, r1, r2)
}