package RoomSystem

class RoomManager {
    companion object{
        val rooms = hashMapOf<Int,Room>()

        @JvmStatic
        fun getRoom(id: Int): Room?{
            return rooms.get(id)
        }

        fun getNextOpen(): Int{
            return rooms.keys.size + 1
        }
    }
}