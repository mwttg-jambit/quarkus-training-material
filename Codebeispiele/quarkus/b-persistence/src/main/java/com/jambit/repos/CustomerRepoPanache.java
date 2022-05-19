package com.jambit.repos;

import com.jambit.entities.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class CustomerRepoPanache implements PanacheRepositoryBase<Customer, Long> {
    public List<Customer> findAllKings(){
        return find("status", Customer.Status.KING).list();
    }

    public List<Customer> findAllVips(){
        return getEntityManager().createQuery("select c from Customer c where c.status = :status", Customer.class)
                .setParameter("status", Customer.Status.VIP)
                .getResultList();
    }

    public List<Customer> findAllOrdinaries(){
        return find("select c from Customer c where c.status = ?1", Customer.Status.ORDINARY).list();
    }

    public List<Customer> findByNameLike(String name){
        //when using named parameters:
        //find("#Customer.findByName", Parameters.with("name", name));
        return find("#Customer.findByName", name).list();
    }

}
