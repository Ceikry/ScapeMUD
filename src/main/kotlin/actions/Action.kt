package actions

import Node.Player

abstract class Action{
    abstract fun handle(player: Player, tokens: Array<String>)
    abstract fun printHelp()
}