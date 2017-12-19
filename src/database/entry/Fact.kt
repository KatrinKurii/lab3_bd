package database.entry

import java.sql.Date


class Fact(var fid: Int=0, var maker: Maker = Maker(), var ware: Ware = Ware(),
           var seller: Seller = Seller(), var buyer: Buyer = Buyer(), var amount: Int = 0,
           var date: Date = Date(System.currentTimeMillis())) : Entry() {
    fun toArray(): Array<String> = arrayOf(ware.wname, maker.mname, maker.country, ware.description,
            seller.sname, buyer.bname,  amount.toString(), date.toString())

    override fun toString(): String = "Fact from $date: seller ${seller.sname}, buyer ${buyer.bname}, amount $amount"
}
