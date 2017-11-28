package com.nab.firebase;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;


/**
 *
 * @author nab
 */
public class FireService extends Observable {

    DatabaseReference ref;

    public FireService() {
        try {
            FileInputStream serviceAccount = new FileInputStream("fireapp-upload.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                    .setDatabaseUrl("https://fireapp-upload.firebaseio.com").build();

            // FirebaseApp.initializeApp(options);
            FirebaseApp defaultApp = FirebaseApp.initializeApp(options);

            System.out.println(defaultApp.getName()); // "[DEFAULT]"

            // Retrieve services by passing the defaultApp variable...
            // FirebaseAuth defaultAuth = FirebaseAuth.getInstance(defaultApp);
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance(defaultApp);

            ref = defaultDatabase.getReference("imgs");

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block                              
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void initListening() {
        //EN PRIMERA INSTANCIA TRAE TODAS LAS IMAGENES Y LUEGO LAS QUE SE VAN AGREGANDO.
        ref.addChildEventListener(new ChildEventListener() {
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {

                System.out.println("snapshot " + snapshot);
                System.out.println("previousChildName " + previousChildName);

                try {
                    Img img = snapshot.getValue(Img.class);
                    System.out.println("IMG " + img.toString());

                    setChanged();
                    notifyObservers(img.url);

                    //patron observer.
                    //notificar imagen activa.
                } catch (RuntimeException e) {
                    System.out.println("EXC " + e.getMessage());
                }

            }

            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
            }

            public void onChildRemoved(DataSnapshot snapshot) {
            }

            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
            }

            public void onCancelled(DatabaseError error) {
            }

        }
        );
        // Attach a listener to read the data at our posts reference
        /*ref.addValueEventListener(new ValueEventListener() {

                public void onDataChange(DataSnapshot snapshot) {
                }

                public void onCancelled(DatabaseError error) {
                }                                                                                                                                                                                                                                                                                                                                                                                                   
            });*/

    }
}
