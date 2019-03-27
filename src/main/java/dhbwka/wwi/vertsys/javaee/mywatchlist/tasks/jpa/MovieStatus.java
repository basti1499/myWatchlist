/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mywatchlist.tasks.jpa;

/**
 * Statuswerte einer Aufgabe.
 */
public enum MovieStatus {
    NOT_STARTED, STARTED, FINISHED;

    /**
     * Bezeichnung ermitteln
     *
     * @return Bezeichnung
     */
    public String getLabel() {
        switch (this) {
            case STARTED:
                return "Angefangen";
            case NOT_STARTED:
                return "Noch nicht Angefangen";
            case FINISHED:
                return "Fertig angeschaut";
            default:
                return this.toString();
        }
    }

}
