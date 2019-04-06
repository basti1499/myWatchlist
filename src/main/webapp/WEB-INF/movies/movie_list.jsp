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
        Filme
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/movie_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/movies/movie/new/"/>">Film hinzufügen</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/movies/genres/"/>">Genres bearbeiten</a>
        </div>
        
        <div class="menuitem">
            <a href="<c:url value="/app/benutzerverwaltung/"/>">Eigene Daten bearbeiten</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <%-- Suchfilter --%>
        <form method="GET" class="horizontal" id="search">
            <input type="text" name="search_text" value="${param.search_text}" placeholder="Beschreibung"/>

            <select name="search_genre">
                <option value="">Alle Genres</option>

                <c:forEach items="${genres}" var="genre">
                    <option value="${genre.id}" ${param.search_genre == genre.id ? 'selected' : ''}>
                        <c:out value="${genre.name}" />
                    </option>
                </c:forEach>
            </select>

            <select name="search_status">
                <option value="">Alle Stati</option>

                <c:forEach items="${statuses}" var="status">
                    <option value="${status}" ${param.search_status == status ? 'selected' : ''}>
                        <c:out value="${status.label}"/>
                    </option>
                </c:forEach>
            </select>

            <button class="" type="submit">
                Suchen
            </button>
        </form>

        <%-- Gefundene Aufgaben --%>
        <c:choose>
            <c:when test="${empty movies}">
                <p>
                    Es wurden keine Filme gefunden. 🐈
                </p>
            </c:when>
            <c:otherwise>
                <jsp:useBean id="utils" class="dhbwka.wwi.vertsys.javaee.mywatchlist.common.web.WebUtils"/>
                
                <table class="movieList">
                    <thead>
                        <tr>
                            <th>Titel</th>
                            <th>Regisseur</th>
                            <th>Erscheinungsjahr</th>
                            <th>Genre</th>
                            <th>Eigentümer</th>
                            <th>Status</th>
                            <th>Hinzugefügt am</th>
                        </tr>
                    </thead>
                    <c:forEach items="${movies}" var="movie">
                        <tr>
                            <td>
                                <a href="<c:url value="/app/movies/movie/${movie.id}/"/>">
                                    <c:out value="${movie.titel}"/>
                                </a>
                            </td>
                            <td>
                                <c:out value="${movie.director}"/>
                            </td>
                            <td>
                                <c:out value="${movie.year1}"/>
                            </td>
                            <td>
                                <c:out value="${movie.genre.name}"/>
                            </td>
                            <td>
                                <c:out value="${movie.owner.username}"/>
                            </td>
                            <td>
                                <c:out value="${movie.status.label}"/>
                            </td>
                            <td>
                                <c:out value="${utils.formatDate(movie.dueDate)}"/>
                                <c:out value="${utils.formatTime(movie.dueTime)}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</template:base>