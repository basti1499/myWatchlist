/*
 * Copyright © 2019 Christopher Duerr, Bastian Hartenstein, Nico Schmitt
 * 
 * E-Mail: duerr.christopher@student.dhbw-karlsruhe.de; hartenstein.bastian@student.dhbw-karlsruhe.de; schmitt.nico@student.dhbw-karlsruhe.de
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mywatchlist.api.data;

import dhbwka.wwi.vertsys.javaee.mywatchlist.movies.ejb.MovieBean;
import dhbwka.wwi.vertsys.javaee.mywatchlist.movies.jpa.Movie;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;


@Stateless
public class MovieFacade {
    
    @EJB
    private MovieBean movieBean;
    
    /**
     * Methode zum finden aller Filme.
     * @return Liste aller Filme
     */
    public List<MovieDTO> getAll() {
        
        List<Movie> movies = movieBean.findAll();
        
        return movies.stream().map((movie) -> {
            
            MovieDTO movieDTO = new MovieDTO();
            movieDTO.setId(movie.getId());
            movieDTO.setTitel(movie.getTitel());
            movieDTO.setOwner(new UserDTO(movie.getOwner().getUsername(),
                    movie.getOwner().getVorname(),
                    movie.getOwner().getNachname(),
                    movie.getOwner().getAlter()));
            movieDTO.setDirector(movie.getDirector());
            movieDTO.setYear1(movie.getYear1());
            movieDTO.setGenre(new GenreDTO(movie.getGenre().getId(), movie.getGenre().getName()));
            movieDTO.setDueDate(movie.getDueDate().toString());
            movieDTO.setDueTime(movie.getDueTime().toString());
            movieDTO.setStatus(movie.getStatus());
            
            return movieDTO;
            
        }).collect(Collectors.toList());
        
    }
    
    /**
     * Methode zum Suchen nach Filmen anhand des Titels.
     * @param search Titel
     * @return Liste aller Filme deren Titel passt
     */
    public List<MovieDTO> get(String search) {
        
        List<Movie> movies = movieBean.findByTitle(search);
        
        return movies.stream().map((movie) -> {
            MovieDTO movieDTO = new MovieDTO();
            movieDTO.setId(movie.getId());
            movieDTO.setTitel(movie.getTitel());
            movieDTO.setOwner(new UserDTO(movie.getOwner().getUsername(),
                    movie.getOwner().getVorname(),
                    movie.getOwner().getNachname(),
                    movie.getOwner().getAlter()));
            movieDTO.setDirector(movie.getDirector());
            movieDTO.setYear1(movie.getYear1());
            movieDTO.setGenre(new GenreDTO(movie.getGenre().getId(), movie.getGenre().getName()));
            movieDTO.setDueDate(movie.getDueDate().toString());
            movieDTO.setDueTime(movie.getDueTime().toString());
            movieDTO.setStatus(movie.getStatus());
            
            return movieDTO;
        }).collect(Collectors.toList());
    }
    
}