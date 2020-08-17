package actions

import Node.Player

class HelpAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size < 2){
            printCommands()
        } else {
            if(tokens[1] == "commands"){
                printCommands()
            } else {
                var action = InputRepository.search(tokens[1])
                if(action == null && GameConstants.BUILD_MODE){
                    action = BuildRepository.search(tokens[1])
                }
                if(action == null) GameConstants.addLine("Topic ${tokens[1]} not found.").also { return }
                GameConstants.addLine("---Help for ${tokens[1]} command---")
                action?.printHelp()
            }
        }
    }

    fun printCommands(){
        GameConstants.addLine("Standard Commands:")
        GameConstants.addLine("---")
        for(i in InputRepository.values()){
            GameConstants.addLine(i.command)
        }
        GameConstants.addLine("For more detailed info about a command, use help command_name")
        GameConstants.addLine("Movement: n,e,s,w,ne,nw,se,sw,up,down")
        if(GameConstants.BUILD_MODE) {
            GameConstants.addLine(System.lineSeparator())
            GameConstants.addLine("Building Commands:")
            GameConstants.addLine("---")
            for (i in BuildRepository.values()) {
                GameConstants.addLine(i.command)
            }

        }
    }

    override fun printHelp() {
        GameConstants.addLine("Usage: help topic")
        GameConstants.addLine("help allows you to learn more about commands, lore, etc.")
    }
}