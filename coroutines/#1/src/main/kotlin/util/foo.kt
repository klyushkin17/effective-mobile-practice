package org.example.util

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

suspend fun main() {

    println("Do first non-suspend block")
    first()
    println("Do second non-suspend block")
    second()
    println("Do third non-suspend block")
    third()
}

suspend fun first(){
    delay(100L)
    println("Do inner block of code in first function")
    innerFirst()
}
suspend fun second() { delay(200L) }
suspend fun third() { delay(300L) }


suspend fun innerFirst() {
    delay(1000L)
}
