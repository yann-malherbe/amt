/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.services;

import ch.heigvd.amt.project1.model.Fact;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yann
 */
@Stateless
public class FactsManager implements FactsManagerLocal {

@PersistenceContext
    public EntityManager em;

    @Override
    public Fact findFactById(long id) {
        return em.find(Fact.class, id);
    }

    @Override
    public List<Fact> findAllFacts() {
        return em.createNamedQuery("findAllFacts").getResultList();
    }

    @Override
    public Fact createFact(Fact fact) {
        em.persist(fact);
        em.flush();
        return fact;
    }

    @Override
    public void updateFact(Fact fact) {
        em.merge(fact);
    }

    @Override
    public void deleteFact(Fact fact) {
        em.remove(fact);
    }
}
