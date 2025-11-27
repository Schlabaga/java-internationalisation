import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Scanner;

public class Bewerbungssystem {

    public static void main(String[] args) {


		System.out.println("Bewerbungssystem!");


		System.out.print("Das Sprichwort vom " + new SimpleDateFormat("dd.MM.yyyy").format(new Date()) + ": ");

        String[] sprichwoerter = new String[] { "Ende gut, alles gut.",
				"Übung macht den Meister.", "Was du heute kannst besorgen, das verschiebe nicht auf morgen.",
				"Aller Anfang ist schwer." };

        int i = ((int) (Math.random() * 10)) % sprichwoerter.length;

        System.out.println(sprichwoerter[i]);

        double fee = Math.random() * 10000;

        System.out.println("Die Software ist nicht lizensiert. Bitte überweisen Sie " + fee + " €.");

		
        Scanner sc = new Scanner(System.in);
        System.out.println("deutsch französisch englisch");
        String sprache = sc.next();

        System.out.println("Geben Sie bitte Ihren Namen ein: ");
        String name = sc.next();

        System.out.println("Welches Geschlecht haben Sie? (m/w): ");
        String geschlecht = sc.next().equals("w") ? "weiblich" : "männlich";

        System.out.println("Welchen Beruf streben Sie an?");
        String beruf = sc.next();

        System.out.println("Wie viele Bewerbungen haben Sie bereits geschrieben?");
        int anzahl = sc.nextInt();

        System.out.println("Welches Gehalt wünschen Sie sich?");
        double gehalt = sc.nextDouble();

        System.out.println("Wie viel Prozent würden Sie bei Verhandlungen akzeptieren?");
        double akzeptanz = sc.nextDouble();

        System.out.println("\n--- Bewerbungsübersicht ---");
        System.out.println("Heute ist " + DateFormat.getDateInstance(DateFormat.FULL).format(new Date()));
        System.out.println("Es ist " + String.format("%tR", LocalTime.now()) + " Uhr");

        if (geschlecht.equals("männlich")) {
            System.out.print("Herr " + name);
        } else {
            System.out.print("Frau " + name);
        }

        System.out.println(" bewirbt sich als " + beruf + ".");
        if (anzahl == 0) {
            System.out.println("Sie haben bisher keine Bewerbungen geschrieben.");
        } else if (anzahl == 1) {
            System.out.println("Sie haben bisher eine Bewerbung geschrieben.");
        } else {
            System.out.println("Sie haben bisher " + anzahl + " Bewerbungen geschrieben. Seine Anzahl ist hoch!");
        }

        System.out.println("Ihr Wunschgehalt beträgt " + gehalt + " €.");
        System.out.println("Sie würden bis zu " + akzeptanz + "% weniger akzeptieren.");

        System.out.println("Möchten Sie die Sprache wechseln?");
        System.out.print("Geben Sie deutsch französisch englisch oder nein ein:\t");
        sprache = sc.next();
        if (!sprache.equals("nein")) {
            // Sprache wechseln (noch nicht implementiert)
        }

        System.out.println("Viel Erfolg bei Ihrer Bewerbung!");
        sc.close();
    }
}