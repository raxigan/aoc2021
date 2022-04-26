val input = "day4.txt".read()

class Boards {

    private val notWinning: List<Board>
    val winning: List<Board>

    constructor(boards: List<Board>, winning: List<Board> = listOf()) {
        this.notWinning = boards
        this.winning = winning
    }

    fun fill(no: Int) = Boards(notWinning.map { it.fill(no) }.filter { !it.wins() },
        winning + notWinning.map { it.fill(no) }.filter { it.wins() })

    fun allWinning() = notWinning.isEmpty()
}

class Board {

    private val numbers: List<List<Int>>

    constructor(numbers: List<Int>) {
        this.numbers = numbers.chunked(5)
    }

    private fun columns() = (0..4).map { no -> numbers.map { it[no] } }

    private fun List<Int>.wins() = this.all { it == -1 }

    fun wins() = numbers.any { it.wins() } || columns().any { it.wins() }

    fun score(no: Int) = numbers.flatten().filter { it != -1 }.sum() * no

    fun fill(no: Int) = Board(numbers.map { l -> l.map { if (it == no) -1 else it } }.flatten())
    override fun toString(): String {
        return "$numbers"
    }


}

val numbers = input[0].split(",").map { it.toInt() }

val chunked = input.subList(1, input.size).filter { it.isNotBlank() }.chunked(5)
val numberLists = chunked.map { it.map { it.trim().split(" ").filter { it.isNotBlank() }.map { it.toInt() } } }
val boards = Boards(numberLists.map { Board(it.flatten()) })

println(findFirstWinningBoardScore())
println(findLastWinningBoardScore())

fun findFirstWinningBoardScore(boards: Boards = this.boards, nums: List<Int> = numbers, id: Int = 0): Int {

    if (boards.winning.isNotEmpty()) return boards.winning.first().score(nums[id - 1])
    return findFirstWinningBoardScore(boards.fill(nums[id]), nums, id + 1)
}

fun findLastWinningBoardScore(boards: Boards = this.boards, nums: List<Int> = numbers, id: Int = 0): Int {

    val bds = boards.fill(nums[id])

    return if (bds.allWinning()) bds.winning.last().score(nums[id])
    else findLastWinningBoardScore(bds, nums, id + 1)
}
