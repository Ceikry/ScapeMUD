package actions

import Entity.Player

class LookAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        player.currentRoom?.look()
    }
}