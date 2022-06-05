package pa;

import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        var locale = Locale.getDefault();
        System.out.println("Default locale:" + locale);
        System.out.println("Available locales:");
        Locale[] available =
                Locale.getAvailableLocales();
        for(Locale loc : available) {
            System.out.println(loc.getDisplayCountry() + "\t" + loc.getDisplayLanguage());
        }
    }
}