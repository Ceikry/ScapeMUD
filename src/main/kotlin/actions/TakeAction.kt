package actions

import Entity.Player

class TakeAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size < 2){
            GameConstants.textQueue += System.lineSeparator() + "Take what?"
            return
        }
        if(player.currentRoom?.hasItem(tokens[1]) == true){
            val item = player.currentRoom!!.getItem(tokens[1])
            GameConstants.textQueue += System.lineSeparator() + "You take ${item.definition?.name} and place it in your bag."
            player.currentRoom!!.items.remove(item)
            player.inventory.add(item)
            return
        }
        GameConstants.textQueue += System.lineSeparator() + "You see no such item."
    }
}