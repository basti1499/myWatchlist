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
import dhbwka.wwi.vertsys.javaee.mywatchlist.movies.ejb.CategoryBean;
import dhbwka.wwi.vertsys.javaee.mywatchlist.movies.jpa.Category;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author harte
 */
@Stateless
public class CategoryFacade {
    
    @EJB
    CategoryBean categoryBean;
    
    @EJB
    MovieFacade movieFacade;
    
    public List<CategoryDTO> getAll() {
        
        List<Category> categories = categoryBean.findAll();
        
        return categories.stream().map((category) -> {
            
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            
            return categoryDTO;
            
        }).collect(Collectors.toList());
        
    }
    
    public CategoryDTO get(Long id) {
        
        Category category = categoryBean.findById(id);
        
        return new CategoryDTO(category.getId(), category.getName());
        
    }
    
}
