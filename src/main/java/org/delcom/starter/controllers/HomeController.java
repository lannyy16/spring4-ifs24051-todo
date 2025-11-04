package org.delcom.starter.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.*;
// import java.util.stream.Collectors;

@RestController
public class HomeController {

    // == Method Asli dari Kode 1 ==
    @GetMapping("/")
    public String hello() {
        return "Hay Abdullah, selamat datang di pengembangan aplikasi dengan Spring Boot!";
    }

    // == Method Asli dari Kode 1 ==
    @GetMapping("/sayHello/{nama}")
    public String sayHello(@PathVariable String nama) {
        return "Hello, " + nama + "!";
    }

    // == Method Asli dari Kode 1 ==
    @PostMapping("/informasiNim")
    public String informasiNim(@RequestBody String nim) {
        nim = nim.trim();

        // Validasi panjang NIM
        if (nim.length() != 8) {
            return "NIM harus 8 karakter";
        }

        // Ekstrak komponen
        String kodeProdi = nim.substring(0, 3);
        String angkatan = "20" + nim.substring(3, 5);
        String urutan = nim.substring(7);

        String programStudi;

        // Mapping Program Studi
        switch (kodeProdi) {
            case "11S":
                programStudi = "Sarjana Informatika";
                break;
            case "12S":
                programStudi = "Sarjana Sistem Informasi";
                break;
            case "14S":
                programStudi = "Sarjana Teknik Elektro";
                break;
            case "21S":
                programStudi = "Sarjana Manajemen Rekayasa";
                break;
            case "22S":
                programStudi = "Sarjana Teknik Metalurgi";
                break;
            case "31S":
                programStudi = "Sarjana Teknik Bioproses";
                break;
            case "114":
                programStudi = "Diploma 4 Teknologi Rekasaya Perangkat Lunak";
                break;
            case "113":
                programStudi = "Diploma 3 Teknologi Informasi";
                break;
            case "133":
                programStudi = "Diploma 3 Teknologi Komputer";
                break;
            default:
                return "Program Studi tidak Tersedia";
        }

        return String.format("Inforamsi NIM %s: >> Program Studi: %s>> Angkatan: %s>> Urutan: %s",
                nim, programStudi, angkatan, urutan);
    }
    
    // == Method Asli dari Kode 1 ==
    //Perolehan Nilai
    @GetMapping("/perolehanNilai")
    public String perolehanNilai(@RequestParam String strBase64) {
        byte[] decoded = Base64.getDecoder().decode(strBase64);
        String nilai = new String(decoded);
        return "Perolehan Nilai: " + nilai;
    }

    // == Method 'perbedaanL' Diambil dari Kode 2 ==
    // ==============
    @GetMapping("/perbedaanL/{strBase64}")
    public String perbedaanL(@PathVariable String strBase64) {
        try {
            // 1. Dekode Base64
            String inputAsli = new String(Base64.getDecoder().decode(strBase64));
            // 2. Pisahkan input menjadi 3 bagian (misal: "20|20|5")
            String[] parts = inputAsli.trim().split("\\|");

            // 3. Pastikan formatnya benar (3 bagian)
            if (parts.length == 3) {
                int nilaiL = Integer.parseInt(parts[0]);
                int nilaiKebalikanL = Integer.parseInt(parts[1]);
                int nilaiTengah = Integer.parseInt(parts[2]);
                
                // 4. Hitung perbedaan
                int perbedaan = Math.abs(nilaiL - nilaiKebalikanL);
                
                // 5. Tentukan dominan (sesuai output, tampaknya ini adalah nilaiTengah)
                int dominan = nilaiTengah;

                // 6. Kembalikan hasil sesuai format screenshot
                return "Nilai L: " + nilaiL + ": Nilai Kebalikan L: " + nilaiKebalikanL + 
                        ": Nilai Tengah: " + nilaiTengah + " Perbedaan: " + perbedaan + 
                        " Dominan: " + dominan;
            } else {
                return "Error: Format input tidak valid. Harusnya 'angka1|angka2|angka3'.";
            }

        } catch (NumberFormatException e) {
            // Menangani jika string hasil dekode bukan merupakan angka
            return "Error: Input bukan angka yang valid.";
        } catch (IllegalArgumentException e) {
            // Menangani jika string Base64 tidak valid
            return "Error: Format Base64 tidak valid.";
        }
    }
        
    // == Method 'palingTer' Diambil dari Kode 2 ==
    @GetMapping("/palingTer/{strBase64}")
    public String palingTer(@PathVariable String strBase64) {
        try {
            // 1. DECODE Base64
            byte[] decodedBytes = Base64.getDecoder().decode(strBase64);
            String inputAsli = new String(decodedBytes);

            // 2. LOGIKA PROGRAM
            String[] baris = inputAsli.split("\\R");
            
            // Buat array untuk menyimpan angka-angka
            int[] angka = new int[baris.length];
            int count = 0; // Jumlah angka yang valid
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;

            // Loop pertama: Parse angka, hitung min & max
            for (String b : baris) {
                try {
                    int n = Integer.parseInt(b.trim());
                    angka[count] = n; // Simpan angka valid
                    count++;
                    
                    if (n > max) max = n;
                    if (n < min) min = n;
                    
                } catch (NumberFormatException e) {
                    // Abaikan baris yang bukan angka
                }
            }

            // 3. RETURN jika tidak ada angka
            if (count == 0) {
                return "Tidak ada angka yang valid ditemukan.";
            }
            
            // 4. LOGIKA FREKUENSI (Tanpa Map)
            int terbanyakVal = 0;
            int terbanyakCount = 0;
            int tersedikitVal = 0;
            int tersedikitCount = Integer.MAX_VALUE;
            int terendahCount = 0; // Hitung frekuensi angka 'min'

            for (int i = 0; i < count; i++) {
                int currentNum = angka[i];
                int currentCount = 0;
                
                // Cek apakah angka ini sudah pernah dihitung sebelumnya
                boolean seen = false;
                for (int j = 0; j < i; j++) {
                    if (angka[j] == currentNum) {
                        seen = true;
                        break;
                    }
                }
                if (seen) continue; // Lewati jika sudah dihitung

                // Hitung frekuensi 'currentNum'
                for (int j = 0; j < count; j++) {
                    if (angka[j] == currentNum) {
                        currentCount++;
                    }
                }
                
                // Update Terbanyak
                if (currentCount > terbanyakCount) {
                    terbanyakCount = currentCount;
                    terbanyakVal = currentNum;
                }
                
                // Update Tersedikit
                if (currentCount < tersedikitCount) {
                    tersedikitCount = currentCount;
                    tersedikitVal = currentNum;
                }
            }
            
            // Hitung frekuensi angka Terendah (min)
            for (int i = 0; i < count; i++) {
                if (angka[i] == min) {
                    terendahCount++;
                }
            }

            // 5. Hitung Jumlah
            long jumlahTertinggi = (long)max * terbanyakCount;
            long jumlahTerendah = (long)min * terendahCount;

            // 6. Kembalikan hasil sesuai format screenshot
            return "Tertinggi: " + max + " Terendah: " + min + 
                    " Terbanyak: " + terbanyakVal + " (" + terbanyakCount + "x) Tersedikit: " + 
                    tersedikitVal + " (" + tersedikitCount + "x) Jumlah Tertinggi: " + 
                    max + " * " + terbanyakCount + " = " + jumlahTertinggi + 
                    " Jumlah Terendah: " + min + " * " + terendahCount + " = " + jumlahTerendah;
            
        } catch (IllegalArgumentException e) {
             // 4. CATCH INI SEKARANG AKAN BERFUNGSI
            return "Error: Format Base64 tidak valid.";
        }
    }
}