fun main() {
    val vowels = "AEIOU"
    val letters = mutableMapOf<Char, Int>()
    for (k in 'A'..'Z') letters[k] = if (k in vowels) k - 'A' + 1 else 0
    val ch = readLine()!![0].toUpperCase()
    println(letters[ch])
}
