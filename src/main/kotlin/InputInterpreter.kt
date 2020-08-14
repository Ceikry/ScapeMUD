import Entity.Player

object InputInterpreter {
    fun handle(line: String, player: Player){
        val tokens = line.split(" ")
        val action = InputRepository.search(tokens[0])
        action ?: println("What?").also { return }
        action?.handle(player, tokens.toTypedArray())
    }
}