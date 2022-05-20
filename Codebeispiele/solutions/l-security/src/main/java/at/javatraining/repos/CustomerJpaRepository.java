package at.javatraining.repos;

import at.javatraining.entities.Customer;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.arc.properties.IfBuildProperty;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@IfBuildProfile("jpa")
@ApplicationScoped
public class CustomerJpaRepository implements CustomerRepository {
    @PersistenceContext
    EntityManager em;

    @Override
    @Counted(name = "counted findAllCustomers")
    @Timed(name = "timed findAllCustomers")
    public List<Customer> findAllCustomers() {
        return em.createQuery("select c from Customer c order by c.name", Customer.class)
                .getResultList();
    }

    @Override
    public Optional<Customer> findCustomerById(Long id) {
        return Optional.ofNullable(em.find(Customer.class, id));
    }

    @Override
    @Transactional
    public Customer saveCustomer(Customer customer) {
        return em.merge(customer);
    }

    @Override
    @Transactional
    public boolean deleteCustomer(Long id) {
        Customer customer = em.find(Customer.class, id);
        if (customer != null) {
            em.remove(customer);
            return true;
        } else {
            return false;
        }
    }
}
