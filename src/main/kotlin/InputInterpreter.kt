import Entity.Player
import RoomSystem.ActionRepeater
import actions.Action
import actions.MovementAction

object InputInterpreter {
    fun handle(line: String, player: Player){
        val tokens = line.split(" ")
        var actionIndex: Int = 0
        var repeat = false
        var repeatAmount = 0
        var action: Action?
        if(player.locked){
            GameConstants.addLine("You're already doing something!").also { return }
        }
        if(tokens[0].startsWith("#")){
            val num = tokens[0].replace("#","")
            val amount: Int? = num.toIntOrNull()
            amount ?: GameConstants.addLine("You can only have a number after a #, Example: #5").also { return }
            actionIndex = 1
            repeatAmount = amount ?: 0
            repeat = true
        }

        if(GameConstants.BUILD_MODE){
            action = BuildRepository.search(tokens[actionIndex])
        } else {
            action = InputRepository.search(tokens[actionIndex])
        }
        if(action == null && GameConstants.BUILD_MODE){
            action = InputRepository.search(tokens[actionIndex])
        }
        if(action == null && GameConstants.validDirs.any(){it == tokens[actionIndex]}){
            action = MovementAction()
        }
        if(action == null){
            GameConstants.textQueue += System.lineSeparator() + "What?"
            GameConstants.gui.refresh()
            return
        }
        if(repeat){
            ActionRepeater(action).repeat(repeatAmount,player,tokens.toTypedArray().copyOfRange(actionIndex,tokens.size))
        } else {
            action.handle(player, tokens.toTypedArray())
        }
        GameConstants.gui.refresh()
    }
}