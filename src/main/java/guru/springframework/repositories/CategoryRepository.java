package guru.springframework.repositories;

import guru.springframework.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

// spring data and H2 DB

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByLastName(String name);
}
