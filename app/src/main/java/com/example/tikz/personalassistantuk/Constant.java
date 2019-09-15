package com.example.tikz.personalassistantuk;

public class Constant {
    private static String FIRST_NAME;
    private static String LAST_NAME;
    private static String NIM;
    private static String PASSWORD;

    public Constant(String fname, String lname, String nim, String pass) {
        setFirstName(fname);
        setLastName(lname);
        setNIM(nim);
        setPASSWORD(pass);
    }

    public static String getFirstName() {
        return FIRST_NAME;
    }

    public static void setFirstName(String firstName) {
        FIRST_NAME = firstName;
    }

    public static String getLastName() {
        return LAST_NAME;
    }

    public static void setLastName(String lastName) {
        LAST_NAME = lastName;
    }

    public static String getNIM() {
        return NIM;
    }

    public static void setNIM(String NIM) {
        Constant.NIM = NIM;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static void setPASSWORD(String PASSWORD) {
        Constant.PASSWORD = PASSWORD;
    }
}
