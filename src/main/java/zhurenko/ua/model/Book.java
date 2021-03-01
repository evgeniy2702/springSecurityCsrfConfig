package zhurenko.ua.model;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "books")
@Proxy(lazy=false)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String author;
    private int year;

    @Column(name = "stile")
    private String stileOfBook;

    @Column(name = "num_pages")
    private int numPages;
    private String description;

    @ManyToOne(targetEntity = Buyer.class,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "buyer_id",
            referencedColumnName = "buyer_id")
    private Buyer buyer;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "owner_books",
            joinColumns =  @JoinColumn(name = "book_id" , referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "owner_id", referencedColumnName = "id_owner"))
    private Set<Owner> owners ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getStileOfBook() {
        return stileOfBook;
    }

    public void setStileOfBook(String stileOfBook) {
        this.stileOfBook = stileOfBook;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Set<Owner> getOwners() {
        return owners;
    }

    public void setOwners(Set<Owner> owners) {
        this.owners = owners;
    }

    private String ownerToString(Set<Owner> owners){
        String string = "";
        for (Owner owner : owners) {
            string = string.concat(owner.toString());
        }
        return string;
    }

    @Override
    public String toString() {
        if(owners.size() == 0){
            return "Book{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", author='" + author + '\'' +
                    ", year=" + year +
                    ", stileOfBook='" + stileOfBook + '\'' +
                    ", numPages=" + numPages +
                    ", description='" + description + '\'' +
                    ", buyerId=" + buyer.getId() +
                    '}';
        } else {
            return "Book{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", author='" + author + '\'' +
                    ", year=" + year +
                    ", stileOfBook='" + stileOfBook + '\'' +
                    ", numPages=" + numPages +
                    ", description='" + description + '\'' +
                    ", buyerId=" + buyer.getId() + '\'' +
                    ", buyerName=" + buyer.getNameBuyer() + '\'' +
                    ", " + ownerToString(owners) +
                    '}';
        }
    }
}
