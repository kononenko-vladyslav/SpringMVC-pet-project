package laba17.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class VerifyUtils {

    private static final Logger LOG = LoggerFactory.getLogger(VerifyUtils.class);
    public static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    public static final String SECRET_KEY ="6LeEiyYUAAAAAKKaprocsTPkWwRhwBzD_xVW3q3z";

    public boolean verify( String gRecaptchaResponse) {
        if (gRecaptchaResponse == null || gRecaptchaResponse.length() == 0) {
            return false;
        }

        try {
            URL verifyUrl = new URL(SITE_VERIFY_URL);

            HttpsURLConnection conn = (HttpsURLConnection) verifyUrl.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String postParams = "secret=" + SECRET_KEY + "&response=" + gRecaptchaResponse;

            conn.setDoOutput(true);

            OutputStream outStream = conn.getOutputStream();
            outStream.write(postParams.getBytes());

            outStream.flush();
            outStream.close();

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode=" + responseCode);

            InputStream is = conn.getInputStream();

            JsonReader jsonReader = Json.createReader(is);
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            System.out.println("Response: " + jsonObject);

            boolean success = jsonObject.getBoolean("success");
            return success;
        } catch (Exception e) {
            LOG.error("Catch error", e);
            return false;
        }
    }
}
