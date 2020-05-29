package guru.springframework.repositories;

import guru.springframework.domain.Category;
import guru.springframework.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductByName(String name);

}
