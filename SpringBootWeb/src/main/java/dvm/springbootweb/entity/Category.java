package dvm.springbootweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;
    private String categoryName;
    @Column(length = 1000)
    private String description;
    @OneToMany(mappedBy = "category",cascade=CascadeType.ALL)
    private Set<Book> listBooks;
}
