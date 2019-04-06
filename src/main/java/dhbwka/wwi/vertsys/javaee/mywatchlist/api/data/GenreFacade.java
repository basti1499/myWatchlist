/*
 * Copyright © 2019 Christopher Duerr, Bastian Hartenstein, Nico Schmitt
 * 
 * E-Mail: duerr.christopher@student.dhbw-karlsruhe.de; hartenstein.bastian@student.dhbw-karlsruhe.de; schmitt.nico@student.dhbw-karlsruhe.de
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mywatchlist.api.data;

import dhbwka.wwi.vertsys.javaee.mywatchlist.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.mywatchlist.movies.ejb.GenreBean;
import dhbwka.wwi.vertsys.javaee.mywatchlist.movies.jpa.Genre;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;


@Stateless
public class GenreFacade {
    
    @EJB
    GenreBean genreBean;
    
    @EJB
    MovieFacade movieFacade;
    
    public List<GenreDTO> getAll() {
        
        List<Genre> genres = genreBean.findAll();
        
        return genres.stream().map((genre) -> {
            
            GenreDTO genreDTO = new GenreDTO();
            genreDTO.setId(genre.getId());
            genreDTO.setName(genre.getName());
            
            return genreDTO;
            
        }).collect(Collectors.toList());
        
    }
    
    public GenreDTO get(Long id) {
        
        Genre genre = genreBean.findById(id);
        
        if (genre != null) {
            return new GenreDTO(genre.getId(), genre.getName());
        }
        return null;
    }
    
}
