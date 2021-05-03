fun main() = readLine()!!
    .map(Char::toInt)
    .run { chunked(size / 2, List<Int>::sum) }
    .run { if (first() == last()) "YES" else "NO" }
    .let(::println)