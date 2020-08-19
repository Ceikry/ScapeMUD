package actions

import Node.Player

class HelpAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size < 2){
            printCommands(player)
        } else {
            if(tokens[1] == "commands"){
                printCommands(player)
            } else {
                var action = InputRepository.search(tokens[1])
                if(action == null && GameConstants.BUILD_MODE){
                    action = BuildRepository.search(tokens[1])
                }
                if(action == null) player.addLine("Topic ${tokens[1]} not found.").also { return }
                player.addLine("---Help for ${tokens[1]} command---")
                action?.printHelp(player)
            }
        }
    }

    fun printCommands(player: Player){
        player.addLine("Standard Commands:")
        player.addLine("---")
        for(i in InputRepository.values()){
            player.addLine(i.command)
        }
        player.addLine("For more detailed info about a command, use help command_name")
        player.addLine("Movement: n,e,s,w,ne,nw,se,sw,up,down")
        if(GameConstants.BUILD_MODE) {
            player.addLine(System.lineSeparator())
            player.addLine("Building Commands:")
            player.addLine("---")
            for (i in BuildRepository.values()) {
                player.addLine(i.command)
            }

        }
    }

    override fun printHelp(player: Player) {
        player.addLine("Usage: help topic")
        player.addLine("help allows you to learn more about commands, lore, etc.")
    }
}