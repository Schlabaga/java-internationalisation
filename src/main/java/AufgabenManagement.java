import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

public class AufgabenManagement {

    public static void main(String[] args) throws IOException {

        Locale locale = null;
        String title;

        if (args.length > 0) {
            String lang = args[0];
            switch (lang) {
                case "deutsch":
                case "de":
                    locale = Locale.GERMAN;
                    break;
                case "francais":
                case "français":
                case "fr":
                    locale = Locale.FRENCH;
                    break;
                case "english":
                case "en":
                    locale = Locale.ENGLISH;
                    break;
            }
        }

        if (locale == null) {
            try {
                Properties properties = new Properties();
                properties.load(new FileInputStream("config.properties"));
                String savedLang = properties.getProperty("language");
                if (savedLang != null) {
                    switch (savedLang) {
                        case "de":
                            locale = Locale.GERMAN;
                            break;
                        case "fr":
                            locale = Locale.FRENCH;
                            break;
                        case "en":
                            locale = Locale.ENGLISH;
                            break;
                    }
                }
            } catch (IOException e) {
                // File doesnt exist
            }
        }

        // wenn kein Argument / kein config

        if (locale == null) {
            locale = Locale.getDefault();
        }

        NumberFormat currency = NumberFormat.getCurrencyInstance(locale);

        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);


        System.out.println(bundle.getString("app.title"));
        System.out.print(bundle.getString("proverb.intro") + new SimpleDateFormat("dd.MM.yyyy").format(new Date()) + ": ");
        String[] sprichwoerter = new String[] { bundle.getString("proverb.1"),
                bundle.getString("proverb.2"), bundle.getString("proverb.3"),
                bundle.getString("proverb.4") };

        int i = ((int) (Math.random() * 10)) % sprichwoerter.length;

        System.out.println(sprichwoerter[i]);
        double fee = Math.random() * 10000;
        System.out.println(bundle.getString("license.message") + fee + " €.");

        Scanner sc = new Scanner(System.in);
        System.out.println(bundle.getString("language.prompt"));
        String sprache = sc.next();

        System.out.println(bundle.getString("input.name"));
        String name = sc.next();

        System.out.println(bundle.getString("input.gender"));
        String geschlecht = sc.next().equals("w") ? "weiblich" : "männlich";

        System.out.println(bundle.getString("input.area"));
        String bereich = sc.next();

        System.out.println(bundle.getString("input.tasks.count"));
        int anzahl = sc.nextInt();

        System.out.println(bundle.getString("input.payment"));
        double verguetung = sc.nextDouble();

        System.out.println(bundle.getString("input.discount"));
        double rabat = sc.nextDouble();

        System.out.println(bundle.getString("overview.title"));
        System.out.println(bundle.getString("overview.today") + new Date());
        System.out.println(bundle.getString("overview.time") + LocalTime.now() + " Uhr");

        if (geschlecht.equals("männlich")) {
            title = bundle.getString("title.mr");
            System.out.print(bundle.getString("title.mr") + name);
        } else {
            title = bundle.getString("title.mrs");
            System.out.print(bundle.getString("title.mrs") + name);
        }


        // JE SUIS ICI


        String domain = MessageFormat.format(
            bundle.getString("summary.area"),
            title,
            name,
            bereich
        );


        System.out.println(domain);
        if (anzahl == 0) {
            System.out.println(bundle.getString("summary.tasks.none"));
        } else if (anzahl == 1) {
            System.out.println(bundle.getString("summary.tasks.one"));
        } else {
            String manyTasks = MessageFormat.format(
                    bundle.getString("summary.tasks.many"),
                    anzahl
            );
            System.out.println(manyTasks);
        }

        String salary = MessageFormat.format(
                bundle.getString("summary.payment"),
                verguetung,
                currency
        );



        System.out.println(salary);

        String offer = MessageFormat.format(
                bundle.getString("summary.discount"),
                rabat
        );
        System.out.println(offer);

        System.out.println(bundle.getString("language.switch.prompt"));
        System.out.print(bundle.getString("language.switch.input"));
        sprache = sc.next();
        if (!sprache.equals(bundle.getString("word.no"))) {
            // Sauvegarder la nouvelle langue dans config.properties
            String langCode = null;
            switch (sprache.toLowerCase()) {
                case "deutsch":
                    langCode = "de";
                    break;
                case "französisch":
                case "francais":
                    langCode = "fr";
                    break;
                case "englisch":
                case "english":
                    langCode = "en";
                    break;
            }


            if (langCode != null) {
                String savedLanguage = MessageFormat.format(bundle.getString("language.saved"),
                        langCode);
                Properties properties = new Properties();
                properties.setProperty("language", langCode);
                properties.store(new FileOutputStream("config.properties"),
                               "Dernière langue utilisée");
                System.out.println(savedLanguage);
            }
        }

        System.out.println(bundle.getString("goodbye"));
        sc.close();
    }
}