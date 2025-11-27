import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Scanner;

public class VeranstaltungsAnmeldung {

    public static void main(String[] args) {
		System.out.println("Veranstaltungsanmeldung!");

		System.out.print("Das Sprichwort vom " + new SimpleDateFormat("dd.MM.yyyy").format(new Date()) + ": ");

        String[] sprichwoerter = new String[] { "Da beißt die Maus keinen Faden ab.",
				"Der dümmste Bauer erntet die dicksten Kartoffeln.", "Erst die Arbeit, dann das Vergnügen.",
				"Der frühe Vogel fängt den Wurm." };
		int i = ((int) (Math.random() * 10)) % sprichwoerter.length;

		System.out.println(sprichwoerter[i]);
		double fee = Math.random() * 10000; 
		System.out.println("Die Software ist nicht lizensiert. Bitte überweisen Sie " + fee + " €.");
	

        Scanner sc = new Scanner(System.in);
        System.out.println("deutsch französisch englisch");
        String sprache = sc.next();

        System.out.println("Geben Sie bitte Ihren Namen ein: ");
        String name = sc.next();


        System.out.println("Heute, " + DateFormat.getDateInstance(DateFormat.FULL).format(new Date())
                + ", ist für dich " + name + " ein besonderer Tag!");
        System.out.println("Es ist " + String.format("%tR", LocalTime.now()) + " Uhr");

        boolean weiter = true;
        do {
            System.out.print("Bitte geben Sie Ihre Veranstaltungsdaten ein\n");
            System.out.print("Wer ist die Ansprechperson? \n");
            String kontaktPerson = sc.next();

            System.out.println("Ist das eine Frau [ja, nein]? ");
            String geschlecht = sc.next().equals("ja") ? "weiblich" : "männlich";

            if (geschlecht.equals("weiblich")) {
                System.out.println("Wieviele Veranstaltungen hat sie bereits organisiert? ");
            } else {
                System.out.println("Wieviele Veranstaltungen hat er bereits organisiert? ");
            }

            int anzahlEvents = sc.nextInt();

            if (geschlecht.equals("männlich")) {
                System.out.print("Herr " + kontaktPerson);
            } else {
                System.out.print("Frau " + kontaktPerson);
            }

            if (anzahlEvents == 0) {
                System.out.println(" hat bislang keine Veranstaltungen organisiert.");
            } else if (anzahlEvents == 1) {
                System.out.println(" hat bislang eine Veranstaltung organisiert.");
            } else {
                System.out.println(" hat bislang " + anzahlEvents + " Veranstaltungen organisiert. Seine Anzahl an Veranstaltungsorganisationen ist hoch.");
            }

            System.out.print("Wie hoch ist die Teilnahmegebühr?\n");
            double gebuehr = sc.nextDouble();

            System.out.println("Wieviel Rabatt wird gewährt?");
            double rabatt = sc.nextDouble();

            double rabattProzent = rabatt / gebuehr;
            System.out.println("Von der Gebühr in Höhe von " + gebuehr + " € wird ein Rabatt von "
                    + rabattProzent * 100 + " % gewährt.");

            System.out.println(name + ", möchtest du die Sprache wechseln?");
            System.out.print("Gib deutsch französisch englisch oder nein ein:\t");
            sprache = sc.next();
            if (sprache.equals("nein")) {
                weiter = false;
            } else {
                // Sprache wechseln (noch nicht implementiert)
            }
        } while (weiter);
        sc.close();
        System.out.println("Viel Spaß bei den Veranstaltungen. Bis bald!");
    }
}