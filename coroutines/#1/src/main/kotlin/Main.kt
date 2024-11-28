package org.example

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.util.throttleFirst
import org.example.util.throttleLatest

suspend fun main() = coroutineScope {
   val flow = flow {
      emit(1)
      delay(10L)
      emit(2)
      delay(10L)
      emit(3)
      delay(10L)
      emit(4)
      delay(700L)
      emit(5)
   }.throttleLatest(200uL)

   val job = CoroutineScope(Dispatchers.IO).launch {
      collectFlow(flow)
   }

   job.join()
}

suspend fun collectFlow(flow: Flow<Int>) {
   flow.collect {
      println(it)
   }
}

