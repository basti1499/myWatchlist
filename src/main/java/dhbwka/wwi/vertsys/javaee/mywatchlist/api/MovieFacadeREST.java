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
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author harte
 */
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

//    @POST
//    @Override
//    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public void create(Movie entity) {
//        super.create(entity);
//    }
//
//    @PUT
//    @Path("{id}")
//    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public void edit(@PathParam("id") Long id, Movie entity) {
//        super.edit(entity);
//    }
//
//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") Long id) {
//        super.remove(super.find(id));
//    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Movie find(@PathParam("id") Long id) {
        return super.find(id);
    }
    
    @GET
    @Path("search/{title}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<MovieDTO> findDTO(@PathParam("title") String title) {
        return movieFacade.get(title);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<MovieDTO> findAllDTO() {
        return movieFacade.getAll();
    }
    
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Movie> findAll() {
        return super.findAll();
    }

//    @GET
//    @Path("{from}/{to}")
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public List<Movie> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
//        return super.findRange(new int[]{from, to});
//    }

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