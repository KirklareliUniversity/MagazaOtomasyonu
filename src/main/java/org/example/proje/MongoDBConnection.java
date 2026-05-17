package org.example.proje;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.concurrent.TimeUnit;

public class MongoDBConnection {
    private static MongoClient mongoClient;
    private static MongoDatabase database;

   
    private static final String URI = "mongodb+srv://veterinerAdmin:62U0ifYGQwEKnLFb@cluster0.gfliqhx.mongodb.net/?appName=Cluster0";
    private static final String DB_NAME = "veterinerAdmin"; 
    public static MongoDatabase connect() {
        if (database == null) {
            try {
                ServerApi serverApi = ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build();

                MongoClientSettings settings = MongoClientSettings.builder()
                        .applyConnectionString(new ConnectionString(URI))
                        .applyToSocketSettings(builder -> 
                            builder.connectTimeout(5000, TimeUnit.MILLISECONDS)
                        )
                        .serverApi(serverApi)
                        .build();

                mongoClient = MongoClients.create(settings);
                database = mongoClient.getDatabase(DB_NAME);
                
                System.out.println("MongoDB bağlantısı başarıyla kuruldu.");
            } catch (Exception e) {
                System.err.println("Bağlantı hatası: " + e.getMessage());
                return null;
            }
        }
        return database;
    }

    public static void close() {
        if (mongoClient != null) {
            try {
                mongoClient.close();
                mongoClient = null;
                database = null;
                System.out.println("Bağlantı kapatıldı.");
            } catch (Exception e) {
                System.err.println("Kapatma hatası: " + e.getMessage());
            }
        }
    }
}