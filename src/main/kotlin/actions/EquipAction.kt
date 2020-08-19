package actions

import Node.Player

class EquipAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size < 2) {
            player.addLine("Equip what?")
            return
        }
        player.equip(tokens[1])
    }

    override fun printHelp(player: Player) {
        player.addLine("Usage: equip item_name")
        player.addLine("Equip allows you to put items from you inventory onto")
        player.addLine("your body. Some equipment has level requirements.")
    }
}