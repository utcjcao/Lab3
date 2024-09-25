package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// import java.util.Locale;
/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    private Map<String, String> codeToCountryMap = new HashMap<>();
    private Map<String, String> countryToCodeMap = new HashMap<>();
    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */

    public CountryCodeConverter() {
        this("country-codes.txt");
    }
    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */

    public CountryCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));
            for (int i = 1; i < lines.size(); i++) {
                String[] data = lines.get(i).split("\\s+");
                StringBuilder countryName = new StringBuilder();
                int counter = 0;
                while (!(data[counter].equals(data[counter].toUpperCase()))) {
                    countryName.append(data[counter]);
                    countryName.append(" ");
                    counter++;
                }
                String finalCountryName = countryName.toString();
                finalCountryName = finalCountryName.substring(0, finalCountryName.length() - 1);
                counter += 1;
                String alpha3 = data[counter];
                System.out.println(alpha3);
                codeToCountryMap.put(finalCountryName, alpha3);
                countryToCodeMap.put(alpha3, finalCountryName);
            }

            // TODO Task: use lines to populate the instance variable(s)

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        // TODO Task: update this code to use an instance variable to return the correct value
        return codeToCountryMap.get(code);
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        // TODO Task: update this code to use an instance variable to return the correct value
        return countryToCodeMap.get(country);
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        // TODO Task: update this code to use an instance variable to return the correct value
        return countryToCodeMap.size();
    }
}
