/*
 * Copyright © 2019 Christopher Duerr, Bastian Hartenstein, Nico Schmitt
 * 
 * E-Mail: duerr.christopher@student.dhbw-karlsruhe.de; hartenstein.bastian@student.dhbw-karlsruhe.de; schmitt.nico@student.dhbw-karlsruhe.de
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mywatchlist.api;

import dhbwka.wwi.vertsys.javaee.mywatchlist.api.data.CategoryDTO;
import dhbwka.wwi.vertsys.javaee.mywatchlist.api.data.CategoryFacade;
import dhbwka.wwi.vertsys.javaee.mywatchlist.movies.jpa.Category;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Stateless
//@Path("dhbwka.wwi.vertsys.javaee.mywatchlist.tasks.jpa.category")
@Path("category")
public class CategoryFacadeREST extends AbstractFacade<Category> {

    @PersistenceContext(unitName = "default")
    private EntityManager em;
    
    @EJB
    CategoryFacade categoryFacade;

    public CategoryFacadeREST() {
        super(Category.class);
    }
    
    /**
     * Reagiert auf GET-Anfragen an /api/category/{id}.
     * @param id ID des gesuchten Genres
     * @return 
     */
    @RolesAllowed("app-user")
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public CategoryDTO findDTO(@PathParam("id") Long id) {
        return categoryFacade.get(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<CategoryDTO> findAllDTO() {
        return categoryFacade.getAll();
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}