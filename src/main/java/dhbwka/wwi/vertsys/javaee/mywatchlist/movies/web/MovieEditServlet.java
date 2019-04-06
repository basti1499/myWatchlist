/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mywatchlist.movies.web;

import dhbwka.wwi.vertsys.javaee.mywatchlist.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.mywatchlist.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.mywatchlist.movies.ejb.GenreBean;
import dhbwka.wwi.vertsys.javaee.mywatchlist.movies.ejb.MovieBean;
import dhbwka.wwi.vertsys.javaee.mywatchlist.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.mywatchlist.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.mywatchlist.movies.jpa.Movie;
import dhbwka.wwi.vertsys.javaee.mywatchlist.movies.jpa.MovieStatus;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Seite zum Anlegen oder Bearbeiten einer Aufgabe.
 */
@WebServlet(urlPatterns = "/app/movies/movie/*")
public class MovieEditServlet extends HttpServlet {

    @EJB
    MovieBean movieBean;

    @EJB
    GenreBean genreBean;

    @EJB
    UserBean userBean;

    @EJB
    ValidationBean validationBean;

    /**
     * Reagiert auf GET-Anfragen, befüllt die Form mit den Filmdaten, falls
     * vorhanden, und leitet den Nutzer an die gewünschte Seite weiter.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kategorien und Stati für die Suchfelder ermitteln
        request.setAttribute("genres", this.genreBean.findAllSorted());
        request.setAttribute("statuses", MovieStatus.values());

        // Zu bearbeitende Aufgabe einlesen
        HttpSession session = request.getSession();

        Movie movie = this.getRequestedMovie(request);
        request.setAttribute("edit", movie.getId() != 0);
                                
        if (session.getAttribute("movie_form") == null) {
            // Keine Formulardaten mit fehlerhaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
            request.setAttribute("movie_form", this.createMovieForm(movie));
        }

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/movies/movie_edit.jsp").forward(request, response);
        
        session.removeAttribute("movie_form");
    }

    /**
     * Reagiert auf POST-Anfragen, verarbeitet die Nutzereingaben, validiert diese
     * und, falls korrekt, fügt sie in die Datenbank ein.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Angeforderte Aktion ausführen
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "save":
                this.saveMovie(request, response);
                break;
            case "delete":
                this.deleteMovie(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neue oder vorhandene Aufgabe speichern
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void saveMovie(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();

        String movieGenre = request.getParameter("movie_genre");
        String movieDueDate = request.getParameter("movie_due_date");
        String movieDueTime = request.getParameter("movie_due_time");
        String movieStatus = request.getParameter("movie_status");
        String movieTitel = request.getParameter("movie_titel");
        String movieDirector = request.getParameter("movie_director"); 
        String movieLongText = request.getParameter("movie_long_text");
        int movieYear1 = 0;
        try {
            
            movieYear1 = Integer.parseInt(request.getParameter("movie_year1"));
        
        } catch (Exception e) {
            movieYear1 = 0;
        }

        Movie movie = this.getRequestedMovie(request);

        if (movieGenre != null && !movieGenre.trim().isEmpty()) {
            try {
                movie.setGenre(this.genreBean.findById(Long.parseLong(movieGenre)));
            } catch (NumberFormatException ex) {
                // Ungültige oder keine ID mitgegeben
            }
        }

        Date dueDate = WebUtils.parseDate(movieDueDate);
        Time dueTime = WebUtils.parseTime(movieDueTime);
     
        
        if (dueDate != null) {
            movie.setDueDate(dueDate);
        } else {
            errors.add("Das Datum muss dem Format dd.mm.yyyy entsprechen.");
        }

        if (dueTime != null) {
            movie.setDueTime(dueTime);
        } else {
            errors.add("Die Uhrzeit muss dem Format hh:mm:ss entsprechen.");
        }

        try {
            movie.setStatus(MovieStatus.valueOf(movieStatus));
        } catch (IllegalArgumentException ex) {
            errors.add("Der ausgewählte Status ist nicht vorhanden.");
        }
        
        movie.setTitel(movieTitel);
        movie.setDirector(movieDirector);
        movie.setYear1(movieYear1);
        movie.setLongText(movieLongText);

        this.validationBean.validate(movie, errors);
        this.validationBean.validateOwner(movie, errors);

        // Datensatz speichern
        if (errors.isEmpty()) {
            this.movieBean.update(movie);
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/movies/list/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("movie_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }

    /**
     * Aufgerufen in doPost: Vorhandenen Film löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void deleteMovie(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Datensatz holen
        Movie movie = this.getRequestedMovie(request);
        
        List<String> errors = new ArrayList<>();
        
        this.validationBean.validateOwner(movie, errors);
        
        if (errors.isEmpty()) {
            // Datensatz löschen
            this.movieBean.delete(movie);
            // Zurück zur Übersicht
            response.sendRedirect(WebUtils.appUrl(request, "/app/movies/list/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("movie_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }

    /**
     * Zu bearbeitende Film aus der URL ermitteln und zurückgeben. Gibt
     * entweder einen vorhandenen Datensatz oder ein neues, leeres Objekt
     * zurück.
     *
     * @param request HTTP-Anfrage
     * @return Zu bearbeitende Aufgabe
     */
    private Movie getRequestedMovie(HttpServletRequest request) {
        // Zunächst davon ausgehen, dass ein neuer Film angelegt werden soll
        Movie movie = new Movie();
        movie.setOwner(this.userBean.getCurrentUser());
        movie.setDueDate(new Date(System.currentTimeMillis()));
        movie.setDueTime(new Time(System.currentTimeMillis()));
        
        // ID aus der URL herausschneiden
        String movieId = request.getPathInfo();

        if (movieId == null) {
            movieId = "";
        }

        movieId = movieId.substring(1);

        if (movieId.endsWith("/")) {
            movieId = movieId.substring(0, movieId.length() - 1);
        }

        // Versuchen, den Datensatz mit der übergebenen ID zu finden
        try {
            movie = this.movieBean.findById(Long.parseLong(movieId));
        } catch (NumberFormatException ex) {
            // Ungültige oder keine ID in der URL enthalten
        }

        return movie;
    }

    /**
     * Neues FormValues-Objekt erzeugen und mit den Daten eines aus der
     * Datenbank eingelesenen Datensatzes füllen. Dadurch müssen in der JSP
     * keine hässlichen Fallunterscheidungen gemacht werden, ob die Werte im
     * Formular aus der Entity oder aus einer vorherigen Formulareingabe
     * stammen.
     *
     * @param movie Die zu bearbeitende Aufgabe
     * @return Neues, gefülltes FormValues-Objekt
     */
    private FormValues createMovieForm(Movie movie) {
        Map<String, String[]> values = new HashMap<>();

        values.put("movie_owner", new String[]{
            movie.getOwner().getUsername()
        });

        if (movie.getGenre() != null) {
            values.put("movie_genre", new String[]{
                "" + movie.getGenre().getId()
            });
        }

        values.put("movie_due_date", new String[]{
            WebUtils.formatDate(movie.getDueDate())
        });

        values.put("movie_due_time", new String[]{
            WebUtils.formatTime(movie.getDueTime())
        });

        values.put("movie_status", new String[]{
            movie.getStatus().toString()
        });

        values.put("movie_titel", new String[]{
            movie.getTitel()
        });
        
        values.put("movie_director", new String[]{
            movie.getDirector()
        });
        
        values.put("movie_year1", new String[]{
            Integer.toString(movie.getYear1())
        });

        values.put("movie_long_text", new String[]{
            movie.getLongText()
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }

}
