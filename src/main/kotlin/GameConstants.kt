import Entity.Player
import gui.Login
import gui.MainWindow

class GameConstants {
    companion object {
        var textQueue = ""
        val gui = MainWindow()
        val login = Login()
        var loadedPlayer: Player? = null

        @JvmStatic
        fun addLine(line: String){
            textQueue += System.lineSeparator() + line
        }

        fun sendWelcome(){
            addLine("Welcome to ScapeSUD! My super lazy attempt at making a")
            addLine("Runescape-inspired MUD but singleplayer (SUD).")
            addLine("It's not super polished because, as I said, it's very lazy :^)")
        }
    }
}