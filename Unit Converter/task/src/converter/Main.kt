package converter

interface IMetrics {
    val names: Array<out String>
}

enum class Length(override vararg val names: String) : IMetrics {
    METER("m", "meter", "meters"),
    KILOMETER("km", "kilometer", "kilometers"),
    CENTIMETER("cm", "centimeter", "centimeters"),
    MILLIMETER("mm", "millimeter", "millimeters"),
    MILE("mi", "mile", "miles"),
    YARD("yd", "yard", "yards"),
    FEE("ft", "foot", "feet"),
    INCH("in", "inch", "inches"),
    ;

    companion object {
        val HUB = METER
    }
}

enum class Weight(override vararg val names: String) : IMetrics {
    GRAM("g", "gram", "grams"),
    KILOGRAM("kg", "kilogram", "kilograms"),
    MILLIGRAM("mg", "milligram", "milligrams"),
    POUND("lb", "pound", "pounds"),
    OUNCE("oz", "ounce", "ounces"),
    ;

    companion object {
        val HUB = GRAM
    }
}

fun convert(pairedMetrics: Pair<IMetrics?, IMetrics?>): Double? = when (pairedMetrics) {
    Length.METER to Length.HUB -> 1.0
    Length.KILOMETER to Length.HUB -> 1000.0
    Length.CENTIMETER to Length.HUB -> 0.01
    Length.MILLIMETER to Length.HUB -> 0.001
    Length.MILE to Length.HUB -> 1609.35
    Length.YARD to Length.HUB -> 0.9144
    Length.FEE to Length.HUB -> 0.3048
    Length.INCH to Length.HUB -> 0.0254
    Weight.GRAM to Weight.HUB -> 1.0
    Weight.KILOGRAM to Weight.HUB -> 1000.0
    Weight.MILLIGRAM to Weight.HUB -> 0.001
    Weight.POUND to Weight.HUB -> 453.592
    Weight.OUNCE to Weight.HUB -> 28.3495
    else -> null
}

fun IMetrics.getHUB(): IMetrics? = when (this) {
    is Length -> Length.HUB
    is Weight -> Weight.HUB
    else -> null
}

fun String.getEnum(): IMetrics? =
    Length.values().firstOrNull { it.names.contains(toLowerCase()) }
        ?: Weight.values().firstOrNull { it.names.contains(toLowerCase()) }

fun main() {
    while (true) {
        print("Enter what you want to convert (or exit): ")
        val strings = readLine()!!.split(" ")

        if (strings.first() == "exit") return

        val number = strings.first().toDoubleOrNull() ?: continue
        val measure1: IMetrics? = strings.getOrNull(1)?.getEnum()
        val measure2: IMetrics? = strings.getOrNull(3)?.getEnum()

        val result =
            if (measure1?.getHUB() == measure2?.getHUB())
                convert(measure2 to measure2?.getHUB())
                    ?.let { convert(measure1 to measure1?.getHUB())?.div(it) }
                    ?.times(number)
            else null

        val measureStr1 = measure1?.names?.get(if (number == 1.0 && result != null) 1 else 2) ?: "???"
        val measureStr2 = measure2?.names?.get(if (result == 1.0) 1 else 2) ?: "???"

        println(if (result == null)
            "Conversion from $measureStr1 to $measureStr2 is impossible" else
            "%s %s is %s %s".format(number, measureStr1, result, measureStr2))
    }
}