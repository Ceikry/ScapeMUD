package actions

import Node.Player

class GearAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        player.printEquipment()
    }

    override fun printHelp(player: Player) {
        player.addLine("Usage: gear")
        player.addLine("Gear shows you all of your equipment slots and what")
        player.addLine("you might or might not have in them.")
    }
}