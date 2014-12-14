/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.services;

import ch.heigvd.amt.project1.model.FactSummary;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Yann
 */
@Local
public interface FactSummariesManagerLocal {
    public FactSummary findFactSummaryById(long id);

    public List<FactSummary> findFactSummariesByOrganizationId(long id);

    public List<FactSummary> findFactSummariesBySensorId(long id);

    public FactSummary createFactSummary(FactSummary fact);

    public void updateFactSummary(FactSummary fact);

    public void deleteFactSummary(FactSummary fact);
}
