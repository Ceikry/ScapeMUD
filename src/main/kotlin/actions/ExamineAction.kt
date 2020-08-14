package actions

import Entity.Player

class ExamineAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size < 2)
            println("Examine what?").also { return }

        if(player.hasItem(tokens[1])) {
            println("You take a closer look at ${player.cachedItem?.definition?.name}")
            println("It's " + player.cachedItem?.definition?.desc)
            return
        }

        if(player.currentRoom?.hasItem(tokens[1]) == true){
            println("You take a closer look at ${player.currentRoom!!.cachedItem?.definition?.name}")
            println("It's " + player.currentRoom!!.cachedItem?.definition?.desc)
            return
        }
    }
}