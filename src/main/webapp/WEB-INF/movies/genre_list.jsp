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
        Genres bearbeiten
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/genre_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/movies/list/"/>">Watchlist</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" class="stacked">
            <%-- CSRF-Token --%>
            <input type="hidden" name="csrf_token" value="${csrf_token}">

            <%-- Feld zum Anlegen einer neuen Kategorie --%>
            <div class="column margin">
                <label for="j_username">Neues Genre:</label>
                <input type="text" name="name" value="${genres_form.values["name"][0]}">

                <button type="submit" name="action" value="create" class="">
                    Hinzufügen
                </button>
            </div>

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty genres_form.errors}">
                <ul class="errors margin">
                    <c:forEach items="${genres_form.errors}" var="error">
                        <li>${error}</li>
                        </c:forEach>
                </ul>
            </c:if>

            <%-- Vorhandene Kategorien --%>
            <c:choose>
                <c:when test="${empty genres}">
                    <p>
                        Es sind noch keine Genres vorhanden. 🐏
                    </p>
                </c:when>
                <c:otherwise>
                    <div>
                        <div class="margin">
                            <c:forEach items="${genres}" var="genre">
                                <input type="checkbox" name="genre" id="${'genre-'.concat(genre.id)}" value="${genre.id}" />
                                <label for="${'genre-'.concat(genre.id)}">
                                    <c:out value="${genre.name}"/>
                                </label>
                                <br />
                            </c:forEach>
                        </div>

                        <button type="submit" name="action" value="delete" class="">
                            Markierte löschen
                        </button>
                    </div>
                </c:otherwise>
            </c:choose>
        </form>
    </jsp:attribute>
</template:base>