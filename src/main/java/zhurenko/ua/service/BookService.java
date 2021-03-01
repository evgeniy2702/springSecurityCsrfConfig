package zhurenko.ua.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhurenko.ua.model.Book;
import zhurenko.ua.model.Owner;
import zhurenko.ua.repository.BookJPA;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class BookService {

    private final BookJPA bookJPA;
    private final OwnerService ownerService;

    @Autowired
    public BookService(BookJPA bookJPA, OwnerService ownerService) {
        this.bookJPA = bookJPA;
        this.ownerService = ownerService;
    }

    public void saveBook(Book book){
        bookJPA.save(book);
        bookJPA.flush();
    }

    public void deleteBook(Book book){
        bookJPA.delete(book);
        bookJPA.flush();
    }

    public void updateBook(Book book){
        bookJPA.saveAndFlush(book);
    }

    public Book getByIdBook(Long id){
        return bookJPA.getBookById(id);
    }

    public List<Book> getAllBooks() {
        return bookJPA.findAll();
    }

    public <T> List<Book> searchBook(T search) {

        List<Book> books = new ArrayList<>();
        if( search instanceof String ) {
            if (bookJPA.findBookByName((String)search).size() != 0)
                books = bookJPA.findBookByName((String) search);
            if (bookJPA.findBookByAuthor((String)search).size() != 0)
                books = bookJPA.findBookByAuthor((String)search);
            if (bookJPA.findBookByStileOfBook((String) search).size() != 0)
                books = bookJPA.findBookByStileOfBook((String) search);
            if (bookJPA.findBookByDescription((String) search).size()!= 0)
                books = bookJPA.findBookByDescription((String) search);
            List<Owner> owners = ownerService.getListOwners();
            Owner owner = (Owner)owners.stream().filter(o -> o.getNameOwner().equalsIgnoreCase((String)search))
                        .toArray()[0];
            if(bookJPA.findBookByOwnersIsContaining(owner).size() != 0)
                books = bookJPA.findBookByOwnersIsContaining(owner);
        } else if (search instanceof Integer) {
            if (bookJPA.findBookByYear((Integer) search).size() != 0)
                books = bookJPA.findBookByYear((Integer) search);
            if (bookJPA.findBookByNumPages((Integer) search).size()!= 0)
                books = bookJPA.findBookByNumPages((Integer) search);
        }
        return books;
    }

    public Set<Owner> getOwners() {
        return new HashSet<>(ownerService.getListOwners());
    }

    public void addOwner(Long book_id, String string){
        if (ownerService.getListOwners().stream().noneMatch(o -> o.getNameOwner().equalsIgnoreCase(string))) {
            Owner owner = new Owner();
            owner.setNameOwner(string);
            ownerService.saveOwner(owner);

            Book book = getByIdBook(book_id);

            book.getOwners().add(owner);
            saveBook(book);

        } else {
            Owner owner = (Owner) ownerService.getListOwners().stream()
                    .filter(o -> o.getNameOwner().equalsIgnoreCase(string))
                    .toArray()[0];

            Book book = getByIdBook(book_id);
            book.getOwners().add(owner);
            saveBook(book);
        }
    }

    public void deleteOwner(Long book_id, Long owner_id) {
        Book book = getByIdBook(book_id);
        Owner owner = ownerService.getOwnerById(owner_id);

        Set<Owner> owners = book.getOwners();
        for (Owner o :owners) {
            if(o.getId().equals(owner_id) ){
                owner = o;
                break;
            }
        }
        owners.remove(owner);

        updateBook(book);
    }

    public Book addNewBook(){
        Book book = new Book();
        book.setOwners(new HashSet<>(getOwners().size()));
        return book;
    }

    public void updateBookById(Book book, Long id) {
        Book book1 = getByIdBook(id);
        book.setOwners(book1.getOwners());
        book1 = book;
        updateBook(book1);
    }
}
