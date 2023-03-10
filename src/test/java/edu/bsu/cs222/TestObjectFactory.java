package edu.bsu.cs222;

import java.time.LocalDate;

public class TestObjectFactory {

    public static class AstronomicalObjects {

        public static AstronomicalObject buildM13Object() {
            HourCoordinate m13RA = new HourCoordinate(16, 41, 41.24);
            HalfCircleDegreeCoordinate m13Dec = new HalfCircleDegreeCoordinate(36, 27, 35.5);
            RightAscensionDeclinationCoordinates m13Coords = new RightAscensionDeclinationCoordinates(m13RA, m13Dec);
            return new AstronomicalObject("M13", m13Coords);
        }

        public static AstronomicalObject buildM31Object() {
            HourCoordinate m31RA = new HourCoordinate(0,42,44.30);
            HalfCircleDegreeCoordinate m31Dec = new HalfCircleDegreeCoordinate(41,16,9);
            RightAscensionDeclinationCoordinates m31Coords = new RightAscensionDeclinationCoordinates(m31RA,m31Dec);
            return new AstronomicalObject("M31",m31Coords);
        }
    }

    public static class ObjectLists {

        public static ObjectList buildM13M31ObjectList() {
            ObjectList objectList = new ObjectList();
            try {
                objectList.addEntry(new ObjectListEntry(
                        AstronomicalObjects.buildM13Object()
                ));
                objectList.addEntry(new ObjectListEntry(
                        AstronomicalObjects.buildM31Object()
                        , new CompletionStatus(LocalDate.parse("2023-01-01"))
                ));
                return objectList;
            }
            catch (ObjectListEntryAlreadyExistsException e) {
                // Should never happen in this method by construction, so we can throw a RuntimeException instead
                throw new RuntimeException(e);
            }

        }
    }
}
