<%-- 
    Document   : benutzerverwaltung
    Created on : 28.03.2019, 12:16:43
    Author     : harte
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Eigene Daten bearbeiten
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/movie_edit.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>
        
        <div class="menuitem">
            <a href="<c:url value="/app/movies/list/"/>">Liste</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" class="stacked">
            <div class="column">
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">

                <%-- Eingabefelder --%>
                <label for="username">Username<span class="required">*</span></label>
                <div class="side-by-side">
                    <input type="text" name="username" value="${user_form.values["username"][0]}" readonly="readonly">
                </div>

                <label for="vorname">Vorname<span class="required">*</span></label>
                <div class="side-by-side">
                    <input type="text" name="vorname" value="${user_form.values["vorname"][0]}">
                </div>
                
                <label for="nachname">Nachname<span class="required">*</span></label>
                <div class="side-by-side">
                    <input type="text" name="nachname" value="${user_form.values["nachname"][0]}">
                </div>
                
                <label for="alter">Alter<span class="required">*</span></label>
                <div class="side-by-side">
                    <input type="text" name="alter" value="${user_form.values["alter"][0]}">
                </div>

                <%-- Button zum Abschicken --%>
                <div class="side-by-side">
                    <button class="" type="submit" name="action" value="save">
                        Sichern
                    </button>
                </div>
            </div>

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty user_form.errors}">
                <ul class="errors">
                    <c:forEach items="${user_form.errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </form>
    </jsp:attribute>
</template:base>