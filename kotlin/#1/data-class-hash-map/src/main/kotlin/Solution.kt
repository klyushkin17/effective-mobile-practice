//Так как HashMap распределяет данные в памяти на основе hash-кода ключей, при
//изменении data-класса могут возникнуть проблемы.
//Если мы изменим какое-либо поле в конструкторе класса Key после заполнения Hash-мапа (а мы это сделать можем, так как поле filed2 является var),
//hash-код объекта(нашего ключа) так же будет пересчитан.
//Соответственно, когда мы попытаемся снова обратиться к элементу по измененному ключу, такого не найдется, и вернется null.
//Стоит отметить, что поля вне конструктора в подсчитывании hash-кода не участвуют

data class Key(
    val field1: Int,
    var field2: String
) {
    var field3: String? = null
}


fun main() {
    val key1 = Key(0, "stringValue")
    val key2 = Key(1, "stringValue")

    val hashMap: HashMap<Key, String> = hashMapOf(
        key1 to "mapValue",
        key2 to "mapValue",
    )

    println(hashMap[key1]) // Выведет "mapValue"

    key1.field2 = "changedStringValue" // Отредактируем поле filed2 объекта класса Key

    println(hashMap[key1]) // Выведет null, что печально
}