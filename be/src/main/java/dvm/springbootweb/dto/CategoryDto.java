package dvm.springbootweb.dto;

import dvm.springbootweb.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private String categoryName;
    private String description;
    private Set<Book> listBooks = new HashSet<>();
}
