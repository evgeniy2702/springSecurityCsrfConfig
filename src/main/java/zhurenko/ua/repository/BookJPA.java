package zhurenko.ua.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zhurenko.ua.model.Book;
import zhurenko.ua.model.Owner;

import java.util.List;


@Repository
public interface BookJPA extends JpaRepository<Book, Long> {

   Book getBookById(Long id);

    List<Book> findBookByName(String name);

    List<Book> findBookByAuthor(String author);

    List<Book> findBookByYear(int year);

    List<Book> findBookByStileOfBook(String stile);

    List<Book> findBookByNumPages(int numPages);

    List<Book> findBookByDescription(String desc);

    List<Book> findBookByOwnersIsContaining(Owner owner);

}
