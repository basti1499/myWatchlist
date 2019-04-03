/*
 * Copyright © 2019 Christopher Duerr, Bastian Hartenstein, Nico Schmitt
 * 
 * E-Mail: duerr.christopher@student.dhbw-karlsruhe.de; hartenstein.bastian@student.dhbw-karlsruhe.de; schmitt.nico@student.dhbw-karlsruhe.de
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbka.wwi.vertsys.javaee.mywatchlist.benutzerverwaltung.web;

import dhbwka.wwi.vertsys.javaee.mywatchlist.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.mywatchlist.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.mywatchlist.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.mywatchlist.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.mywatchlist.common.web.WebUtils;
import java.io.IOException;
import java.io.PrintWriter;
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


@WebServlet(name = "BenutzerverwaltungServlet", urlPatterns = {"/app/benutzerverwaltung/*"})
public class BenutzerverwaltungServlet extends HttpServlet {

    @EJB
    UserBean userBean;
    
    @EJB
    ValidationBean validationBean;
    
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        User user = userBean.getCurrentUser();
        
        if(session.getAttribute("user_form") == null){
            request.setAttribute("user_form", this.createUserForm(user));
        }
       
        
        request.getRequestDispatcher("/WEB-INF/benutzerverwaltung/benutzerverwaltung.jsp").forward(request, response);
        
        session.removeAttribute("user_form");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        this.saveUser(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
    private FormValues createUserForm(User user) {
        Map<String, String[]> values = new HashMap<>();
        
        values.put("username", new String[]{
            user.getUsername()
        });
        
        values.put("vorname", new String[]{
            user.getVorname()
        });
        
        values.put("nachname", new String[]{
            user.getNachname()
        });
        
        values.put("alter", new String[]{
            Integer.toString(user.getAlter())
        });
        
        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }
    
    private void saveUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errors = new ArrayList<>();
        
        String vorname = request.getParameter("vorname");
        String nachname = request.getParameter("nachname");
        int alter = 0;
        try {
            alter = Integer.parseInt(request.getParameter("alter"));
        } catch (Exception e) {
            errors.add("Das Alter darf nicht leer sein!");
        }
        
        
        User user = userBean.getCurrentUser();
        
        
        user.setVorname(vorname);        
        user.setNachname(nachname);
        user.setAlter(alter);
        
        this.validationBean.validate(user, errors);
        
        if (errors.isEmpty()) {
            // Datensatz speichern
            this.userBean.update(user);
            
            // Weiter zur nächsten Seite
            response.sendRedirect(WebUtils.appUrl(request, "/app/dashboard/"));
            
        } else {
            // Fehler: Formular erneut anzeigen
//            FormValues formValues = new FormValues();
//            formValues.setValues(request.getParameterMap());
            FormValues formValues = createUserForm(userBean.getCurrentUser());
            formValues.setErrors(errors);
            
            HttpSession session = request.getSession();
            session.setAttribute("user_form", formValues);
            
            response.sendRedirect(request.getRequestURI());
        }
    }

}
