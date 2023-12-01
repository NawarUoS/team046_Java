package src.util;

import src.account.BankDetails;

import javax.swing.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashedBankDetailsGenerator {
    private static String salt;

    public static String hashBankDetail(String bankDetail, String userID) {
        try {
            // Create a MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Concatenate the salt and password bytes
            salt = userID;

            byte[] saltedBankDetailBytes = concatenateBytes(salt.getBytes(),
                    String.valueOf(bankDetail).getBytes());

            // Update the digest with the salted password bytes
            md.update(saltedBankDetailBytes);

            // Get the hashed password bytes
            byte[] hashedBankDetailBytes = md.digest();

            // Convert the hashed password bytes to a hexadecimal string
            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte b : hashedBankDetailBytes) {
                hexStringBuilder.append(String.format("%02x", b));
            }
            return hexStringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception, e.g., log it or throw a custom exception
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] concatenateBytes(byte[] arr1, byte[] arr2) {
        byte[] combined = new byte[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, combined, 0, arr1.length);
        System.arraycopy(arr2, 0, combined, arr1.length, arr2.length);
        return combined;
    }

    public static void main(String[] args) {
        long bankDetail = 123123123123213L;
        String salt = "GetsUserID";
        String hashedCardNumber = hashBankDetail(String.valueOf(bankDetail),
                salt);

        System.out.println("Original Card Number: " + bankDetail);
        System.out.println("Hashed Card Number: " + hashedCardNumber);
    }
}
