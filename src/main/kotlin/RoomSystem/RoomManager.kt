package RoomSystem

class RoomManager {
    companion object{
        val rooms = hashMapOf<Int,Room>()

        @JvmStatic
        fun getRoom(id: Int): Room?{
            return rooms.get(id)
        }
    }
}