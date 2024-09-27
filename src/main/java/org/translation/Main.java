package org.translation;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {
    private static String newline = "\n";

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {

        Translator translator = new JSONTranslator();
        // Translator translator = new InLabByHandTranslator();

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    // @SuppressWarnings("checkstyle:EqualsAvoidNull")
    public static void runProgram(Translator translator) {
        while (true) {
            String country = promptForCountry(translator);
            String q = "quit";
            if (q.equals(country)) {
                break;
            }

            CountryCodeConverter countryConverter = new CountryCodeConverter();
            String countryCode = countryConverter.fromCountry(country).toLowerCase();

            String language = promptForLanguage(translator, countryCode);
            if (q.equals(language)) {
                break;
            }

            LanguageCodeConverter languageConverter = new LanguageCodeConverter();
            String languageCode = languageConverter.fromLanguage(language);

            System.out.print(country + " in " + language + " is " + translator.translate(countryCode, languageCode)
                    + "\n");
            System.out.print("Press enter to continue or quit to exit." + newline);
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (q.equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        List<String> countries = translator.getCountries();
        CountryCodeConverter countryConverter = new CountryCodeConverter();

        // country code to country name
        for (int i = 0; i < countries.size(); i++) {
            countries.set(i, countryConverter.fromCountryCode(countries.get(i)));
        }

        // sort country names alphabetically
        Collections.sort(countries);

        StringBuilder countriesStr = new StringBuilder();
        // print countries line by line
        for (int i = 0; i < countries.size() - 1; i++) {
            countriesStr.append(countries.get(i)).append(newline);
        }
        countriesStr.append(countries.get(countries.size() - 1));

        System.out.print(countriesStr + newline);
        System.out.print("select a country from above:" + newline);

        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {
        List<String> languages = translator.getCountryLanguages(country);
        LanguageCodeConverter languageConverter = new LanguageCodeConverter();

        // language code to language name
        for (int i = 0; i < languages.size(); i++) {
            languages.set(i, languageConverter.fromLanguageCode(languages.get(i)));
        }

        // sort languages
        Collections.sort(languages);

        // print languages line by line
        StringBuilder languageStr = new StringBuilder();
        for (int i = 0; i < languages.size() - 1; i++) {
            languageStr.append(languages.get(i)).append(newline);
        }
        languageStr.append(languages.get(languages.size() - 1));

        System.out.print(languageStr + newline);
        System.out.print("select a language from above:" + newline);

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
