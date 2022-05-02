val input = "day9.txt".read()

class Board {

    private val nums: List<List<Int>>
    private val maxRow: Int
    private val maxCol: Int

    constructor(lines: List<String>) {
        val colNo = lines[0].length

        this.nums = listOf((0..colNo + 1).map { 10 }.toList()) + lines.map {
            listOf(10) + it.split("").filter { it.isNotBlank() }.map { it.toInt() } + listOf(10)
        }.toList() + listOf((0..colNo + 1).map { 10 }.toList())
        this.maxRow = nums.size - 2
        this.maxCol = nums[0].size - 2
    }

    override fun toString(): String {
        return this.nums.map { it.map { it.toString().padStart(2, ' ') }.toString() }.joinToString("\n")
    }

    fun isLowest(row: Int, col: Int): Boolean {
        val me = nums[row][col]

        val min = listOf(
            nums[row - 1][col],
            nums[row][col - 1],
            nums[row][col + 1],
            nums[row + 1][col]
        ).minOrNull()!!

        return me < min
    }

    fun lows(row: Int = 1, col: Int = 1, soFar: List<Int> = listOf()): List<Int> {

        val lowest = isLowest(row, col)

        val soF = if (lowest) soFar + nums[row][col] else soFar

        if (row == maxRow && col == maxCol) return soF

        return lows(
            if (col != maxCol) row else row + 1,
            if (col != maxCol) col + 1 else 1,
            soF
        )
    }

    fun greaterN(row: Int, col: Int, soFar: Set<Pair<Pair<Int, Int>, Int>> = setOf()): Set<Pair<Pair<Int, Int>, Int>> {

        if(soFar.any { it.first == Pair(row, col)}) return soFar

        val me = nums[row][col]

        val n = setOf(
            Pair(Pair(row - 1, col), nums[row - 1][col]),
            Pair(Pair(row, col - 1), nums[row][col - 1]),
            Pair(Pair(row, col + 1), nums[row][col + 1]),
            Pair(Pair(row + 1, col), nums[row + 1][col])
        )

        val grN = n.filter { it.second > me }.filter { it.second != 10 && it.second != 9 }
            .filter { k -> !soFar.any { it.first == k.first } }
            .toSet()

        return soFar + grN.map { greaterN(it.first.first, it.first.second, soFar) }
            .flatten().toSet() + Pair(Pair(row, col), me)

    }

    fun basins(
        row: Int = 1,
        col: Int = 1,
        soFar: Set<Set<Pair<Pair<Int, Int>, Int>>> = setOf()
    ): Set<Set<Pair<Pair<Int, Int>, Int>>> {

        if (row == maxRow && col == maxCol) return soFar

        if(soFar.any{ it.any { it.first == Pair(row, col) }}) {
            return basins(
                if (col != maxCol) row else row + 1,
                if (col != maxCol) col + 1 else 1,
                soFar
            )
        }

        val lowest = isLowest(row, col)

        val soF =
            if (lowest) soFar + setOf((greaterN(row, col)), setOf(Pair(Pair(row, col), nums[row][col]))) else soFar

        return basins(
            if (col != maxCol) row else row + 1,
            if (col != maxCol) col + 1 else 1,
            soF
        )
    }
}

val board = Board(input)
val basins = board.basins()

println(basins)

basins.toList().sortedByDescending { it.size }.map { it.size }.take(3).forEach { println(it) }
val largest = basins.toList().sortedByDescending { it.size }.map { it.size }.take(3)

println(largest.reduce { a, b -> a * b })
