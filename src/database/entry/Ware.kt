package database.entry

class Ware(var wid: Int = 0, var wname: String = "", var number: Int = 0, var price: Double = 0.0,
           var description: String = "", var availability: Boolean = true) : Entry() {
    override fun toString(): String = "Ware $wname for $price$. (Info: $description)"
    override fun equals(other: Any?): Boolean {
        if (other?.javaClass != this.javaClass)
            return false
        return wname == (other as Ware).wname && number == other.number && price == other.price &&
                description == other.description && availability == other.availability
    }
}