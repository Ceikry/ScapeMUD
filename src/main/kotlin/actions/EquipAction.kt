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

    override fun printHelp() {
        GameConstants.addLine("Usage: equip item_name")
        GameConstants.addLine("Equip allows you to put items from you inventory onto")
        GameConstants.addLine("your body. Some equipment has level requirements.")
    }
}