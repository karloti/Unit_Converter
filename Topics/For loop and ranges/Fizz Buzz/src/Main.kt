fun main() = readLine()!!
    .split(" ")
    .map(String::toInt)
    .let { it[0]..it[1] }
    .map {
        when {
            it % 15 == 0 -> "FizzBuzz"
            it % 5 == 0 -> "Buzz"
            it % 3 == 0 -> "Fizz"
            else -> it
        }
    }
    .joinToString("\n")
    .let(::println)
