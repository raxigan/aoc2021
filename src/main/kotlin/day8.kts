val input = "day8.txt".read()

val res = input.map { sumLine(it) }

println(res.sum())

fun sumLine(line: String): Int {

    val left = line.split("|")[0].split(" ").filter { it.isNotBlank() }.map { it.trim() }

    val one = left.first { it.length == 2 }
    val four = left.first { it.length == 4 }
    val seven = left.first { it.length == 3 }

    val right = line.split("|")[1].split(" ").filter { it.isNotBlank() }.map { it.trim() }

    return right.map {

        when (it.length) {
            2 -> 1
            3 -> 7
            4 -> 4
            7 -> 8
            else -> {
                when (it.length) {
                    5 -> {
                        if (one.all { k -> it.contains(k) }) 3
                        else if ((it.toList() - four.toList()).size == 2) 5
                        else 2
                    }
                    6 -> {
                        if (four.all { k -> it.contains(k) }) 9
                        else if (seven.all { k -> it.contains(k) }) 0
                        else 6
                    }
                    else -> 0
                }
            }
        }


    }
        .joinToString("")
        .toInt()
}

