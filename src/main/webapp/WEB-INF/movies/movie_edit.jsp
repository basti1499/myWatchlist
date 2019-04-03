<%-- 
    Copyright © 2018 Dennis Schulmeister-Zimolong

    E-Mail: dhbw@windows3.de
    Webseite: https://www.wpvs.de/

    Dieser Quellcode ist lizenziert unter einer
    Creative Commons Namensnennung 4.0 International Lizenz.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        <c:choose>
            <c:when test="${edit}">
                Film bearbeiten
            </c:when>
            <c:otherwise>
                Film hinzufügen
            </c:otherwise>
        </c:choose>
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
                <label for="movie_owner">Eigentümer:</label>
                <div class="side-by-side">
                    <input type="text" name="movie_owner" value="${movie_form.values["movie_owner"][0]}" readonly="readonly">
                </div>

                <label for="movie_category">Genre:</label>
                <div class="side-by-side">
                    <select name="movie_category">
                        <option value="">Kein Genre</option>

                        <c:forEach items="${categories}" var="category">
                            <option value="${category.id}" ${movie_form.values["movie_category"][0] == category.id.toString() ? 'selected' : ''}>
                                <c:out value="${category.name}" />
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <label for="movie_due_date">
                    Hinzugefügt am:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="movie_due_date" value="${movie_form.values["movie_due_date"][0]}">
                    <input type="text" name="movie_due_time" value="${movie_form.values["movie_due_time"][0]}">
                </div>

                <label for="movie_status">
                    Status:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side margin">
                    <select name="movie_status">
                        <c:forEach items="${statuses}" var="status">
                            <option value="${status}" ${movie_form.values["movie_status"][0] == status ? 'selected' : ''}>
                                <c:out value="${status.label}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <label for="movie_titel">
                    Titel:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="movie_titel" value="${movie_form.values["movie_titel"][0]}">
                </div>
                
                <label for="movie_director">
                    Regisseur:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="movie_director" value="${movie_form.values["movie_director"][0]}">
                </div>
                
                <label for="movie_year1">
                    Erscheinungsjahr:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="movie_year1" value="${movie_form.values["movie_year1"][0]}">
                </div>

                <label for="movie_long_text">
                    Beschreibung:
                </label>
                <div class="side-by-side">
                    <textarea name="movie_long_text"><c:out value="${movie_form.values['movie_long_text'][0]}"/></textarea>
                </div>

                <%-- Button zum Abschicken --%>
                <div class="side-by-side">
                    <button class="" type="submit" name="action" value="save">
                        Sichern
                    </button>

                    <c:if test="${edit}">
                        <button class="" type="submit" name="action" value="delete">
                            Löschen
                        </button>
                    </c:if>
                </div>
            </div>

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty movie_form.errors}">
                <ul class="errors">
                    <c:forEach items="${movie_form.errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </form>
    </jsp:attribute>
</template:base>