/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.services;

import ch.heigvd.amt.project1.model.Fact;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Yann
 */
@Local
public interface FactsManagerLocal {
    public Fact findFactById(long id);

    public List<Fact> findAllFacts();

    public Fact createFact(Fact fact);

    public void updateFact(Fact fact);

    public void deleteFact(Fact fact);
}
