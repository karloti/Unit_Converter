class Car(val make: String, val year: Int) {
    var speed: Int = 0
    fun accelerate() { speed += 5 }
    fun decelerate() { speed = (speed - 5).coerceAtLeast(0) }
}