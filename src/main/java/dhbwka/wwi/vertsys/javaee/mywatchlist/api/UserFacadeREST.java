/*
 * Copyright © 2019 Christopher Duerr, Bastian Hartenstein, Nico Schmitt
 * 
 * E-Mail: duerr.christopher@student.dhbw-karlsruhe.de; hartenstein.bastian@student.dhbw-karlsruhe.de; schmitt.nico@student.dhbw-karlsruhe.de
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mywatchlist.api;

import dhbwka.wwi.vertsys.javaee.mywatchlist.api.data.UserDTO;
import dhbwka.wwi.vertsys.javaee.mywatchlist.api.data.UserFacade;
import dhbwka.wwi.vertsys.javaee.mywatchlist.common.jpa.User;
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
@Path("user")
public class UserFacadeREST extends AbstractFacade<User> {

    @PersistenceContext(unitName = "default")
    private EntityManager em;
    
    @EJB
    UserFacade userFacade;

    public UserFacadeREST() {
        super(User.class);
    }
    
    /**
     * Reagiert auf GET-Anfragen mit einer ID im Pfad, dies zeigt einen
     * bestimmten User an.
     * @param id ID des Nutzers
     * @return Einzelnes UserDTO-Objekt im JSON Format
     */
    @RolesAllowed("app-user")
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public UserDTO findDTO(@PathParam("id") String id) {
        return userFacade.get(id);
    }
    
    /**
     * Reagiert auf GET-Anfragen an /api/user, gibt eine Liste aller Nutzer zurück.
     * @return Liste aller Nutzer
     */
    @RolesAllowed("app-user")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<UserDTO> findAllDTO() {
        return userFacade.getAll();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}