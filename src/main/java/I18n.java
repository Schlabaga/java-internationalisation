import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Classe utilitaire pour l'internationalisation (I18N).
 * Encapsule l'accès au ResourceBundle et tous les formats dépendants de la Locale.
 */
public class I18n {
    private final ResourceBundle bundle;
    private final Locale locale;
    private final DateFormat dateFormat;
    private final DateFormat timeFormat;
    private final NumberFormat currencyFormat;
    private final NumberFormat percentFormat;

    /**
     * Constructeur qui initialise tous les formats pour une locale donnée.
     * @param locale La locale à utiliser pour les formats et les traductions
     */
    public I18n(Locale locale) {
        this.locale = locale;
        this.bundle = ResourceBundle.getBundle("messages", locale);
        this.dateFormat = DateFormat.getDateInstance(DateFormat.LONG, locale);
        this.timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT, locale);
        this.currencyFormat = NumberFormat.getCurrencyInstance(locale);

        // Forcer le symbole Euro pour les locales européennes
        if (locale.equals(Locale.GERMAN) || locale.equals(Locale.FRENCH)) {
            java.text.DecimalFormatSymbols symbols =
                ((java.text.DecimalFormat) currencyFormat).getDecimalFormatSymbols();
            symbols.setCurrencySymbol("€");
            ((java.text.DecimalFormat) currencyFormat).setDecimalFormatSymbols(symbols);
        } else if (locale.equals(Locale.ENGLISH) || locale.equals(Locale.US)) {
            java.text.DecimalFormatSymbols symbols =
                ((java.text.DecimalFormat) currencyFormat).getDecimalFormatSymbols();
            symbols.setCurrencySymbol("$");
            ((java.text.DecimalFormat) currencyFormat).setDecimalFormatSymbols(symbols);
        }

        this.percentFormat = NumberFormat.getPercentInstance(locale);
        this.percentFormat.setMaximumFractionDigits(2);
    }

    /**
     * Récupère une chaîne traduite à partir de sa clé.
     * @param key La clé de la ressource
     * @return La chaîne traduite
     */
    public String getString(String key) {
        return bundle.getString(key);
    }

    /**
     * Formate un message avec des paramètres variables.
     * @param key La clé du message pattern
     * @param args Les arguments à insérer dans le message
     * @return Le message formaté
     */
    public String formatMessage(String key, Object... args) {
        return MessageFormat.format(bundle.getString(key), args);
    }

    /**
     * Formate une date selon la locale.
     * @param date La date à formater
     * @return La date formatée
     */
    public String formatDate(Date date) {
        return dateFormat.format(date);
    }

    /**
     * Formate une heure selon la locale.
     * @param date La date contenant l'heure à formater
     * @return L'heure formatée
     */
    public String formatTime(Date date) {
        return timeFormat.format(date);
    }

    /**
     * Formate un montant en devise selon la locale.
     * @param amount Le montant à formater
     * @return Le montant formaté avec le symbole de devise approprié
     */
    public String formatCurrency(double amount) {
        return currencyFormat.format(amount);
    }

    /**
     * Formate un pourcentage selon la locale.
     * @param value La valeur en pourcentage (ex: 15 pour 15%)
     * @return Le pourcentage formaté
     */
    public String formatPercent(double value) {
        return percentFormat.format(value / 100.0);
    }

    /**
     * Retourne la locale actuellement utilisée.
     * @return La locale
     */
    public Locale getLocale() {
        return locale;
    }
}