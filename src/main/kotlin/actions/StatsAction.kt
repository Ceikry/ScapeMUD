package actions

import Entity.Player

class StatsAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        player.printStats()
    }
}