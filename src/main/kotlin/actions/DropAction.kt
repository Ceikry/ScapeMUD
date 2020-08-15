package actions

import Entity.Player

class DropAction : Action(){
    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size < 2) {
            GameConstants.textQueue += System.lineSeparator() + "Drop what?"
            return
        }
        if(player.hasItem(tokens[1])){
            val item = player.cachedItem
            item ?: return
            player.inventory.remove(item)
            player.currentRoom?.items?.add(item)
            GameConstants.textQueue += System.lineSeparator() + "You drop ${item.definition?.name} on the floor."
        }
    }
}