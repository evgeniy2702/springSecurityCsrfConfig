package zhurenko.ua.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zhurenko.ua.model.Book;
import zhurenko.ua.model.Owner;
import zhurenko.ua.service.BookService;
import zhurenko.ua.service.OwnerService;

import java.security.Principal;


@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = {"/","/showBook"})
    public String showBook(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null) {
            System.out.println("showBook null");
        }
        else {
            System.out.println("showBook " + auth.toString());
        }
        return "showBooks";
    }

    @GetMapping("/book/{id}")
    public String getBook(@PathVariable(value = "id", required = false) Long id,
                          Model model)  {
        Book book = bookService.getByIdBook(id);
        model.addAttribute("book", book);
        return "book";
    }

    @GetMapping("/book/new")
    public String addBookGet(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null) {
            System.out.println("book/new null");
        }
        else {
            System.out.println("book/new " + auth.toString());
        }
        model.addAttribute("book", bookService.addNewBook());
        model.addAttribute("default", "Default value");
        return "addBook";
    }

    @PostMapping("/book/new")
    public String addBookPost(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null) {
            System.out.println("book/new null");
        }
        else {
            System.out.println("book/new " + auth.toString());
        }
        model.addAttribute("book", bookService.addNewBook());
        model.addAttribute("default", "Default value");
        return "addBook";
    }

    @GetMapping("/book/delete/{id}")
    public String deleteBook(@PathVariable(value = "id", required = false) Long id,
                             Model model){
        Book book = bookService.getByIdBook(id);
        bookService.deleteBook(book);
        model.addAttribute("books", bookService.getAllBooks());
        return "redirect:/";
    }

    @PostMapping("/book/add")
    public String saveBook(@ModelAttribute Book book){
        bookService.saveBook(book);
        return "redirect:/";
    }

    @PostMapping("/book/update/{id}")
    public String updateBook(@PathVariable(value = "id", required = false) Long id,
                             @ModelAttribute Book book){
        bookService.updateBookById(book, id);
        return "redirect:/";
    }

    @GetMapping("/book/delete/relation")
    public String updateBookWithoutOwner(@RequestParam("book_id") Long book_id,
                                         @RequestParam("owner_id") Long owner_id){
        bookService.deleteOwner(book_id, owner_id);
        return "redirect:/";
    }

    @GetMapping("/book/add/owner")
    public String addOwner (@RequestParam("id") Long book_id,
                            @RequestParam("owner_book") String string){
    bookService.addOwner(book_id, string);
        return "redirect:/";
    }

    @GetMapping("/book/update/{id}")
    public String showUpdateBook(@PathVariable(value = "id", required = false) Long id,
                                 Model model){
        model.addAttribute("book",bookService.getByIdBook(id));
        return "updateBook";
    }

    @GetMapping("/book/search")
    public String search(@ModelAttribute Book book){
        return "searchBook";
    }

    @GetMapping("/books")
    public String searchBook(@RequestParam(value = "search", required = false) String search,
                             Model model){
        model.addAttribute("booksSearch", bookService.searchBook(search));
        return "searchBook";
    }
}
