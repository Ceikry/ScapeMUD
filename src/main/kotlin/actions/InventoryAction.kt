package actions

import Entity.Player

class InventoryAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        player.printInventory()
    }

    override fun printHelp() {
        GameConstants.addLine("Usage: inventory")
        GameConstants.addLine("Inventory lists out the contents of your inventory")
        GameConstants.addLine("and how much space you have left.")
    }
}