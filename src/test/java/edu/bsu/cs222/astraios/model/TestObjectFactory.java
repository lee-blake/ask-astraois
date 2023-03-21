package edu.bsu.cs222.astraios.model;

import java.time.LocalDate;

public class TestObjectFactory {

    public static class AstronomicalObjects {

        public static AstronomicalObject buildM13Object() {
            HourCoordinate m13RightAscension = new HourCoordinate(16, 41, 41.24);
            HalfCircleDegreeCoordinate m13Declination = new HalfCircleDegreeCoordinate(36, 27, 35.5);
            RightAscensionDeclinationCoordinates m13Coords = new RightAscensionDeclinationCoordinates(
                    m13RightAscension,
                    m13Declination
            );
            return new AstronomicalObject("M13", m13Coords);
        }

        public static AstronomicalObject buildM31Object() {
            HourCoordinate m31RightAscension = new HourCoordinate(0,42,44.30);
            HalfCircleDegreeCoordinate m31Declination = new HalfCircleDegreeCoordinate(41,16,9);
            RightAscensionDeclinationCoordinates m31Coords = new RightAscensionDeclinationCoordinates(
                    m31RightAscension,
                    m31Declination
            );
            return new AstronomicalObject("M31",m31Coords);
        }

        public static AstronomicalObject buildM83Object() {
            HourCoordinate m83RightAscension = new HourCoordinate(13,37,00.9);
            HalfCircleDegreeCoordinate m83Declination = new HalfCircleDegreeCoordinate(-29,51,57);
            RightAscensionDeclinationCoordinates m83Coords = new RightAscensionDeclinationCoordinates(
                    m83RightAscension,
                    m83Declination
            );
            return new AstronomicalObject("M83",m83Coords);
        }

    }

    public static class ObjectJournals {

        public static ObjectJournal buildM13M31ObjectJournal() {
            ObjectJournal objectJournal = new ObjectJournal();
            objectJournal.addEntry(new ObjectJournalEntry(
                    AstronomicalObjects.buildM13Object()
            ));
            objectJournal.addEntry(new ObjectJournalEntry(
                    AstronomicalObjects.buildM31Object(),
                    new CompletionStatus(LocalDate.parse("2023-01-01"))
            ));
            return objectJournal;
        }

        public static ObjectJournal buildM13M83ObjectJournal() {
            ObjectJournal objectJournal = new ObjectJournal();
            objectJournal.addEntry(new ObjectJournalEntry(
                    AstronomicalObjects.buildM13Object()
            ));
            objectJournal.addEntry(new ObjectJournalEntry(
                    AstronomicalObjects.buildM83Object(),
                    new CompletionStatus(LocalDate.parse("2023-01-01"))
            ));
            return objectJournal;
        }
    }
}
