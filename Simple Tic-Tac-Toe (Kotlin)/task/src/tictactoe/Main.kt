package tictactoe

import kotlin.math.abs

fun main() {
    val mtGame = Array(3) { Array(3) {' '}}

    val dashes = "---------"
    println(dashes)
    var c: Char
    for (i in  0 .. 2) {
        print("| ")
        for (j in 0 .. 2) {
            c = ' '
            mtGame[i][j] = c
            print("$c ")
        }
        println("|")
    }
    println(dashes)

    var x: Int
    var y: Int
    var continueGame = true
    var nChar = 'X'
    var gameMessage: String = ""
    while (continueGame) {
        val usrCoordenates = readln()
        val (ok, msg) = isValid(usrCoordenates)
        if (ok) {
            val tmpV = usrCoordenates.split(" ")
            x = tmpV[0].toInt() - 1
            y = tmpV[1].toInt() - 1
            if (mtGame[x][y] == ' ') {
                mtGame[x][y] = nChar
                val (endGame, msgSolution)  = solution(mtGame)
                if (endGame) {
                    continueGame = false
                    gameMessage = msgSolution
                }
                printBoard(mtGame)
                nChar = if (nChar == 'X') 'O' else 'X'
            } else {
                println("This cell is occupied! Choose another one!")
            }
        } else {
            println(msg)
        }
    }
    println(gameMessage)

}

const val WIN_ACCOUNT = 3


fun printBoard(mt: Array<Array<Char>>) {
    val dashes = "---------"
    println(dashes)
    var c: Char
    for (i in mt.indices) {
        print("| ")
        for (j in mt[i].indices) {
            c = mt[i][j]
            print("$c ")
        }
        println("|")
    }
    println(dashes)
}

fun isValid(usrInput: String): Pair<Boolean, String> {
    val x: Int?
    val y: Int?
    val msgNumbers = "You should enter numbers!"
    val msgOutOfBound = "Coordinates should be from 1 to 3!"

    val v = usrInput.split(" ")
    if (v.size != 2)
        return Pair(false, msgNumbers)
    x = v[0].toIntOrNull()
    if (x == null) {
        return Pair(false, msgNumbers)
    }
    y = v[1].toIntOrNull()
    if (y == null) {
        return Pair(false, msgNumbers)
    }
    if (x !in 1..3) {
        return Pair(false, msgOutOfBound)
    }
    if (y !in 1 .. 3) {
        return Pair(false, msgOutOfBound)
    }
    return Pair(true, "Ok")
}

fun solution(mt: Array<Array<Char>>): Pair<Boolean, String> {
    return when {
        checkWin(mt, 'X') && checkWin(mt, 'O') -> Pair(true, "Impossible")
        countElements(mt) >= 2 -> Pair(true, "Impossible")
        checkWin(mt, 'X') -> Pair(true, "X wins")
        checkWin(mt, 'O') -> Pair(true, "O wins")
        isFull(mt) -> Pair(true, "Draw")
        else -> Pair(false, "Game not finished")
    }
}

fun isFull(mt: Array<Array<Char>>): Boolean {
    var c = 0
    val op = listOf('X', 'O')
    for (i in mt.indices) {
        for (j in 0 until  mt[i].size) {
            if (mt[i][j] in op)
                c += 1
        }
    }
    return (WIN_ACCOUNT*WIN_ACCOUNT) == c
}

fun countElements(mt: Array<Array<Char>>): Int {
    var cx = 0
    var co = 0

    for (inner in mt) {
        for (v in inner) {
            when (v) {
                'X' -> cx++
                'O' -> co++
            }
        }
    }
    return abs(co-cx)
}

fun checkWin(mt: Array<Array<Char>>, target: Char): Boolean {
    // eh possivel revolver isso sem repetir tanto codigo ?
    var countWin = 0
    // horizontal
    for (i in mt.indices) {
        for (j in 0 until mt[i].size) {
            if (mt[i][j] == target) {
                countWin += 1
            }
        }
        if (countWin == WIN_ACCOUNT)
            return true
        countWin = 0
    }
    // vertical
    countWin = 0
    for (i in mt.indices) {
        for (j in 0 until mt[i].size) {
            if (mt[j][i] == target) {
                countWin += 1
            }
        }
        if (countWin == WIN_ACCOUNT)
            return true
        countWin = 0
    }
    // diagonal
    countWin = 0
    for (i in mt.indices) {
        if (mt[i][i] == target)
            countWin += 1
    }
    if (countWin == WIN_ACCOUNT)
        return true
    countWin = 0
    // (2,0), (1,1) (0, 2)
    var ac = 0
    for (i in mt.size-1 downTo 0) {
        if (mt[i][ac] == target)
            countWin += 1
        ac += 1
    }
    return countWin == WIN_ACCOUNT
}