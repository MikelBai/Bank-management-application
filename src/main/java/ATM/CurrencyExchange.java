package ATM;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Must import Google Gson
 * Project Structure -> Modules -> main -> + -> Library -> From Maven -> com.google.code.gson:gson:2.8.5
 **/

public class CurrencyExchange implements Serializable {

    private final String myURL;

    private final double margin;

    private final List<String> supportedCurrencies;

    /**
     * Initializes a currency exchange
     */
    public CurrencyExchange() {
        this.margin = 0.00;
        supportedCurrencies = new ArrayList<>();
        addDefaultAvailableCurrency();
        myURL = "https://v3.exchangerate-api.com/pair/e0d0c515c4454ca6e8223f6d";
    }

    /**
     * Adds a newly supported currency
     * @param currencyCode the code for the currency
     */
    private void addSupportedCurrency(String currencyCode) {
        if (!supportedCurrencies.contains(currencyCode)) {
            supportedCurrencies.add(currencyCode);
        }
    }

    /**
     * The currency types available by default
     */
    private void addDefaultAvailableCurrency() {
        addSupportedCurrency("USD");
        addSupportedCurrency("EUR");
        addSupportedCurrency("JPY");
        addSupportedCurrency("GBP");
        addSupportedCurrency("AUD");
        addSupportedCurrency("CNH");
        addSupportedCurrency("CAD");
    }

    /**
     * Returns the list of supported currencies
     * @return the list of supported currencies
     */
    public List<String> getSupportedCurrencies() {
        return supportedCurrencies;
    }

    /**
     * Returns a JsonObject containing relevant info regarding the exchange rates between two currencies
     * @param originalCurrency  the currency being set as the baseline
     * @param convertToCurrency the currency being compared to the original currency
     * @return A JsonObject from the exchange rate API
     */
    private JsonObject getConversionDataObject(String originalCurrency, String convertToCurrency) {
        JsonObject jsonObject;
        try {
            URL url = new URL(myURL + "/" + originalCurrency + "/" + convertToCurrency);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            jsonObject = root.getAsJsonObject();
        } catch (Exception e) {
            return null;
        }
        return jsonObject;
    }

    /**
     * Returns a string representation of the conversion between two currencies
     * @param originalCurrency  the currency being set as the baseline
     * @param convertToCurrency the currency being compared to the original currency
     * @return a string: The conversion rate from XXX to YYY is #.##
     */
    public String getResultsString(String originalCurrency, String convertToCurrency) {
        JsonObject jsonObject = getConversionDataObject(originalCurrency, convertToCurrency);
        assert jsonObject != null;
        return "The conversion rate from " + jsonObject.get("from") +
                " to " + jsonObject.get("to") +
                " is " + jsonObject.get("rate").toString();
    }

    /**
     * Returns the exchange rate between two currencies
     * @param jsonObject the object that the data is extracted from
     * @return a double, the exchange rate
     */
    private double getConversionRate(JsonObject jsonObject) {
        return jsonObject.get("rate").getAsDouble();
    }

    /**
     * Returns the amount of YYY currency if ## amount of XXX currency was exchanged to YYY currency
     * @param originalAmount    the amount of XXX currency
     * @param originalCurrency  the type of the initial currency
     * @param convertToCurrency the type of the currency to be exchanged into
     * @return a double of the resulting amount of YYY currency after the exchange
     */
    private double getConvertedAmount(double originalAmount, String originalCurrency, String convertToCurrency) {
        JsonObject conversionJson = getConversionDataObject(originalCurrency, convertToCurrency);
        assert conversionJson != null;
        double conversionRate = getConversionRate(conversionJson);
        return originalAmount * conversionRate;
    }

    /**
     * Returns how much of the original amount is left after a margin has been deducted
     * @param originalAmount the original amount of money
     * @return a double of the amount after deductions
     */
    private double getAmountAfterMargins(double originalAmount) {
        return originalAmount * (1 - margin);
    }

    /**
     * Returns the actual amount of currency after an exchange at this bank
     * @param originalAmount    the amount of XXX currency
     * @param originalCurrency  the type of the initial currency
     * @param convertToCurrency the type of the currency to be exchanged into
     * @return a double of the resulting amount of YYY currency after the exchange
     */
    public double getActualConvertedAmount(double originalAmount, String originalCurrency, String convertToCurrency) {
        double amountAfterMargins = getAmountAfterMargins(originalAmount);
        return getConvertedAmount(amountAfterMargins, originalCurrency, convertToCurrency);
    }
}
