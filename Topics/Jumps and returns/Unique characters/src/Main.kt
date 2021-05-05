fun main() = readLine()!!
    .groupBy { it }
    .filterValues { it.size == 1 }
    .count()
    .let(::println)