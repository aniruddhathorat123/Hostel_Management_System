package org.aniruddha.hostelMgt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

/**
 * Main class which interact with Firebase DB and perform specified operations on DB.
 * All the methods in this class must be accessed via `FirebaseController.java`.
 */
public class FirebaseService {
    private static final String COLLEGE_REGISTRATION_NUMBER = "college_reg_no";
    private static final String NAME = "name";
    private static final String CONTACT_NUMBER = "contact_no";
    private static final String ROOM_NO = "room_no";
    // Firebase DB object.
    private static Firestore db;

    // class constructor.
    public static FirebaseService getFirebaseService() {
        db = FirestoreClient.getFirestore();
        return new FirebaseService();
    }

    /**
     * Save student info which present in StudentData object.
     * @param collection Name of DB collection.
     * @param studData Student info which need to store into DB.
     * @return Timestamp of insert operation on DB.
     */
    public String saveStudentData(String collection, StudentData studData)
            throws InterruptedException, ExecutionException {
        DocumentReference docRef = db.collection(collection).document(studData.getStudId());
        // Add document data.
        Map<String, Object> data = new HashMap<>();
        data.put(COLLEGE_REGISTRATION_NUMBER, studData.getColRegNo());
        data.put(CONTACT_NUMBER, studData.getContactNo());
        data.put(NAME, studData.getName());
        data.put(ROOM_NO, studData.getRoomNo());
        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
        // wait until operation gets completed.
        // This required to get correct update time.
        while (!result.isDone());
        if (result.isDone()) {
            return result.get().getUpdateTime().toDate().toString();
        } else {
            return result.get().toString();
        }
    }

    /**
     * Gives all the students with their info from the firebase DB.
     * @param collection Name of DB collection.
     * @return List of all students and their info in DB.
     */
    public List<StudentData> getAllStudentsData(String collection) throws InterruptedException,
            ExecutionException {
        // collection of users.
        List<StudentData> studList = new ArrayList<>();
        // asynchronously retrieve all users
        ApiFuture<QuerySnapshot> query = db.collection(collection).get();

        // query.get() blocks on response
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            StudentData stud = new StudentData();
            stud.setStudId(document.getId());
            stud.setName(document.getString(NAME));
            stud.setColRegNo(document.getString(COLLEGE_REGISTRATION_NUMBER));
            stud.setRoomNo(document.getLong(ROOM_NO).intValue());
            stud.setContactNo(document.getLong(CONTACT_NUMBER));
            studList.add(stud);
        }
        return studList;
    }

    /**
     * Return the student info for given student ID.
     * @param collection Name of DB collection.
     * @param searchId Student ID to search student inside the DB.
     * @return Student info having with given Student ID.
     */
    public StudentData getQueryStudentData(String collection, String searchId)
            throws InterruptedException, ExecutionException {
        StudentData stud = new StudentData();
        DocumentReference student = db.collection(collection).document(searchId);
        DocumentSnapshot document = student.get().get();
        stud.setStudId(document.getId());
        stud.setName(document.getString(NAME));
        stud.setColRegNo(document.getString(COLLEGE_REGISTRATION_NUMBER));
        stud.setRoomNo(document.getLong(ROOM_NO).intValue());
        stud.setContactNo(document.getLong(CONTACT_NUMBER));
        return stud;
    }

    /**
     * Remove the student info for given student ID from DB.
     * @param collection Name of DB collection.
     * @param deleteId Student ID to delete that student from the DB.
     * @return Timestamp of delete operation on DB.
     */
    public String deleteQueryStudentData(String collection, String deleteId)
            throws ExecutionException, InterruptedException {
        DocumentReference student = db.collection(collection).document(deleteId);
        ApiFuture<WriteResult> result = student.delete();
        // wait until operation gets completed.
        // This required to get correct update time.
        while (!result.isDone());
        if (result.isDone()) {
            return result.get().getUpdateTime().toDate().toString();
        } else {
            return result.get().toString();
        }
    }
}
