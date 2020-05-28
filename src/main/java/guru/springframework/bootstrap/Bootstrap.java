package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Product;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.ProductRepository;
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
    private  final ProductRepository productRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        LoadCategories();

        LoadCustomers();

        LoadVendors();

        LoadProducts();

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

        vendorRepository.save(v1);
        vendorRepository.save(v2);

        log.info("Vendors Loader finished : " + vendorRepository.count());
    }

    private void LoadProducts(){

        Product product1 = Product.builder().name("Pineapples").build();
        Product product2 = Product.builder().name("Apples").build();
        Product product3 = Product.builder().name("Oranges").build();

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        log.info("Products Loader finished : " + productRepository.count());
    }
}
