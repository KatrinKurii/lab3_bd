package database.entry

class Buyer(var bid: Int = 0, var bname: String = "") : Entry() {
    override fun toString(): String = "Buyer $bname"
    override fun equals(other: Any?): Boolean {
        if (other?.javaClass != this.javaClass)
            return false
        return bname == (other as Buyer).bname
    }
}
