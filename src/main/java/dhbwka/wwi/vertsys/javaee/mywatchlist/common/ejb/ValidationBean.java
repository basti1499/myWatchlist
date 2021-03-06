/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mywatchlist.common.ejb;

import dhbwka.wwi.vertsys.javaee.mywatchlist.movies.jpa.Movie;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

/**
 * Kleine EJB, die dafür genutzt werden kann, die Werte einer Entity zu
 * validieren, bevor diese gespeichert wird. Zwar validiert der Entity Manager
 * die Bean beim Speichern ebenfalls, da das aber erst am Ende der Transaktion
 * erfolgt, ist es schwer, rechtzeitig darauf zu reagieren.
 */
@Stateless
public class ValidationBean {
    
    @Resource
    Validator validator;
    
    @EJB
    UserBean userBean;
    
    /**
     * Wertet die "Bean Validation" Annotationen des übergebenen Objekts aus
     * (@Min, @Max, @Size, @NotNull, …) und gib eine Liste der dabei gefundenen
     * Fehler zurück.
     * 
     * @param <T>
     * @param object Zu überprüfendes Objekt
     * @return Liste mit Fehlermeldungen
     */
    public <T> List<String> validate(T object) {
        List<String> messages = new ArrayList<>();
        return this.validate(object, messages);
    }
    
    /**
     * Wertet die "Bean Validation" Annotationes des übergebenen Objekts aus
     * und stellt die gefundenen Meldungen in messages.
     * 
     * @param <T>
     * @param object Zu überprüfendes Objekt
     * @param messages List mit Fehlermeldungen
     * @return Selbe Liste wie messages
     */
    public <T> List<String> validate(T object, List<String> messages) {
        Set<ConstraintViolation<T>> violations = this.validator.validate(object);
        
        violations.forEach((ConstraintViolation<T> violation) -> {
            messages.add(violation.getMessage());
        });
        
        return messages;
    }
    
    /**
     * Überprüft ob der aktuell angemeldete Benutzer auch der Besitzer des Filmes ist,
     * ist er es nicht, darf er diesen nicht bearbeiten.
     * @param movie Zu bearbeitender Film
     * @param messages Bisherige Fehlermessages
     * @return Bisherige + Neue Fehlermessages
     */
    public List<String> validateOwner(Movie movie, List<String> messages) {
        
        if (!userBean.getCurrentUser().getUsername().equals(movie.getOwner().getUsername())) {
            messages.add("Du darfst nur deine eigenen Movies bearbeiten.");
        }
        
        return messages;
    }
}
