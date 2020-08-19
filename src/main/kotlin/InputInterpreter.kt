import ActionPulse.ActionPulse
import Node.Player
import RoomSystem.ActionRepeater
import actions.Action
import actions.MovementAction

object InputInterpreter {
    fun handle(line: String, player: Player){
        val mtokens = line.split(";")
        val tokens = mtokens[0].split(" ")
        var actionIndex: Int = 0
        var repeat = false
        var repeatAmount = 0
        var action: Action?

        if(tokens[actionIndex] == ""){
            actionIndex++
            if(tokens.getOrNull(actionIndex) == null){
                return
            }
        }

        if(player.locked){
            player.addLine("You're already doing something!").also { return }
        }
        if(tokens[actionIndex].startsWith("#")){
            val num = tokens[actionIndex].replace("#","")
            val amount: Int? = num.toIntOrNull()
            amount ?: player.addLine("You can only have a number after a #, Example: #5").also { return }
            actionIndex++
            repeatAmount = amount ?: 0
            repeat = true
        }
        if(line.contains(";")){
            val newline = mtokens.subList(1,mtokens.size).joinToString(";")
            GameConstants.queue(object: ActionPulse(1){
                override fun pulse(): Boolean {
                    System.out.println("Handling $newline")
                    handle(newline,player)
                    return true
                }
            })
        }

        val command = tokens[actionIndex]
        System.out.println("command = [$command]")

        if(GameConstants.BUILD_MODE){
            action = BuildRepository.search(command)
        } else {
            action = InputRepository.search(command)
        }
        if(action == null && GameConstants.BUILD_MODE){
            action = InputRepository.search(command)
        }
        if(action == null && GameConstants.validDirs.any(){it == command}){
            action = MovementAction()
        }
        if(action == null){
            player.addLine ("What?")
            return
        }
        if(repeat){
            ActionRepeater(action).repeat(repeatAmount,player,tokens.toTypedArray().copyOfRange(actionIndex,tokens.size))
        } else {
            action.handle(player, tokens.toTypedArray().copyOfRange(actionIndex,tokens.size))
        }
    }
}