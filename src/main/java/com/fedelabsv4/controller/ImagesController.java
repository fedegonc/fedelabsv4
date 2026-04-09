package com.fedelabsv4.controller;

import com.fedelabsv4.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/images")
public class ImagesController {
    
    @Autowired
    private CloudinaryService cloudinaryService;
    
    private final List<Map<String, String>> uploadedImages = new ArrayList<>();
    
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
            Map<String, Object> uploadResult = cloudinaryService.uploadImage(file);
            
            String publicId = (String) uploadResult.get("public_id");
            String url = (String) uploadResult.get("secure_url");
            
            Map<String, String> imageInfo = new HashMap<>();
            imageInfo.put("fileName", fileName);
            imageInfo.put("publicId", publicId);
            imageInfo.put("url", url);
            uploadedImages.add(imageInfo);
            
            response.put("success", true);
            response.put("message", "Imagen subida exitosamente a Cloudinary");
            response.put("fileName", fileName);
            response.put("publicId", publicId);
            response.put("url", url);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al subir archivo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<List<Map<String, String>>> listImages() {
        return ResponseEntity.ok(uploadedImages);
    }
    
    @DeleteMapping("/delete/{publicId:.+}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteImage(@PathVariable String publicId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            cloudinaryService.deleteImage(publicId);
            
            uploadedImages.removeIf(img -> publicId.equals(img.get("publicId")));
            
            response.put("success", true);
            response.put("message", "Imagen eliminada exitosamente de Cloudinary");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
