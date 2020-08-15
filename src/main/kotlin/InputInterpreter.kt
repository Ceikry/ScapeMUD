import Entity.Player

object InputInterpreter {
    fun handle(line: String, player: Player){
        val tokens = line.split(" ")
        val action = InputRepository.search(tokens[0])
        if(action == null){
            GameConstants.textQueue += System.lineSeparator() + "What?"
            GameConstants.gui.refresh()
            return
        }
        action.handle(player, tokens.toTypedArray())
        GameConstants.gui.refresh()
    }
}