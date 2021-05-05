package converter

interface IMetrics { val names: Array<out String> }

enum class Length(override vararg val names: String) : IMetrics {
    METER("meter", "meters", "m"),
    KILOMETER("kilometer", "kilometers", "km"),
    CENTIMETER("centimeter", "centimeters", "cm"),
    MILLIMETER("millimeter", "millimeters", "mm"),
    MILE("mile", "miles", "mi"),
    YARD("yard", "yards", "yd"),
    FEE("foot", "feet", "ft"),
    INCH("inch", "inches", "in");
    companion object { val HUB = METER }
}

enum class Weight(override vararg val names: String) : IMetrics {
    GRAM("gram", "grams", "g"),
    KILOGRAM("kilogram", "kilograms", "kg"),
    MILLIGRAM("milligram", "milligrams", "mg"),
    POUND("pound", "pounds", "lb"),
    OUNCE("ounce", "ounces", "oz");
    companion object { val HUB = GRAM }
}

enum class Temperature(override vararg val names: String) : IMetrics {
    CELSIUS("degree Celsius", "degrees Celsius", "celsius", "dc", "c"),
    FAHRENHEIT("degree Fahrenheit", "degrees Fahrenheit", "fahrenheit", "df", "f"),
    KELVINS("kelvin", "kelvins", "k");
    companion object { val HUB = CELSIUS }
}

fun Double.convert(pairedMetrics: Pair<IMetrics?, IMetrics?>): Double? = when (pairedMetrics) {
    Length.METER to Length.HUB -> this
    Length.KILOMETER to Length.HUB -> this * 1000.0
    Length.CENTIMETER to Length.HUB -> this * 0.01
    Length.MILLIMETER to Length.HUB -> this * 0.001
    Length.MILE to Length.HUB -> this * 1609.35
    Length.YARD to Length.HUB -> this * 0.9144
    Length.FEE to Length.HUB -> this * 0.3048
    Length.INCH to Length.HUB -> this * 0.0254
    Weight.GRAM to Weight.HUB -> this
    Weight.KILOGRAM to Weight.HUB -> this * 1000.0
    Weight.MILLIGRAM to Weight.HUB -> this * 0.001
    Weight.POUND to Weight.HUB -> this * 453.592
    Weight.OUNCE to Weight.HUB -> this * 28.3495
    Temperature.CELSIUS to Temperature.HUB -> this
    Temperature.KELVINS to Temperature.HUB -> this - 273.15
    Temperature.FAHRENHEIT to Temperature.HUB -> (this - 32) * 5 / 9
    Length.HUB to Length.METER -> this
    Length.HUB to Length.KILOMETER -> this / 1000.0
    Length.HUB to Length.CENTIMETER -> this / 0.01
    Length.HUB to Length.MILLIMETER -> this / 0.001
    Length.HUB to Length.MILE -> this / 1609.35
    Length.HUB to Length.YARD -> this / 0.9144
    Length.HUB to Length.FEE -> this / 0.3048
    Length.HUB to Length.INCH -> this / 0.0254
    Weight.HUB to Weight.GRAM -> this
    Weight.HUB to Weight.KILOGRAM -> this / 1000.0
    Weight.HUB to Weight.MILLIGRAM -> this / 0.001
    Weight.HUB to Weight.POUND -> this / 453.592
    Weight.HUB to Weight.OUNCE -> this / 28.3495
    Temperature.HUB to Temperature.CELSIUS -> this
    Temperature.HUB to Temperature.FAHRENHEIT -> this * 9 / 5 + 32
    Temperature.HUB to Temperature.KELVINS -> this + 273.15
    else -> null
}

fun String.koko(metric: IMetrics) = metric.names.any { it.equals(this, true) }

fun String.getEnum(): IMetrics? = Length.values().firstOrNull(::koko)
    ?: Weight.values().firstOrNull(::koko)
    ?: Temperature.values().firstOrNull(::koko)

fun IMetrics.getHUB(): IMetrics? = when (this) {
    is Length -> Length.HUB
    is Weight -> Weight.HUB
    is Temperature -> Temperature.HUB
    else -> null
}

fun main() {
    while (true) {
        print("Enter what you want to convert (or exit): ")
        val matchResult = Regex("exit|(-?\\d+\\.?\\d*)\\s(.+)\\s(?:to|in|convertTo)\\s(.+)")
            .find(readLine()!!) ?: continue
        if (matchResult.value == "exit") return
        val number = matchResult.groupValues[1].toDoubleOrNull() ?: continue
        val unit1 = matchResult.groupValues[2].getEnum()
        val unit2 = matchResult.groupValues[3].getEnum()
        val result = if (unit1?.getHUB() != unit2?.getHUB()) null else
            number.convert(unit1 to unit1?.getHUB())?.convert(unit2?.getHUB() to unit2)
        val unitStr1 = unit1?.names?.get(if (number == 1.0 && result != null) 0 else 1) ?: "???"
        val unitStr2 = unit2?.names?.get(if (result == 1.0) 0 else 1) ?: "???"
        when {
            result == null -> "Conversion from $unitStr1 to $unitStr2 is impossible"
            number < 0 && (unit1 is Length) -> "Length shouldn't be negative"
            number < 0 && (unit1 is Weight) -> "Weight shouldn't be negative"
            else -> "%s %s is %s %s".format(number, unitStr1, result, unitStr2)
        }.let(::println)
    }
}