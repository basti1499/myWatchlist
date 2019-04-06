/*
 * Copyright © 2019 Christopher Duerr, Bastian Hartenstein, Nico Schmitt
 * 
 * E-Mail: duerr.christopher@student.dhbw-karlsruhe.de; hartenstein.bastian@student.dhbw-karlsruhe.de; schmitt.nico@student.dhbw-karlsruhe.de
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mywatchlist.api;

import dhbwka.wwi.vertsys.javaee.mywatchlist.api.data.MovieDTO;
import dhbwka.wwi.vertsys.javaee.mywatchlist.api.data.MovieFacade;
import dhbwka.wwi.vertsys.javaee.mywatchlist.movies.jpa.Movie;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Stateless
//@Path("dhbwka.wwi.vertsys.javaee.mywatchlist.tasks.jpa.movie")
@Path("movie")
public class MovieFacadeREST extends AbstractFacade<Movie> {

    @PersistenceContext(unitName = "default")
    private EntityManager em;
    
    @EJB
    MovieFacade movieFacade;

    public MovieFacadeREST() {
        super(Movie.class);
    }
    
    /**
     * Reagiert auf GET-Anfragen auf /api/movie/search.
     * @param search Parameter für die Suche nach Filmen
     * @return Liste aller Filme die auf die Suche passen
     */
    @RolesAllowed("app-user")
    @GET
    @Path("search")
    @Produces({MediaType.APPLICATION_JSON})
    public List<MovieDTO> findDTO(@QueryParam("search") String search) {
        return movieFacade.get(search);
    }

    /**
     * Reagiert auf GET-Anfragen auf /api/movie.
     * @return Liste aller Filme
     */
    @RolesAllowed("app-user")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<MovieDTO> findAllDTO() {
        return movieFacade.getAll();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}