fun main() {
    val sp = readLine()!!
            .substringAfter("?")
            .split("&")
            .map { it.split("=").let { it[0] to it[1] } }
            .toMutableList()
    sp.indexOf(Pair("cookie", "")).let { if (it >= 0) sp[it] = Pair("cookie", "not found") }
    sp.forEach { println(it.first + " : " + it.second) }
    sp.find { it.first == "pass" }.let { if (it != null) println("password : " + it.second) } }
