package com.fedelabsv4.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/images")
public class ImagesController {
    
    private static final String UPLOAD_DIR = "src/main/resources/static/images/";
    
    @GetMapping
    public String imagesPage() {
        return "images/manage";
    }
    
    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        
        if (file.isEmpty()) {
            response.put("success", false);
            response.put("message", "Archivo vacío");
            return ResponseEntity.badRequest().body(response);
        }
        
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            response.put("success", false);
            response.put("message", "Nombre de archivo inválido");
            return ResponseEntity.badRequest().body(response);
        }
        
        String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        
        if (!extension.matches("\\.(jpg|jpeg|png|gif|webp|svg)")) {
            response.put("success", false);
            response.put("message", "Formato no válido. Solo .jpg, .jpeg, .png, .gif, .webp, .svg");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.write(filePath, file.getBytes());
            
            response.put("success", true);
            response.put("message", "Imagen subida exitosamente");
            response.put("fileName", fileName);
            response.put("imagePath", "/images/" + fileName);
            
            return ResponseEntity.ok(response);
            
        } catch (IOException e) {
            response.put("success", false);
            response.put("message", "Error al subir archivo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<List<Map<String, String>>> listImages() {
        List<Map<String, String>> images = new ArrayList<>();
        
        File uploadDir = new File(UPLOAD_DIR);
        if (uploadDir.exists() && uploadDir.isDirectory()) {
            File[] files = uploadDir.listFiles((dir, name) -> 
                name.toLowerCase().endsWith(".jpg") || 
                name.toLowerCase().endsWith(".jpeg") || 
                name.toLowerCase().endsWith(".png") || 
                name.toLowerCase().endsWith(".gif") ||
                name.toLowerCase().endsWith(".webp") ||
                name.toLowerCase().endsWith(".svg")
            );
            
            if (files != null) {
                for (File file : files) {
                    Map<String, String> imageInfo = new HashMap<>();
                    String fileName = file.getName();
                    
                    imageInfo.put("fileName", fileName);
                    imageInfo.put("path", "/images/" + fileName);
                    imageInfo.put("size", String.format("%.2f KB", file.length() / 1024.0));
                    
                    images.add(imageInfo);
                }
            }
        }
        
        return ResponseEntity.ok(images);
    }
    
    @DeleteMapping("/delete/{fileName:.+}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteImage(@PathVariable String fileName) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            File file = filePath.toFile();
            
            if (file.exists() && file.isFile()) {
                if (file.delete()) {
                    response.put("success", true);
                    response.put("message", "Imagen eliminada exitosamente");
                    return ResponseEntity.ok(response);
                } else {
                    response.put("success", false);
                    response.put("message", "No se pudo eliminar el archivo");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
            } else {
                response.put("success", false);
                response.put("message", "Archivo no encontrado");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
