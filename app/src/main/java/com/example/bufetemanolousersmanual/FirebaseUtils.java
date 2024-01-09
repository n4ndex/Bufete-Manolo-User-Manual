package com.example.bufetemanolousersmanual;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseUtils {

    private static FirebaseFirestore firestore;

    public static void initializeFirebase(Context context) {
        try {
            FirebaseApp.initializeApp(context);
            firestore = FirebaseFirestore.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error initializing Firebase", Toast.LENGTH_SHORT).show();
        }
    }

    public static void readData(String collection, String document, OnSuccessListener<DocumentSnapshot> onSuccessListener, OnFailureListener onFailureListener) {
        if (firestore != null) {
            firestore.collection(collection)
                    .document(document)
                    .get()
                    .addOnSuccessListener(onSuccessListener)
                    .addOnFailureListener(onFailureListener);
        }
    }

    /*
    public static void writeData(String collection, String document, Object data, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        if (firestore != null) {
            firestore.collection(collection)
                    .document(document)
                    .set(data)
                    .addOnSuccessListener(onSuccessListener)
                    .addOnFailureListener(onFailureListener);
        }
    }

        public static FirebaseFirestore getFirestore() {
        return firestore;
    }

    public static void readCollection(String collection, OnSuccessListener<QuerySnapshot> onSuccessListener, OnFailureListener onFailureListener) {
        if (firestore != null) {
            firestore.collection(collection)
                    .get()
                    .addOnSuccessListener(onSuccessListener)
                    .addOnFailureListener(onFailureListener);
        }
    }
     */
}