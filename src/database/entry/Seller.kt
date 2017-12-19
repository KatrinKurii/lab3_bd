package database.entry

class Seller(var sid: Int = 0, var sname: String = "") : Entry() {
    override fun toString(): String = "Seller $sname"
    override fun equals(other: Any?): Boolean {
        if (other?.javaClass != this.javaClass)
            return false
        return sname == (other as Seller).sname
    }
}