package actions

import Node.Player

class StatsAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        player.printStats()
    }

    override fun printHelp(player: Player) {
        player.addLine("Usage: stats")
        player.addLine("stats prints out all of your equipment stats.")
    }
}