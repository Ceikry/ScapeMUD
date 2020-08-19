import ActionPulse.ActionPulse
import Node.Player
import org.apache.mina.core.session.IoSession

class GameConstants {
    companion object {
        var textQueue = ArrayList<String>()
        var gui: ClientDummy = ClientDummy(null)
        var BUILD_MODE = true
        val validDirs = arrayOf("n","e","s","w","ne","nw","se","sw","up","down")
        val actionRepeater = PulseRunner()
        val players = hashMapOf<IoSession,Player>()
        val playerName = hashMapOf<String,Player>()
        const val PLAYER_SAVE_PATH = "data/players/"


        @JvmStatic
        fun addLine(line: String){
            textQueue.add(line)
        }

        fun queue(pulse: ActionPulse){
            actionRepeater.addToQueue(pulse)
        }

        fun registerPlayer(player: Player){
            playerName.put(player.loginHandler.username,player)
        }

        fun registerSession(player: Player){
            players.put(player.session!!,player)
        }

        fun sendWelcome(player: Player){
            player.addLine(ColorCore.build("Welcome to ScapeMUD! My super lazy attempt at making a",ColorCore.CYAN))
            player.addLine(ColorCore.build("Runescape-inspired MUD.",ColorCore.CYAN))
            player.addLine(ColorCore.build("It's not super polished because, as I said, it's very lazy :^)",ColorCore.CYAN))
            player.addLine("--------------------------------------------------------------")
            player.addLine(ColorCore.build("Please enter your username: ",ColorCore.RED))
        }
    }
}