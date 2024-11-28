package org.example

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

suspend fun main() = coroutineScope {
   val flow = flow<Int> {
      emit(4)
      delay(5000L)
      emit(8)
      delay(1000L)
      emit(10)
   }

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

