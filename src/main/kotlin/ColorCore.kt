class ColorCore {
    companion object{
        const val RED = "31"
        const val GREEN = "32"
        const val BLUE = "34"
        const val YELLOW = "33"
        const val PURPLE ="35"
        const val CYAN = "36"
        const val RESET = "0"
        const val BOLD = "1"
        const val FAINT = "2"
        const val ITALIC = "3"
        const val UNDERLINE = "4"
        const val SLOW_BLINK = "5"
        const val RAPID_BLINK = "6"
        const val CROSSED_OUT = "9"
        const val FRAMED = "51"
        const val ENCIRCLED = "52"

       fun build(message: String, vararg effects: String): String{
           val sb = StringBuilder()
           sb.append("\u001B[")
           effects.map { sb.append(it).append(if(effects.indexOf(it) == effects.size - 1) "" else ";") }
           sb.append("m")
           sb.append(message)
           sb.append("\u001B[")
           sb.append(RESET)
           sb.append("m")
           return sb.toString()
       }
    }
}