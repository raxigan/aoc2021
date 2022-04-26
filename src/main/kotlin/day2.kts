val input = "day2.txt".read()

println(input)

fun String.toNum() = this.split(" ")[1].toInt()

val horizontal = input.filter { it.startsWith("forward") }
    .map { it.toNum() }
    .sum()

val vertical = input.filter { !it.startsWith("forward") }
    .map { if (it.startsWith("down")) it.toNum() else -it.toNum() }
    .sum()

println(horizontal)
println(vertical)

// -2120749
println(horizontal * vertical)

// PART 2

val result = readCommand()

println(result.first)
println(result.second)
println(result.first * result.second)


fun readCommand(lineNo: Int = 0, horizontal: Long = 0, depth: Long = 0, aim: Long = 0): Pair<Long, Long> {

    if (lineNo == input.size) {
        return horizontal to depth
    }

    return if (input[lineNo].startsWith("down")) {
        readCommand(lineNo + 1, horizontal, depth, aim + input[lineNo].toNum())
    } else if (input[lineNo].startsWith("up")) {
        readCommand(lineNo + 1, horizontal, depth, aim - input[lineNo].toNum())
    } else {
        readCommand(lineNo + 1, horizontal + input[lineNo].toNum(), depth + input[lineNo].toNum() * aim, aim)
    }
}