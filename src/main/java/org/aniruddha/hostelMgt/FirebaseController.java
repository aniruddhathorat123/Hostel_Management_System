package org.aniruddha.hostelMgt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller class used to provide methods to perform operations on Firebase DB.
 * This class should handle all exceptions which may generate from 'FirebaseService.java'.
 * This class is also responsible to instantiate the `FirebaseInit.java`
 */
public class FirebaseController {
    public static final String STATUS = "status";
    public static final String MESSAGE = "msg";
    public static final String RESPONSE = "resp";

    private static final String COLLECTION_NAME = "students";
    private static FirebaseService service;

    // class constructor.
    public static FirebaseController getInstance() {
        FirebaseInit init = FirebaseInit.getInstance();
        init.initialise();
        service =  FirebaseService.getFirebaseService();
        return new FirebaseController();
    }

    /**
     * Store or update the data into collection.
     * @param studData Student object contains details of student to ave in DB.
     * @return status code true: success, false: failure and one string message.
     */
    public Map<String, Object> saveData(StudentData studData) {
        Map<String, Object> response = new HashMap<>();
        try {
            String ts = service.saveStudentData(COLLECTION_NAME, studData);
            response.put(STATUS, true);
            response.put(MESSAGE, ts);
            return response;
        } catch(Exception e) {
            e.printStackTrace();
            response.put(STATUS, false);
            response.put(MESSAGE, "Error while saving data : " + e);
            return  response;
        }
    }

    /**
     * Retrieve data from collection.
     * @return List of all students and their details.
     */
    public List<StudentData> retrieveData() {
        List<StudentData> studList = new ArrayList<>();
        try {
            studList = service.getAllStudentsData(COLLECTION_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studList;
    }

    /**
     * Retrieve student data as per user requested.
     * @param searchId student ID parameter to search inside the DB.
     * @return status code true: success, false: failure, and searched student details.
     */
    public Map<String, Object> retrieveQueryData(String searchId) {
        Map<String, Object> response = new HashMap<>();
        try {
            StudentData data =  service.getQueryStudentData(COLLECTION_NAME, searchId);
            response.put(RESPONSE, data);
            response.put(STATUS, true);
        } catch (Exception e) {
            response.put(STATUS, false);
            throw new RuntimeException(e);
        }
        return response;
    }

    /**
     * Delete student data as per user requested.
     * @param deleteId Student ID to delete that student from the DB.
     * @return status code true: success, false: failure, and timestamp of delete operation.
     */
    public Map<String, Object> deleteQueryData(String deleteId) {
        Map<String, Object> response = new HashMap<>();
        try {
            String timeStamp = service.deleteQueryStudentData(COLLECTION_NAME, deleteId);
            response.put(STATUS, true);
            response.put(MESSAGE, timeStamp);
        } catch (Exception e) {
            response.put(STATUS, false);
            throw new RuntimeException(e);
        }
        return response;
    }
}
