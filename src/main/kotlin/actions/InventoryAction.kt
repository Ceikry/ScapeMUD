package actions

import Node.Player

class InventoryAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        player.printInventory()
    }

    override fun printHelp(player: Player) {
        player.addLine("Usage: inventory")
        player.addLine("Inventory lists out the contents of your inventory")
        player.addLine("and how much space you have left.")
    }
}