
val input = "day1.txt".read().map { it.toLong() }

// === PART 1

listOf(1)

input.indices
    .toList()
    .subList(1, input.size)
    .count { input[it] > input[it - 1] }

// === PART 2

(input + 0 + 0)
    .indices
    .toList()
    .subList(0, input.size - 3)
    .count { (input[it] + input[it + 1] + input[it + 2]) < (input[it + 1] + input[it + 2] + input[it + 3]) }
