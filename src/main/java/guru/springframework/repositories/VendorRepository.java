package guru.springframework.repositories;

import guru.springframework.domain.Product;
import guru.springframework.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

    Vendor findVendorByName(String name);

}
