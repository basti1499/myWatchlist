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
import dhbwka.wwi.vertsys.javaee.mywatchlist.movies.jpa.Category;
import dhbwka.wwi.vertsys.javaee.mywatchlist.movies.jpa.MovieStatus;
import java.sql.Date;
import java.sql.Time;


public class MovieDTO {
    
    private long id;
    private UserDTO Owner;
    private Category category;
    private String titel;
    private String director;
    private int year1;
    private String dueDate;
    private String dueTime;
    private MovieStatus status;

    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDTO getOwner() {
        return Owner;
    }

    public void setOwner(UserDTO Owner) {
        this.Owner = Owner;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getYear1() {
        return year1;
    }

    public void setYear1(int year1) {
        this.year1 = year1;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public MovieStatus getStatus() {
        return status;
    }

    public void setStatus(MovieStatus status) {
        this.status = status;
    }
    
    
    
}