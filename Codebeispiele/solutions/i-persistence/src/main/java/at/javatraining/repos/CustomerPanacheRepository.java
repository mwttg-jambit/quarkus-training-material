package at.javatraining.repos;


import at.javatraining.entities.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class CustomerPanacheRepository implements PanacheRepositoryBase<Customer,Long>{
    public List<Customer> findAllKings(){
        return find("status", Customer.Status.KING).list();
    }

    public List<Customer> findAllVips(){
        return getEntityManager().createQuery("select c from Customer c where c.status=:status")
                .setParameter("status", Customer.Status.VIP)
                .getResultList();
    }

    public List<Customer> findAllOrdinaries(){
        return find("select c from Customer c where c.status=?1", Customer.Status.ORDINARY).list();
    }

    // use a named query
    public Stream<Customer> findCustomerByName(String name){
        return find("#Customer.findByName", name).stream();
    }
}
