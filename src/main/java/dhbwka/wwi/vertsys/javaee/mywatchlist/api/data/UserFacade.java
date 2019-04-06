/*
 * Copyright © 2019 Christopher Duerr, Bastian Hartenstein, Nico Schmitt
 * 
 * E-Mail: duerr.christopher@student.dhbw-karlsruhe.de; hartenstein.bastian@student.dhbw-karlsruhe.de; schmitt.nico@student.dhbw-karlsruhe.de
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mywatchlist.api.data;

import dhbwka.wwi.vertsys.javaee.mywatchlist.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.mywatchlist.common.jpa.User;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;


@Stateless
public class UserFacade {
    
    @EJB
    private UserBean userBean;
    
    /**
     * Methode zum finden aller Nutzer.
     * @return Liste aller registrierter Nutzer
     */
    public List<UserDTO> getAll() {
        
        List<User> users = userBean.findAll();
        
        return users.stream().map((user) -> {
            
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(user.getUsername());
            userDTO.setVorname(user.getVorname());
            userDTO.setNachname(user.getNachname());
            userDTO.setAlter(user.getAlter());
            
            return userDTO;
            
        }).collect(Collectors.toList());
        
    }
    
    /**
     * Methode zum finden eines Nutzers anhand seiner ID
     * @param id ID des gesuchten Nutzer
     * @return Nutzer mit der ID
     */
    public UserDTO get(String id) {
        User user = userBean.find(id);
        if (user != null) {
            return new UserDTO(user.getUsername(),
                    user.getVorname(),
                    user.getNachname(),
                    user.getAlter());
        }
        return null;
    }
}