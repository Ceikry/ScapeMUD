package buildActions

import Entity.Player
import actions.Action

class SetDescAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        if(player.currentRoom != null){
            val sb = StringBuilder()
            for(i in tokens.indices){
                if(i == 0) continue
                sb.append(tokens[i] + " ")
            }
            player.currentRoom!!.entryText = sb.toString()
        }
    }

    override fun printHelp() {
        GameConstants.addLine("Usage: sd type your description here")
        GameConstants.addLine("sd sets the description for a room.")
    }
}