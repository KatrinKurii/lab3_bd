package gui

import database.entry.*
import java.awt.Dimension
import database.DatabaseProvider as db
import javax.swing.*
import javax.swing.BoxLayout.X_AXIS
import javax.swing.BoxLayout.Y_AXIS

class Window: JFrame("Lab DB") {
    private var table: JTable
    private val scroll: JScrollPane
    init {
        isVisible = true
        defaultCloseOperation = EXIT_ON_CLOSE
        contentPane.layout = BoxLayout(contentPane,Y_AXIS)
        contentPane.add(createToolBar())
        table = showTable()
        scroll = JScrollPane(table)
        scroll.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        scroll.horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        scroll.preferredSize = Dimension(1000,400)
        contentPane.add(scroll)
        pack()
    }

    private fun showTable(provider: List<Fact> = db.read("Fact")): JTable = JTable(provider.map { it.toArray() }.toTypedArray(), arrayOf("Ware", "Maker", "Country",  "Description", "Seller", "Buyer",  "Number", "Date"))

    private fun createToolBar(): JPanel {
        val tools = JPanel()
        tools.layout = BoxLayout(tools, X_AXIS)
        val add = JButton("Add")
        add.setMnemonic('A')
        add.addActionListener {
            val buyers = db.read<Buyer>("Buyer").toTypedArray()
            val sellers = db.read<Seller>("Seller").toTypedArray()
            val makers = db.read<Maker>("Maker").toTypedArray()
            val wares = db.read<Ware>("Ware").toTypedArray()
            val buyer = JOptionPane.showInputDialog(this, "Select buyer", "Add fact of sale",
                    JOptionPane.QUESTION_MESSAGE,null,buyers,buyers[0]) as Buyer? ?: return@addActionListener
            val seller = JOptionPane.showInputDialog(this, "Select seller", "Add fact of sale",
                    JOptionPane.QUESTION_MESSAGE, null, sellers, sellers[0]) as Seller? ?: return@addActionListener
            val maker = JOptionPane.showInputDialog(this, "Select maker", "Add fact of sale",
                    JOptionPane.QUESTION_MESSAGE, null, makers, makers[0]) as Maker? ?: return@addActionListener
            val ware = JOptionPane.showInputDialog(this, "Select ware", "Add fact of sale",
                    JOptionPane.QUESTION_MESSAGE, null, wares, wares[0]) as Ware? ?: return@addActionListener
            val amount = JOptionPane.showInputDialog(this, "Set amount", "Add fact of sale",
                    JOptionPane.QUESTION_MESSAGE)?.toIntOrNull() ?: return@addActionListener
            db.exec(Fact(0,maker,ware,seller,buyer,amount))
            table = showTable()
            scroll.setViewportView(table)
        }
        tools.add(add)
        val search = JButton("Search")
        search.setMnemonic('S')
        search.addActionListener {
            val wares = db.read<Ware>("Ware").toTypedArray()
            val ware = JOptionPane.showInputDialog(this, "Select ware", "Search fact of sale",
                    JOptionPane.QUESTION_MESSAGE, null, wares, wares[0]) as Ware?
            table = if (ware == null) {
                showTable()
            } else {
                val result = db.read<Fact>("Fact").filter { it.ware == ware }
                showTable(result)
            }
            scroll.setViewportView(table)
        }
        tools.add(search)
        val delete = JButton("Delete")
        delete.setMnemonic('D')
        delete.addActionListener {
            val facts = db.read<Fact>("Fact").toTypedArray()
            val fact = JOptionPane.showInputDialog(this, "Select fact of sale", "Delete fact of sale",
                    JOptionPane.QUESTION_MESSAGE, null, facts, facts[0]) as Fact? ?: return@addActionListener
            db.exec(fact, db.DELETE)
            table = showTable()
            scroll.setViewportView(table)
        }
        tools.add(delete)
        val update = JButton("Update")
        update.setMnemonic('U')
        update.addActionListener {
            val facts = db.read<Fact>("Fact").toTypedArray()
            val buyers = db.read<Buyer>("Buyer").toTypedArray()
            val sellers = db.read<Seller>("Seller").toTypedArray()
            val makers = db.read<Maker>("Maker").toTypedArray()
            val wares = db.read<Ware>("Ware").toTypedArray()
            val fact = JOptionPane.showInputDialog(this, "Select fact of sale", "Update fact of sale",
                    JOptionPane.QUESTION_MESSAGE, null, facts, facts[0]) as Fact? ?: return@addActionListener
            val buyer = JOptionPane.showInputDialog(this, "Select buyer", "Update fact of sale",
                    JOptionPane.QUESTION_MESSAGE,null,buyers,fact.buyer) as Buyer? ?: return@addActionListener
            val seller = JOptionPane.showInputDialog(this, "Select seller", "Update fact of sale",
                    JOptionPane.QUESTION_MESSAGE, null, sellers, fact.seller) as Seller? ?: return@addActionListener
            val maker = JOptionPane.showInputDialog(this, "Select maker", "Update fact of sale",
                    JOptionPane.QUESTION_MESSAGE, null, makers, fact.maker) as Maker? ?: return@addActionListener
            val ware = JOptionPane.showInputDialog(this, "Select ware", "Update fact of sale",
                    JOptionPane.QUESTION_MESSAGE, null, wares, fact.ware) as Ware? ?: return@addActionListener
            val amount = JOptionPane.showInputDialog(this, "Set amount", "Update fact of sale",
                    JOptionPane.QUESTION_MESSAGE)?.toIntOrNull() ?: return@addActionListener
            fact.buyer = buyer
            fact.seller = seller
            fact.maker = maker
            fact.ware = ware
            fact.amount = amount
            db.exec(fact, db.UPDATE)
            table = showTable()
            scroll.setViewportView(table)
        }
        tools.add(update)
        return tools
    }
}
