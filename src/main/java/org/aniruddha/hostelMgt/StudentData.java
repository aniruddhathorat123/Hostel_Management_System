package org.aniruddha.hostelMgt;

/**
 * Student data class.
 */
public class StudentData {
    private String studId;
    private String name;
    private int roomNo;
    private String colRegNo;
    private Long contactNo;

    public String getStudId() {
        return studId;
    }

    public void setStudId(String studId) {
        this.studId = studId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public String getColRegNo() {
        return colRegNo;
    }

    public void setColRegNo(String colRegNo) {
        this.colRegNo = colRegNo;
    }

    public Long getContactNo() {
        return contactNo;
    }

    public void setContactNo(Long contactNo) {
        this.contactNo = contactNo;
    }
}
