package buildActions

import Node.Player
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

    override fun printHelp(player: Player) {
        player.addLine("Usage: sd type your description here")
        player.addLine("sd sets the description for a room.")
    }
}