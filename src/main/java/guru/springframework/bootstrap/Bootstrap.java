package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner{

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        LoadCategories();

        LoadCustomers();

        LoadVendors();

    }

    private void LoadCustomers() {
        Customer customer1 = Customer.builder().id(1L).firstName("Lior").lastName("Lavon").build();
        Customer customer2 = Customer.builder().id(2L).firstName("Meitar").lastName("Lavon").build();
        Customer customer3 = Customer.builder().id(3L).firstName("Noa").lastName("Lavon").build();

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        log.info("Customer Loader finished : " + customerRepository.count());
    }

    private void LoadCategories() {
        Category fruit = new Category();
        fruit.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruit);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        log.info("Category Loader finished : " + categoryRepository.count());
    }

    private void LoadVendors(){

        Vendor v1 = Vendor.builder().name("Western Tasty Fruits Ltd.").build();

        Vendor v2 = Vendor.builder().name("Exotic Fruits Company").build();

        Vendor v3 = Vendor.builder().name("Home Fruits").build();

        Vendor v4 = Vendor.builder().name("Fun Fresh Fruits Ltd.").build();

        Vendor v5 = Vendor.builder().name("Nuts for Nuts Company").build();

        vendorRepository.save(v1);
        vendorRepository.save(v2);
        vendorRepository.save(v3);
        vendorRepository.save(v4);
        vendorRepository.save(v5);

        log.info("Vendors Loader finished : " + vendorRepository.count());
    }
}
