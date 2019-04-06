/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mywatchlist.movies.ejb;

import dhbwka.wwi.vertsys.javaee.mywatchlist.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.mywatchlist.movies.jpa.Genre;
import dhbwka.wwi.vertsys.javaee.mywatchlist.movies.jpa.Movie;
import dhbwka.wwi.vertsys.javaee.mywatchlist.movies.jpa.MovieStatus;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Einfache EJB mit den üblichen CRUD-Methoden für Aufgaben
 */
@Stateless
@RolesAllowed("app-user")
public class MovieBean extends EntityBean<Movie, Long> { 
   
    public MovieBean() {
        super(Movie.class);
    }
    
    /**
     * Alle Aufgaben eines Benutzers, nach Fälligkeit sortiert zurückliefern.
     * @param username Benutzername
     * @return Alle Aufgaben des Benutzers
     */
    public List<Movie> findByUsername(String username) {
        return em.createQuery("SELECT t FROM Movie t WHERE t.owner.username = :username ORDER BY t.dueDate, t.dueTime")
                 .setParameter("username", username)
                 .getResultList();
    }
    
    /**
     * Suche nach Aufgaben anhand ihrer Bezeichnung, Kategorie und Status.
     * 
     * Anders als in der Vorlesung behandelt, wird die SELECT-Anfrage hier
     * mit der CriteriaBuilder-API vollkommen dynamisch erzeugt.
     * 
     * @param search In der Kurzbeschreibung enthaltener Text (optional)
     * @param genre Kategorie (optional)
     * @param status Status (optional)
     * @return Liste mit den gefundenen Aufgaben
     */
    public List<Movie> search(String search, Genre genre, MovieStatus status) {
        // Hilfsobjekt zum Bauen des Query
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        
        // SELECT t FROM Movie t
        CriteriaQuery<Movie> query = cb.createQuery(Movie.class);
        Root<Movie> from = query.from(Movie.class);
        query.select(from);

        // ORDER BY dueDate, dueTime
        query.orderBy(cb.asc(from.get("dueDate")), cb.asc(from.get("dueTime")));
        
        // WHERE t.titel LIKE :search
        Predicate p = cb.conjunction();
        
        if (search != null && !search.trim().isEmpty()) {
            p = cb.and(p, cb.like(from.get("titel"), "%" + search + "%"));
            query.where(p);
        }
        
        // WHERE t.genre = :genre
        if (genre != null) {
            p = cb.and(p, cb.equal(from.get("genre"), genre));
            query.where(p);
        }
        
        // WHERE t.status = :status
        if (status != null) {
            p = cb.and(p, cb.equal(from.get("status"), status));
            query.where(p);
        }
        
        return em.createQuery(query).getResultList();
    }
    
    /**
     * Suche nach Filmen anhand des Titels.
     * @param title Titel nach dem gesucht werden soll
     * @return Liste aller passenden Filme
     */
    public List<Movie> findByTitle(String title) {
        return em.createQuery("SELECT t FROM Movie t WHERE t.titel LIKE :title")
                .setParameter("title", "%" + title + "%")
                .getResultList();
    }
}
