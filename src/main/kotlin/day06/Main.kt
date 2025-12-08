package org.cesar.day06

import java.io.File
import kotlin.system.measureNanoTime
import kotlin.text.split

var answer:Long = 0

fun main() {
    val time = measureNanoTime {
        val inputFile = readFile("input.txt")
        val lines = inputFile.readLines()

        val (worksheet, operators) = extractProblems(lines)

        //worksheet.printResult()
        //answer = worksheet.doTheMathWithStr(operators)
        answer = worksheet.doTheMathWithInt(operators)
    }

    println("Execution time: ${time / 1_000_000_000.0}s")
    println("Answer: $answer")
}

private fun extractProblems(worksheet: List<String>): Pair<List<List<Int>>, List<Char>>  {
    val maxLength = worksheet.maxOf { it.length }

    val paddedSpaces = worksheet
        .dropLast(1)
        .map { line ->
            line.padEnd(maxLength, ' ')
        }
    //println(paddedSpaces.toString())

    val problemsStr = mutableListOf(mutableListOf<String>())
    for (col in paddedSpaces[0].indices) {
        val num = paddedSpaces.fold("") { res, string ->
            res + string[col]
        }

        if (num.isBlank()){
            problemsStr.add(mutableListOf())
        } else {
            problemsStr.last().add(num)
        }
    }
    //problemsStr.printResult()

    val problems = problemsStr.map { problem ->
        problem.map { it.trim().toInt() }
    }
    //println(problemsInt.toString())

    val operators = worksheet
        .last()
        .toCharArray()
        .filter { !it.isWhitespace() }

    return problems to operators
}

private fun List<List<Int>>.doTheMathWithInt(operators: List<Char>): Long {
    require(isNotEmpty()) { "Matrix must not be empty" }

    return this.withIndex().sumOf { (col, problem) ->
        if (operators[col] == '*') {
            problem.fold(1L) { acc, num ->
                acc * num.toLong()
            }

        } else if (operators[col] == '+') {
            problem.sumOf { num -> num.toLong() }

        } else {
            error("Unsuported operation")
        }
    }
}

private fun List<List<String>>.doTheMathWithStr(operators: List<String>): Long {
    require(isNotEmpty()) { "Matrix must not be empty" }

    return this[0].indices.sumOf { column ->
        if (operators[column] == "*") {
            this.fold(1L) { acc, problem ->
                acc * problem[column].toLong()
            }

        } else if (operators[column] == "+") {
            this.sumOf { it[column].toLong() }

        } else {
            error("Unsuported operation")
        }
    }
}

private fun readFile(file: String): File {
    val day = object {}::class.java.packageName.substringAfterLast('.')
    val inputURL = {}.javaClass.getResource("/$day/$file")
    return File(inputURL.toURI())
}

fun List<List<String>>.printResult() {
    for ((num, row) in this.withIndex()) {
        val line = row
        //val line = row.joinToString(" ")
        println("$num | $line")
    }
}
