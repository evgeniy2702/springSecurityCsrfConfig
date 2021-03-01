package zhurenko.ua.model;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "owners")
@Proxy(lazy = false)
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_owner")
    private Long id;
    @Column(name = "name")
    private String nameOwner;
    @Column(name = "email")
    private String username;
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER,
            mappedBy = "owners")
//    @JoinTable(name = "owner_books",
//            joinColumns =  @JoinColumn(name = "owner_id" , referencedColumnName = "id_owner"),
//            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"))
    private Set<Book> bookOwnerSet ;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "owner_role",
            joinColumns = @JoinColumn(name = "owner_id", referencedColumnName = "id_owner"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOwner() {
        return nameOwner;
    }

    public String getUsername() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setNameOwner(String nameOwner) {
        this.nameOwner = nameOwner;
    }

    public Set<Book> getBookOwnerSet() {
        return bookOwnerSet;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", nameOwner='" + nameOwner + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles ='" + roles.stream().map(r -> r.getName()).toArray()[0] + '\'' +
                '}';
    }
}
