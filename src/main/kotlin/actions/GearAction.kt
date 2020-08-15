package actions

import Entity.Player

class GearAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        player.printEquipment()
    }
}