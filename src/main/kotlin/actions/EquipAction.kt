package actions

import Entity.Player

class EquipAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size < 2) {
            GameConstants.textQueue += System.lineSeparator() + "Equip what?"
            return
        }
        player.equip(tokens[1])
    }
}