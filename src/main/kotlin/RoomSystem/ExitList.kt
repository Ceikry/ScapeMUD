package RoomSystem

class ExitList {
    var south: Int = 0
    var east: Int = 0
    var west: Int = 0
    var north: Int = 0
    var north_west: Int = 0
    var north_east: Int = 0
    var south_east: Int = 0
    var south_west: Int = 0
    var up: Int = 0
    var down: Int = 0

    fun hasNorth(): Boolean {
        return north != 0
    }

    fun hasSouth(): Boolean {
        return south != 0
    }

    fun hasEast(): Boolean {
        return east != 0
    }

    fun hasWest(): Boolean {
        return west != 0
    }

    fun hasNW(): Boolean{
        return north_west != 0
    }

    fun hasNE(): Boolean {
        return north_east != 0
    }

    fun hasSE(): Boolean {
        return south_east != 0
    }

    fun hasSW(): Boolean {
        return south_west != 0
    }

    fun hasUp(): Boolean {
        return up != 0
    }

    fun hasDown(): Boolean {
        return down != 0
    }
}