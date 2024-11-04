package com.example.kitlin_problem_3.extantion_functoins

fun List<Any>.findFirstIntInList(): Int {
    this.forEach { listElement ->
        if (listElement is Int) return listElement
    }
    return -1
}
