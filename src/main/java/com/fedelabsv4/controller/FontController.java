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
@RequestMapping("/fonts")
public class FontController {
    
    private static final String UPLOAD_DIR = "src/main/resources/static/fonts/";
    
    @GetMapping
    public String fontsPage() {
        return "fonts/manage";
    }
    
    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> uploadFont(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        
        if (file.isEmpty()) {
            response.put("success", false);
            response.put("message", "Archivo vacío");
            return ResponseEntity.badRequest().body(response);
        }
        
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        
        if (!extension.matches("\\.(ttf|otf|woff|woff2)")) {
            response.put("success", false);
            response.put("message", "Formato no válido. Solo .ttf, .otf, .woff, .woff2");
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
            response.put("message", "Fuente subida exitosamente");
            response.put("fileName", fileName);
            response.put("fontPath", "/fonts/file/" + fileName);
            
            return ResponseEntity.ok(response);
            
        } catch (IOException e) {
            response.put("success", false);
            response.put("message", "Error al subir archivo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<List<Map<String, String>>> listFonts() {
        List<Map<String, String>> fonts = new ArrayList<>();
        
        File uploadDir = new File(UPLOAD_DIR);
        if (uploadDir.exists() && uploadDir.isDirectory()) {
            File[] files = uploadDir.listFiles((dir, name) -> 
                name.endsWith(".ttf") || name.endsWith(".otf") || 
                name.endsWith(".woff") || name.endsWith(".woff2")
            );
            
            if (files != null) {
                for (File file : files) {
                    Map<String, String> fontInfo = new HashMap<>();
                    String fileName = file.getName();
                    String fontName = fileName.substring(0, fileName.lastIndexOf("."));
                    
                    fontInfo.put("name", fontName);
                    fontInfo.put("fileName", fileName);
                    fontInfo.put("path", "/fonts/file/" + fileName);
                    
                    fonts.add(fontInfo);
                }
            }
        }
        
        return ResponseEntity.ok(fonts);
    }
    
    @GetMapping("/file/{fileName:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFont(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists() && resource.isReadable()) {
                String contentType = "application/octet-stream";
                
                if (fileName.endsWith(".ttf")) {
                    contentType = "font/ttf";
                } else if (fileName.endsWith(".otf")) {
                    contentType = "font/otf";
                } else if (fileName.endsWith(".woff")) {
                    contentType = "font/woff";
                } else if (fileName.endsWith(".woff2")) {
                    contentType = "font/woff2";
                }
                
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/delete/{fileName:.+}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteFont(@PathVariable String fileName) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            File file = filePath.toFile();
            
            if (file.exists() && file.isFile()) {
                if (file.delete()) {
                    response.put("success", true);
                    response.put("message", "Fuente eliminada exitosamente");
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
