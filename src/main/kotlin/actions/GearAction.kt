package actions

import Node.Player

class GearAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        player.printEquipment()
    }

    override fun printHelp() {
        GameConstants.addLine("Usage: gear")
        GameConstants.addLine("Gear shows you all of your equipment slots and what")
        GameConstants.addLine("you might or might not have in them.")
    }
}