package ru.slimercorp.springcourse.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.slimercorp.springcourse.models.Book;
import ru.slimercorp.springcourse.models.Person;

import java.util.List;
import java.util.Optional;

/**
 * @author Neil Alishev
 */
@Component
public class BookDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Book> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select p from Book p", Book.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Book show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
    }

    @Transactional
    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Session session = sessionFactory.getCurrentSession();
        Book bookToUpdated = session.get(Book.class, id);
        bookToUpdated.setName(updatedBook.getName());
        bookToUpdated.setAuthor(updatedBook.getAuthor());
        bookToUpdated.setYear(updatedBook.getYear());
    }
    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.get(Book.class, id));
    }

    @Transactional
    public Optional<Person> whoGrabBook(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        return Optional.ofNullable(book.getOwner());
    }

    @Transactional
    public void release(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setOwner(null);
    }

    @Transactional
    public void setPerson(int id, Person person) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setOwner(person);
    }
}
