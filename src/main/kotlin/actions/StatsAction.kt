package actions

import Node.Player

class StatsAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        player.printStats()
    }

    override fun printHelp() {
        GameConstants.addLine("Usage: stats")
        GameConstants.addLine("stats prints out all of your equipment stats.")
    }
}