import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class AufgabenManagement {

    // --- 1. Encapsulation propre de l'I18N ---
    static class I18nManager {
        private ResourceBundle bundle;
        private Locale locale;

        public I18nManager(Locale locale) {
            setLocale(locale);
        }

        public void setLocale(Locale locale) {
            this.locale = locale;
            this.bundle = ResourceBundle.getBundle("messages", locale);
        }

        public Locale getLocale() {
            return locale;
        }

        public String get(String key) {
            try {
                return bundle.getString(key);
            } catch (MissingResourceException e) {
                return "!" + key + "!";
            }
        }

        // Pour les phrases à trous ({0}, {1}...)
        public String format(String key, Object... args) {
            return MessageFormat.format(get(key), args);
        }

        // Formats dépendant de la Locale (Dates, Monnaie, etc.)
        public String formatDate(Date date) {
            return DateFormat.getDateInstance(DateFormat.MEDIUM, locale).format(date);
        }

        public String formatTime(LocalTime time) {
            return DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(locale).format(time);
        }

        public String formatCurrency(double amount) {
            return NumberFormat.getCurrencyInstance(locale).format(amount);
        }

        public String formatPercent(double value) {
            // Diviser par 100 car NumberFormat attend 0.1 pour 10%
            return NumberFormat.getPercentInstance(locale).format(value / 100.0);
        }
    }

    // --- 2. Utilisation d'Enums pour la logique (Plus de texte magique) ---
    enum Gender {
        MALE, FEMALE
    }

    public static void main(String[] args) throws IOException {
        Locale locale = Locale.getDefault();

        // Gestion simplifiée des arguments et de la config
        if (args.length > 0) {
            locale = parseLocaleCode(args[0]);
        } else {
            try (FileInputStream fis = new FileInputStream("config.properties")) {
                Properties props = new Properties();
                props.load(fis);
                String savedLang = props.getProperty("language");
                if (savedLang != null) locale = parseLocaleCode(savedLang);
            } catch (IOException e) {
                // Pas de config, on reste sur défaut
            }
        }

        // Initialisation du manager
        I18nManager i18n = new I18nManager(locale);
        Scanner sc = new Scanner(System.in);

        // --- Début du programme ---
        System.out.println(i18n.get("app.title"));

        // Date formatée proprement
        System.out.print(i18n.format("proverb.intro", i18n.formatDate(new Date())) + " ");

        // Choix aléatoire du proverbe
        String[] sprichwoerter = {
            i18n.get("proverb.1"), i18n.get("proverb.2"),
            i18n.get("proverb.3"), i18n.get("proverb.4")
        };
        System.out.println(sprichwoerter[new Random().nextInt(sprichwoerter.length)]);

        // Monnaie formatée proprement
        double fee = Math.random() * 10000;
        System.out.println(i18n.format("license.message", i18n.formatCurrency(fee)));

        // --- Saisie utilisateur ---
        // On ne demande plus la langue ici pour simplifier le flux, ou on le ferait via un menu numéroté.

        System.out.println(i18n.get("input.name"));
        String name = sc.next();

        System.out.println(i18n.get("input.gender"));
        String genderInput = sc.next();
        // Logique découplée du texte : on mappe l'entrée 'w' ou autre vers l'Enum
        Gender gender = (genderInput.equalsIgnoreCase("w") || genderInput.equalsIgnoreCase("f"))
                        ? Gender.FEMALE : Gender.MALE;

        System.out.println(i18n.get("input.area"));
        String bereich = sc.next();

        System.out.println(i18n.get("input.tasks.count"));
        int anzahl = sc.nextInt();

        System.out.println(i18n.get("input.payment"));
        double verguetung = sc.nextDouble();

        System.out.println(i18n.get("input.discount"));
        double rabat = sc.nextDouble();

        // --- Affichage ---
        System.out.println(i18n.get("overview.title"));
        System.out.println(i18n.get("overview.today") + " " + i18n.formatDate(new Date()));
        // Heure formatée (plus de "+ Uhr")
        System.out.println(i18n.format("overview.time", i18n.formatTime(LocalTime.now())));

        // Gestion du titre via Enum et MessageFormat (pas de concaténation)
        String titleKey = (gender == Gender.MALE) ? "title.mr" : "title.mrs";
        String title = i18n.get(titleKey);

        // ATTENTION: J'ai ajouté une clé virtuelle 'format.name_display' ici pour éviter "title + name"
        // Tu devras ajouter dans tes properties: format.name_display={0} {1}
        System.out.println(i18n.format("format.name_display", title, name));

        // Résumé
        System.out.println(i18n.format("summary.area", title, name, bereich));

        if (anzahl == 0) {
            System.out.println(i18n.get("summary.tasks.none"));
        } else if (anzahl == 1) {
            System.out.println(i18n.get("summary.tasks.one"));
        } else {
            System.out.println(i18n.format("summary.tasks.many", anzahl));
        }

        System.out.println(i18n.format("summary.payment", i18n.formatCurrency(verguetung)));
        // Formatage pourcentage correct
        System.out.println(i18n.format("summary.discount", rabat));

        // --- Changement de langue (Logique corrigée) ---
        System.out.println(i18n.get("language.switch.prompt"));
        // Utilisation d'un choix numérique pour éviter de parser "nein/non/no"
        System.out.println("1: Deutsch, 2: Français, 3: English, 0: " + i18n.get("word.no"));

        String choice = sc.next();
        String langCode = null;

        switch (choice) {
            case "1": langCode = "de"; break;
            case "2": langCode = "fr"; break;
            case "3": langCode = "en"; break;
            default: langCode = null; // 0 ou autre = on ne change rien
        }

        if (langCode != null) {
            Properties props = new Properties();
            props.setProperty("language", langCode);
            try (FileOutputStream fos = new FileOutputStream("config.properties")) {
                props.store(fos, "User Language Config");
                // On recharge le manager pour dire au revoir dans la nouvelle langue
                i18n.setLocale(parseLocaleCode(langCode));
                System.out.println(i18n.format("language.saved", langCode));
            }
        }

        System.out.println(i18n.get("goodbye"));
        sc.close();
    }

    private static Locale parseLocaleCode(String code) {
        switch (code.toLowerCase()) {
            case "de":
                return Locale.GERMANY;
            case "fr":
                return Locale.FRANCE;
            case "en":
                return Locale.US;
            default:
                return Locale.getDefault();
        }
    }
}