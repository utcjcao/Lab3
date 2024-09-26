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

            String language = promptForLanguage(translator, countryConverter.fromCountry(country));
            if (q.equals(language)) {
                break;
            }
            // TODO Task: Once you switch promptForLanguage so that it returns the language
            //            name rather than the 2-letter language code, you will need to
            //            convert it back to its 2-letter language code when calling translate.
            //            Note: you should use the actual names in the message printed below though,
            //            since the user will see the displayed message.

            LanguageCodeConverter languageConverter = new LanguageCodeConverter();

            System.out.println(country + " in " + language + " is "
                    + translator.translate(country, languageConverter.fromLanguage(language)));
            System.out.println("Press enter to continue or quit to exit.");
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

        // print countries line by line
        for (String country : countries) {
            System.out.println(country);
        }

        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {

        // TODO Task: replace the line below so that we sort the languages alphabetically
        //  and print them out; one per line
        // TODO Task: convert the language codes to the actual language names before sorting
        List<String> languages = translator.getCountryLanguages(country);
        LanguageCodeConverter languageConverter = new LanguageCodeConverter();

        // language code to language name
        for (int i = 0; i < languages.size(); i++) {
            languages.set(i, languageConverter.fromLanguageCode(languages.get(i)));
        }

        // sort languages
        Collections.sort(languages);

        // print languages line by line
        for (String language : languages) {
            System.out.println(language);
        }

        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
