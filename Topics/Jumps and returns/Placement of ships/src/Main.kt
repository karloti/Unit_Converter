fun main() {
    var x = (1..5).toList()
    var y = (1..5).toList()
    readLine()!!
            .split(" ")
            .map(String::toInt)
            .windowed(2, 2) { (a, b) ->
                x = x.minus(a)
                y = y.minus(b)
            }
    println(x.joinToString(" "))
    println(y.joinToString(" "))

}