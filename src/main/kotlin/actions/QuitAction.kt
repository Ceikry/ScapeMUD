package actions

import Node.Player

class QuitAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        if(player.locked){
            player.addLine("You can't quit while you're doing something.").also { return }
        }
        player.addLine("Saved!")
        player.session?.closeOnFlush()
        System.out.println("${player.name} successfully quit.")
    }

    override fun printHelp(player: Player) {
        player.addLine("Usage: quit")
        player.addLine("Quit ends your play session and saves your character.")
    }
}