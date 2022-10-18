package com.example.supplychainmanagement;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;

public class login {
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));

    }
    static String getEncryptedPassword(String passwordText) throws NoSuchAlgorithmException{

        try{

            BigInteger number = new BigInteger(1,getSHA(passwordText));
            StringBuilder hexString = new StringBuilder(number.toString(16));
            return hexString.toString();

        }catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }

    public static boolean customerLogin(String email,String password){

        try{
            DatabaseConnection dbcon = new DatabaseConnection();
            String encryptedpass = getEncryptedPassword(password);

            String query = String.format("SELECT * FROM customer WHERE email = '%s' AND password = '%s'",email,encryptedpass);
            ResultSet rs = dbcon.getQueryTable(query);
            if(rs.next()) return true;
        }

        catch(Exception e){
            e.printStackTrace();
        }

        return false;

    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(getEncryptedPassword("saiprasad123"));
        System.out.println(customerLogin("saiprasad@gmail.com","saiprasad123"));
    }
}
