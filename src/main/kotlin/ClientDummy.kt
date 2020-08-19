import org.apache.mina.core.session.IoSession

class ClientDummy(var session: IoSession?){

    fun refresh(){
        if(GameConstants.textQueue.isNotEmpty()) {
            for (i in GameConstants.textQueue) {
                session?.write(i)
            }
            session?.write("----------------------")
            GameConstants.textQueue.clear()
        }
    }
}