import Node.Player
import org.apache.mina.core.service.IoHandlerAdapter
import org.apache.mina.core.session.IdleStatus
import org.apache.mina.core.session.IoSession

class ConnectionHandler : IoHandlerAdapter(){
    override fun exceptionCaught(session: IoSession?, cause: Throwable?) {
        cause?.printStackTrace()
    }

    override fun messageReceived(session: IoSession?, message: Any?) {
        val input = message.toString()
        val player = GameConstants.players.get(session) ?: return
        if(player.loggedIn) {
            player.commandQueue.add(input)
        } else if(!player.loggedIn) {
            player.loginHandler.handle(input)
        }
    }

    override fun sessionIdle(session: IoSession?, status: IdleStatus?) {
        if(session?.getIdleCount(status)!! > 100){
            session.closeOnFlush()
        }
    }

    override fun sessionOpened(session: IoSession?) {
        val player = Player()
        player.session = session
        GameConstants.registerSession(player)
        GameConstants.sendWelcome(player)
        super.sessionOpened(session)
    }

    override fun sessionClosed(session: IoSession?) {
        val player = GameConstants.players.get(session)
        if(player?.loggedIn!!) {
            player.save()
        }
        GameConstants.players.remove(session)
        GameConstants.playerName.remove(player?.name)
        super.sessionClosed(session)
    }
}