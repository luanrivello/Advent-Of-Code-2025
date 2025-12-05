package org.cesar

import java.io.File
import kotlin.system.measureNanoTime
import kotlin.text.toInt

var answer:Long = 0

fun main() {
    val time = measureNanoTime {
        val input  = readFile()
        val ranges = input.split(',')

        //findInvalidIDsInRange("10000000", "44444555")
        //splitNumberInParts("123456", 6, 3)

        for (range in ranges) {
            val (lowerBound, upperBound) = range.split('-', limit = 2)
            findInvalidIDsInRange(lowerBound, upperBound)
        }
    }

    println("Execution time: ${time / 1_000_000_000.0}s")
    println("Answer: $answer")
}

private fun readFile(): String {
    val inputURL = {}.javaClass.getResource("/day02/example.txt")
    val inputFile = File(inputURL.toURI())

    return inputFile.readText().trim()
}

private fun findInvalidIDsInRange (lowerBound: String, upperBound: String) {
    println("|| $lowerBound -> $upperBound ||")
    var currentIdStr = lowerBound
    var currentIdNum = lowerBound.toLong()

    while (true) {
        val digitCount = currentIdStr.length

        var splits: List<Long>

        if (digitCount == 1) {
            currentIdNum = 11L
            splits = listOf(1L, 1L)

        } else if (digitCount % 2 != 0) {
            splits = splitNumberInParts(currentIdStr, digitCount, 1)
            splits = goToNearestInvalidId(splits)
            currentIdNum = splits.joinToString("").toLong()

        } else {
                //TODO if odd check for 1 -> digitCount/2 just split it in all ways
                splits = splitNumberInParts(currentIdStr, digitCount, digitCount/2)
                splits = goToNearestInvalidId(splits)
                currentIdNum = splits.joinToString("").toLong()
        }

        if (!processId(currentIdNum, upperBound)) {
            println("** OUT OF BOUNDS **")
            return
        }

        println("CurrentId: $currentIdNum")
        //println("Answer: $answer")

        val newSplits = goToNextInvalidId(splits)
        //println(newSplits)
        currentIdStr = newSplits.joinToString("")
        currentIdNum = currentIdStr.toLong()
        /*
        for (split in 1 until currentIdNum/2) {
            var (firstHalf, secondHalf) = splitInHalf(currentIdNum.toString())
            println("Current ID: $firstHalf|$secondHalf")

            val (newFirstHalf, newSecondHalf) = goToNextInvalidId(firstHalf, secondHalf)

            currentIdStr = newFirstHalf.toString() + newSecondHalf.toString()
            currentIdNum = currentIdStr.toLong()

            if (!processId(currentIdNum, upperBound)) {
                println("** OUT OF BOUNDS **")
                return
            }
        }

        currentIdStr = (currentIdNum + additionFactor).toString()
        currentIdNum = currentIdStr.toLong()
         */
    }
}

private fun splitNumberInParts(numberStr: String, digitCount: Int, split: Int): List<Long> {
    val result = mutableListOf<Long>()

    for (i in 0 ..< digitCount step split) {
        result.add(numberStr.substring(i, split+i).toLong())
    }

    //println(result)
    return result
}

private fun computeNextIdParameters(currentIdNum: Long, digitCount: Int): Pair<Long, Long> {
    if (digitCount % 2 != 0) {
        val newIdNum = TenToThePowerOf(digitCount) + TenToThePowerOf(digitCount / 2)
        val additionFactor = TenToThePowerOf((digitCount + 1) / 2) + 1
        return newIdNum to additionFactor
    } else {
        val additionFactor = TenToThePowerOf(digitCount / 2) + 1
        return currentIdNum to additionFactor
    }
}

private fun TenToThePowerOf(times: Int): Long {
    var result: Long = 1
    repeat(times) { result *= 10 }
    return result
}

private fun splitInHalf(number: String): Pair<Int, Int> {
    val numberMidPoint = (number.length/2)
    val firstHalf = number.take(numberMidPoint).toInt()
    val secondHalf = number.drop(numberMidPoint).toInt()
    return firstHalf to secondHalf
}

fun goToNearestInvalidId(splits: List<Long>): List<Long> {
    val largestIndice = splits.indices.maxBy { splits[it] }
    val firstValue = splits[0]

    val newList =
        if (largestIndice == 0 && splits.size % 2 != 0) {
            splits.map { firstValue }
        } else if (largestIndice == 0 && splits.size % 2 != 0) {
            //? idk
            splits.map { firstValue }
        } else {
            splits.map { splits[0] + 1 }
        }

    //println(newList)
    return newList
}

fun goToNearestInvalidId(firstHalf: Int, secondHalf: Int): Pair<Int, Int> {
    if (firstHalf > secondHalf) {
        val newSecondHalf = firstHalf
        println("Jump to ID: $firstHalf|$secondHalf")
        return firstHalf to newSecondHalf

    } else if (firstHalf < secondHalf) {
        val newFirstHalf = firstHalf + 1
        println("Jump to ID: $newFirstHalf|$newFirstHalf")
        return newFirstHalf to newFirstHalf
    }

    return firstHalf to secondHalf
}

private fun processId(currentIdNum: Long, upperBound: String): Boolean {
    if (currentIdNum <= upperBound.toLong()) {
        answer += currentIdNum
        return true
    } else {
        return false
    }
}

private fun goToNextInvalidId(splits: List<Long>): List<Long> {
    return if (splits.all { it == 9L }) {
        val newSize = splits.size + 1
        List(newSize) {
            i -> if (i == 0 || i == newSize/2) 1L else 0L
        }
    } else {
        splits.map { splits[0] + 1 }
    }
}
