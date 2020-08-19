import Node.Player
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.File
import java.io.FileReader

class LoginHandler(val player: Player){
    var stage: Stage = Stage.ENTER_UN
    var username: String = ""
    var password: String = ""
    var validInitial = "abcdefghijklmnopqrstuvwxyz"
    var validChars = validInitial + "0123456789"
    var parser = JSONParser()
    var reader: FileReader? = null

    fun checkExist(): Boolean{
        return File(GameConstants.PLAYER_SAVE_PATH + "$username.json").exists()
    }

    fun sendEnterPass(){
        player.addLine(ColorCore.build("Password: ",ColorCore.RED))
    }

    fun sendConfirmPass(){
        player.addLine(ColorCore.build("Confirm Password:",ColorCore.RED))
    }

    fun checkValid(line: String): Boolean{
        var allValid = true
        for(i in line){
            if(!validChars.contains(i)) {
                allValid = false
                break
            }
        }
        if(line.contains(" ") || !validInitial.contains(line[0]) || !allValid || line.length > 20){
            player.addLine("Usernames can only contain letters and numbers and can be no longer than 20 characters.")
            return false
        }
        return true
    }

    fun handle(line: String){
        val toParse = line.toLowerCase()
        when(stage){

            Stage.ENTER_UN -> {
                if(checkValid(toParse)){
                    username = toParse
                    if(!checkExist()){
                        player.addLine("$username does not exist. Would you like to create it? (Y/N)")
                        stage = Stage.ASK_MAKE_NEW
                        return
                    } else {
                        sendEnterPass()
                        stage = Stage.ENTER_PASS
                    }
                }
            }

            Stage.ENTER_PASS -> {
                val reader = FileReader(GameConstants.PLAYER_SAVE_PATH + "$username.json")
                val data = parser.parse(reader) as JSONObject
                val password = data["password"].toString()
                this.password = line
                if(password != line){
                    player.addLine("Invalid password!")
                    player.session?.closeOnFlush()
                    return
                }
                if(GameConstants.playerName.contains(username)){
                    player.addLine("This player is already logged in.")
                    player.session?.closeOnFlush()
                }
                parseSave(data)
                player.init()
                stage = Stage.DONE
                return
            }

            Stage.ASK_MAKE_NEW -> {
                if(line.toLowerCase().contains("y")){
                    sendEnterPass()
                    stage = Stage.MAKE_NEW_PASS
                }
            }

            Stage.MAKE_NEW_PASS -> {
                password = line
                sendConfirmPass()
                stage = Stage.CONFIRM_NEW_PASS
            }

            Stage.CONFIRM_NEW_PASS -> {
                if(password != line){
                    player.addLine("Sorry, your passwords don't match. Please try again.")
                    sendEnterPass()
                    stage = Stage.MAKE_NEW_PASS
                } else {
                    dumpNewPlayer()
                    player.init()
                    player.loggedIn = true
                }
            }



        }
    }


    fun parseSave(data: JSONObject){
        player.parse(data)
    }

    fun dumpNewPlayer(){
        player.croomId = 1
        player.save()
    }

    enum class Stage{
        ENTER_UN,
        ENTER_PASS,
        ASK_MAKE_NEW,
        MAKE_NEW_PASS,
        CONFIRM_NEW_PASS,
        DONE
    }
}