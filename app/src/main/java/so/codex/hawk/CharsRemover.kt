package so.codex.hawk

fun removeUselessChars(str: String): String {
    var result = ""
    val words = str.split(" ")
    words.forEach {
        result += it.asSequence()
            .filter {c ->
                c.isLetter()
            }.joinToString("", "", "") + " "
    }

    return result
}