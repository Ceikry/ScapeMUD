import Node.NPC.DropTables
import RoomSystem.RoomManager
import org.apache.mina.core.session.IdleStatus
import org.apache.mina.filter.codec.ProtocolCodecFilter
import org.apache.mina.filter.codec.textline.TextLineCodecFactory
import org.apache.mina.transport.socket.nio.NioSocketAcceptor
import java.net.InetSocketAddress
import java.nio.charset.Charset


val acceptor = NioSocketAcceptor()

fun main() {
    DefinitionParser().parseItems()
    DefinitionParser().parseObjects()
    DefinitionParser().parseNPCs()
    DropTables.map()
    RoomManager.init("data/rooms.json")
    System.out.println("\u001B[32mTest\u001B[0m")

    acceptor.filterChain.addLast("codec",ProtocolCodecFilter(TextLineCodecFactory( Charset.forName("UTF-8"))))
    acceptor.handler = ConnectionHandler()
    acceptor.sessionConfig.readBufferSize = 2048
    acceptor.sessionConfig.setIdleTime(IdleStatus.BOTH_IDLE, 10)
    acceptor.bind(InetSocketAddress(4444))
}

fun gameloop(){

}