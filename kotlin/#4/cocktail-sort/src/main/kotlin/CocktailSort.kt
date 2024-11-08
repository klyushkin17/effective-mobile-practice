package org.example

fun List<Int?>.cocktailSort(){
    this as MutableList<Int?>

    var start = 0
    var end = this.size - 1
    var isSorted = false

    while (!isSorted) {
        isSorted = true

        for (index in start until end) {
            if (this[index] == null && this[index + 1] != null) {
                this[index] = this[index + 1].also { this[index + 1] = this[index] }
                isSorted = false
            } else if (this[index] != null && this[index + 1] != null && this[index]!! > this[index + 1]!!) {
                this[index] = this[index + 1].also { this[index + 1] = this[index] }
                isSorted = false
            }
        }

        if (isSorted) break

        isSorted = true
        end--

        for (index in end - 1 downTo start) {
            if (this[index] == null && this[index + 1] != null) {
                this[index] = this[index + 1].also { this[index + 1] = this[index] }
                isSorted = false
            } else if (this[index] != null && this[index + 1] != null && this[index]!! > this[index + 1]!!) {
                this[index] = this[index + 1].also { this[index + 1] = this[index] }
                isSorted = false
            }
        }
    }
}
