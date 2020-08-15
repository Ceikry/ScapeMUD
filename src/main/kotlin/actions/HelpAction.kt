package actions

import Entity.Player

class HelpAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size < 2){
            printCommands()
        } else {
            if(tokens[1] == "commands"){
                printCommands()
            }
        }
    }

    fun printCommands(){
        GameConstants.addLine("Standard Commands:")
        GameConstants.addLine("---")
        for(i in InputRepository.values()){
            GameConstants.addLine(i.command)
        }
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
}