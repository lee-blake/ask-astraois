package edu.bsu.cs222.astraios.model.astronomy;

import edu.bsu.cs222.astraios.model.journal.Header;

import java.util.Map;

public class VisibilityChecker {

    private final AstronomicalObject objectToCheck;
    private final Observation observation;

    public VisibilityChecker(AstronomicalObject objectToCheck, Observation observation) {
        this.objectToCheck = objectToCheck;
        this.observation = observation;
    }

    public boolean objectIsVisible() {
        return !sunInterferes() && !belowTheHorizon();
    }

    private boolean sunInterferes() {
        Sun sun = new Sun();
        return sun.sunInterferesWithViewing(this.observation);
    }

    private boolean belowTheHorizon() {
        return !this.objectToCheck.getAltAzAtObservation(this.observation).isAboveHorizon();
    }

    public String buildVisibilityStatusString() {
        if(objectIsVisible()) {
            return buildStatusStringWhenVisible();
        }
        else {
            return buildStatusStringWhenNotVisible();
        }
    }

    private String buildStatusStringWhenVisible() {
        return buildTwilightWarning() + buildStatusStringHeaders() + buildStatusStringBody();
    }

    private String buildTwilightWarning() {
        if(astronomicalTwilight()) {
            return "Warning: This observation is in astronomical twilight, "
                    + "which may interfere with viewing faint objects.\n";
        }
        return "";
    }

    private boolean astronomicalTwilight() {
        Sun sun = new Sun();
        return sun.isAstronomicalTwilight(this.observation);
    }

    private String buildStatusStringHeaders() {
        return String.format("%-15s   %-10s   %-10s   %-10s   %-10s\n",
                "Name",
                "R.A.",
                "Dec.",
                "Az.",
                "Alt."
        );
    }

    private String buildStatusStringBody() {
        Map<Header,String> bodyValueMap = buildStatusStringBodyValueMap();
        String name = bodyValueMap.get(Header.NAME);
        String rightAscension = bodyValueMap.get(Header.RIGHT_ASCENSION);
        String declination = bodyValueMap.get(Header.DECLINATION);
        String azimuth = bodyValueMap.get(Header.AZIMUTH);
        String altitude = bodyValueMap.get(Header.ALTITUDE);
        return String.format("%-15s   %-10s   %-10s   %-10s   %-10s",
                name,
                rightAscension,
                declination,
                azimuth,
                altitude
        );
    }

    private Map<Header,String> buildStatusStringBodyValueMap() {
        AstronomicalObject.AstronomicalObjectCLIFormatter objectFormatter =
                this.objectToCheck.new AstronomicalObjectCLIFormatter();
        Map<Header,String> bodyValueMap = objectFormatter.getCLIValueMap();
        AzimuthAltitudeCoordinates objectLocation = this.objectToCheck.getAltAzAtObservation(this.observation);
        AzimuthAltitudeCoordinates.AzimuthAltitudeCoordinatesCLIFormatter locationFormatter =
                objectLocation.new AzimuthAltitudeCoordinatesCLIFormatter();
        Map<Header,String> locationValueMap = locationFormatter.getCLIValueMap();
        bodyValueMap.putAll(locationValueMap);
        return bodyValueMap;
    }

    private String buildStatusStringWhenNotVisible() {
        String statusString = "The object '"
                + this.objectToCheck.getName()
                + "' is not visible due to the following reasons:";
        if(sunInterferes()) {
            statusString += "\n-It is neither night nor astronomical twilight.";
        }
        if(belowTheHorizon()) {
            statusString += "\n-The object is below the horizon.";
        }
        return statusString;
    }
}
