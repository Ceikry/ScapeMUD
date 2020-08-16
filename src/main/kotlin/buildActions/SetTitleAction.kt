package buildActions

import Entity.Player
import actions.Action


class SetTitleAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        if(player.currentRoom != null){
            val sb = StringBuilder()
            for(i in tokens.indices){
                if(i == 0) continue
                sb.append(tokens[i] + " ")
            }
            player.currentRoom!!.title = sb.toString()
        }
    }

    override fun printHelp() {
        GameConstants.addLine("Usage: st type the title of the room here.")
        GameConstants.addLine("st sets the title for the current room.")
    }
}