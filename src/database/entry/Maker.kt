package database.entry

class Maker(var mid: Int = 0, var mname: String = "", var country: String = "") : Entry() {
    override fun toString(): String = "Maker $mname from $country"
    override fun equals(other: Any?): Boolean {
        if (other?.javaClass != this.javaClass)
            return false
        return mname == (other as Maker).mname && country == other.country
    }
}