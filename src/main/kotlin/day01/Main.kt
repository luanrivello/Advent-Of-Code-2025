package org.cesar.day01

import java.io.File
import kotlin.math.absoluteValue
import kotlin.system.measureNanoTime

var password = 0
var currentDial = 50

fun main() {
    val time = measureNanoTime {
        val inputURL = {}.javaClass.getResource("/day01/input.txt")
        val inputFile = File(inputURL.toURI())

        inputFile.bufferedReader().use { reader ->
            reader.forEachLine { line ->
                val (direction, turnAmount) = parseLine(line)

                val fullRotationsCount = turnAmount/100
                val newTurnAmount = turnAmount%100

                password += fullRotationsCount
                currentDial %= 100

                currentDial = rotateDial(currentDial, direction, newTurnAmount)
            }
        }
    }

    println("Password: $password")
    println("Execution time: ${time / 1_000_000_000.0} s")
}

private fun parseLine(line: String): Pair<Char, Int> {
    val direction = line.first()
    val amount = line.drop(1).toInt()
    return direction to amount
}

private fun rotateDial(currentDial:Int, direction: Char, turnAmount: Int): Int {
    val newDial: Int

    if (direction == 'L') {
        newDial = currentDial - turnAmount
    } else {
        newDial = currentDial + turnAmount
    }

    countRotations(newDial, currentDial)

    return newDial
}

private fun countRotations(newDial: Int, oldDial: Int) {
    if (newDial == 0) {
        password++
    } else if (newDial > 0 && oldDial < 0) {
        password++
    } else if (newDial < 0 && oldDial > 0) {
        password++
    } else {
        password += (newDial/100).absoluteValue
    }
}