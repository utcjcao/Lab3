package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    private final JSONArray jsonArray;
    private Map<String, JSONObject> jsonmap = new HashMap<>();

    // TODO Task: pick appropriate instance variables for this class

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     *
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            this.jsonArray = new JSONArray(jsonString);
            this.jsonmap = new HashMap<>();
            for (int i = 0; i < this.jsonArray.length(); i++) {
                JSONObject country = jsonArray.getJSONObject(i);
                jsonmap.put(country.getString("alpha3"), country);
            }
            // TODO Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.

        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // TODO Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        List languages = new ArrayList();
        for (String key : jsonmap.get(country).keySet()) {
            languages.add(key);
        }
        languages.remove("id");
        languages.remove("alpha2");
        languages.remove("alpha3");
        return languages;
    }

    @Override
    public List<String> getCountries() {
        List countries = new ArrayList();
        for (String key : jsonmap.keySet()) {
            countries.add(key);
        }
        return countries;
    }

    @Override
    public String translate(String country, String language) {
        if (jsonmap.containsKey(country)) {
            if (jsonmap.get(country).has(language)) {
                return jsonmap.get(country).getString(language);
            }
        }
        return null;
    }
}
