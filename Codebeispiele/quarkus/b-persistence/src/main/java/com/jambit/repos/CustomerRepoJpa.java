package com.jambit.repos;

import com.jambit.entities.Customer;
import io.quarkus.arc.profile.IfBuildProfile;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
//@IfBuildProfile("jpa")
public class CustomerRepoJpa implements CustomerRepo{
    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Customer> findAll() {
        return em.createQuery("select c from Customer c order by c.name", Customer.class)
                .getResultList();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return Optional.of(em.find(Customer.class, id));
    }

    @Override
    @Transactional
    public void remove(Long id) {
        findById(id).ifPresent(c -> em.remove(c));
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        return em.merge(customer);
    }
}
