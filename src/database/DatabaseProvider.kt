package database

import database.entry.Entry
import org.hibernate.HibernateError
import org.hibernate.cfg.Configuration

object DatabaseProvider {
    val SAVE = 0
    val UPDATE = 1
    val DELETE = 2
    private val sessionFactory = Configuration().configure("hibernate.cfg.xml").buildSessionFactory()!!
    fun exec(entry: Entry, query: Int = SAVE) {
        val session = sessionFactory.openSession()
        try {
            session.beginTransaction()
            when (query) {
                SAVE -> session.save(entry)
                UPDATE -> session.saveOrUpdate(entry)
                DELETE -> session.delete(entry)
            }
            session.transaction.commit()
        } catch (e: HibernateError) {
            e.printStackTrace()
            session.transaction.rollback()
        } finally {
            session.close()
        }
    }
    fun <T> read(entry: String): List<T> {
        val session = sessionFactory.openSession()
        var list = emptyList<T>()
        try {
            session.beginTransaction()
            list = session.createQuery("From $entry").list() as List<T>
            session.transaction.commit()
        } catch (e: HibernateError) {
            e.printStackTrace()
            session.transaction.rollback()
        } finally {
            session.close()
        }
        return list
    }
}