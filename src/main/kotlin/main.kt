package smth

import java.util.function.Predicate

//quicksort в функциональном стиле
fun quicksort(toSort: List<Int>): List<Int> {
    if (toSort.count() < 2)
        return toSort
    val cmp = toSort[0]
    val leftPart = toSort.filter { it < cmp }
    val rightPart = toSort.filter {it > cmp}
    return quicksort(leftPart) + cmp + quicksort(rightPart)
}

//reverse с помощью foldRight
fun reverse(toReverse: List<Int>): List<Int> {
    return toReverse.foldRight(listOf()) {e, l -> l + e}
}

//filter, оставляет только элементы <5
fun myFilter(toFilter: List<Int>): List<Int> {
    return toFilter.fold(listOf()) {l, e -> if (e < 5) (l + e) else l}
}

//длина строк
fun lengths(strings: List<String>): List<Int> {
    return strings.map { it.count() }
}

fun sumsq(n: Int): Int {
    val numbers: MutableList<Int> = mutableListOf()
    for (i in 1..n)
        numbers.add(i)
    return numbers.filter{ it <= n }.map { it * it }.fold(0) {it, e -> e + it}
}

fun xOperation(x: Int, y:Int, operation: Char):Int {
    when (operation) {
        '+' -> return x + y
        '*' -> return x * y
        '=' -> return y
        else -> return x
    }
}

//mapAccumL, operations - ('+', '*', '.', '='), '.' - nothing, (x, y, '.', '=') - (x, x)
fun mapAccumL(x: Int, y: List<Int>, firstOperation: Char, secondOperation: Char): Pair<Int, List<Int>> {
    var newX = x
    var newY = y
    var curX = 0
    when (secondOperation) {
        '+' -> newY = y.map {
            it -> curX = newX
            newX = xOperation(newX, it, firstOperation)
            it + curX
        }
        '*' -> newY = y.map {
            it -> curX = newX
            newX = xOperation(newX, it, firstOperation)
            it * curX
        }
        '=' -> newY = y.map {
            it -> curX = newX
            newX = xOperation(newX, it, firstOperation)
            curX
        }
        else -> newX = y.fold(newX){l, r -> xOperation(l, r, firstOperation)}
    }
    return Pair(newX, newY)
}

fun main() {
    val toSort: List<Int> = listOf(10, 5, 6, 2, 4, 9, 1, 7, 3, 8)
    println(quicksort(toSort))

    val toReverse: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(reverse(toReverse))

    val toFilter: List<Int> = listOf(10, 5, 6, 2, 4, 9, 1, 7, 3, 8)
    println(myFilter(toFilter))

    val strings: List<String> = listOf("a", "aa", "aaa", "", "gdrhvkrsjhgvsbr")
    println(lengths(strings))

    println(sumsq(3))

    val x = 5
    val y = listOf(2, 4, 8)
    println(mapAccumL(x, y, '.', '='))
}