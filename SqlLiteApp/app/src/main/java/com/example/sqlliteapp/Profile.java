package com.example.sqlliteapp;

public class Profile {
    int id;
    String Name,surname;
    long marks;

    public Profile() {
        super();
    }
    public Profile(int i, String Name,String surname,long marks) {
        super();
        this.id = i;
        this.Name = Name;
        this.surname = surname;
        this.marks=marks;
    }

    // constructor
    public Profile(String Name,String surname, long marks){
        this.Name = Name;
        this.surname = surname;
        this.marks = marks;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }
    public String getSurname(String surname)
    {
        return surname;
    }
    public void surname(String surname) {
        this.surname = surname;
    }

    public long getMarks(long marks) {
        return marks;
    }
    public void setMarks(long marks) {
        this.marks = marks;
    }

}
