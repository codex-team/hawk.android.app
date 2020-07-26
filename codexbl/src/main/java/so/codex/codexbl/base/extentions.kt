package so.codex.codexbl.base

fun Logger.info(message: String) {
    info(this::class.java.simpleName, message)
}