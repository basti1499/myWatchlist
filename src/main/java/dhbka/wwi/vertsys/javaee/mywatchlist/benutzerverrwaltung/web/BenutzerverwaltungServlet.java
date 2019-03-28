/*
 * Copyright © 2019 Christopher Duerr, Bastian Hartenstein, Nico Schmitt
 * 
 * E-Mail: duerr.christopher@student.dhbw-karlsruhe.de; hartenstein.bastian@student.dhbw-karlsruhe.de; schmitt.nico@student.dhbw-karlsruhe.de
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbka.wwi.vertsys.javaee.mywatchlist.benutzerverrwaltung.web;

import dhbwka.wwi.vertsys.javaee.mywatchlist.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.mywatchlist.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.mywatchlist.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.mywatchlist.common.web.FormValues;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author harte
 */
@WebServlet(name = "BenutzerverwaltungServlet", urlPatterns = {"/app/benutzerverwaltung"})
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
        
        request.setAttribute("user_form", this.createUserForm(user));
        
        request.getRequestDispatcher("/WEB-INF/benutzerverwaltung/benutzerverwaltung.jsp").forward(request, response);
        
//        session.removeAttribute("moviek_form");
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

}
