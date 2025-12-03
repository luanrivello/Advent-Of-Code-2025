package org.cesar

import java.io.File

fun main() {
    var password = 0
    var currentDial = 50

    val inputURL = {}.javaClass.getResource("/input.txt")
    val inputFile = File(inputURL.toURI())

    inputFile.bufferedReader().use { reader ->
        reader.forEachLine { line ->
            val (direction, amount) = parseLine(line)

            currentDial = rotateDial(direction, amount, currentDial)

            if (currentDial % 100 == 0) {
                password++
            }
        }
    }

    println(password)
}

private fun parseLine(line: String): Pair<Char, Int> {
    val direction = line.first()
    val amount = line.drop(1).toInt()
    return direction to amount
}

private fun rotateDial(direction: Char, amount: Int, current: Int): Int {
    val newCurrent: Int

    if (direction == 'L') {
        newCurrent = current - amount
    } else {
        newCurrent = current + amount
    }

    return newCurrent
}