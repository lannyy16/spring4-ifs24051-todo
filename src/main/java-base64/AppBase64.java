package org.delcom.controllers;

import org.springframework.stereotype.Component;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Component
public class AppBase64 {
    
    public void convertFileToBase64(String inputFile, String outputFile) {
        try {
            // Baca semua byte dari file input
            byte[] fileBytes = Files.readAllBytes(Paths.get(inputFile));
            
            // Encode byte ke Base64
            String base64Encoded = Base64.getEncoder().encodeToString(fileBytes);
            
            // Tulis hasil ke file output
            Files.write(Paths.get(outputFile), base64Encoded.getBytes());
            
            System.out.println("File berhasil dikonversi: " + inputFile + " -> " + outputFile);
            System.out.println("Base64 Result: " + base64Encoded);
            
        } catch (Exception e) {
            System.err.println("Error dalam konversi file: " + e.getMessage());
            throw new RuntimeException("Gagal mengkonversi file", e);
        }
    }
    
    public String encodeToBase64(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        }
        return Base64.getEncoder().encodeToString(text.getBytes());
    }
    
    public String decodeFromBase64(String base64Text) {
        if (base64Text == null) {
            throw new IllegalArgumentException("Base64 text cannot be null");
        }
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64Text);
            return new String(decodedBytes);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid Base64 string", e);
        }
    }
    
    public boolean validateBase64(String base64Text) {
        if (base64Text == null || base64Text.isEmpty()) {
            return false;
        }
        try {
            Base64.getDecoder().decode(base64Text);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
    public static void main(String[] args) {
        AppBase64 app = new AppBase64();
        
        // Konversi file plain.txt ke base64
        app.convertFileToBase64("plain.txt", "plain-ke-base64.txt");
        
        // Test encode/decode string
        String originalText = "Hello, World!";
        String encoded = app.encodeToBase64(originalText);
        String decoded = app.decodeFromBase64(encoded);
        
        System.out.println("Original: " + originalText);
        System.out.println("Encoded: " + encoded);
        System.out.println("Decoded: " + decoded);
        System.out.println("Validation: " + app.validateBase64(encoded));
    }
}