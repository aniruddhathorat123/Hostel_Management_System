package org.aniruddha.hostelMgt;
import java.io.FileInputStream;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

/**
 * This class is used to initialise Cloud Firestore SDK.
 * This class should instantiate once at time of app starting.
 */
public class FirebaseInit {
    // Credentials to access DB.
    private static final String SERVICE_ACCOUNT_KEY =
            "path_to_service_account_key_json_file";
    private static final String DATABASE_URL = "https://chatapplication-26244.firebaseio.com";
    // class constructor.
    public static FirebaseInit getInstance() {
        return new FirebaseInit();
    }

    public void initialise() {
        try {
            FileInputStream serviceAccount = new FileInputStream(SERVICE_ACCOUNT_KEY);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(DATABASE_URL)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
