package actions

import Entity.Player

class InventoryAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        player.printInventory()
    }
}