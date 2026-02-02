package com.microfuzzer.controller;

import com.microfuzzer.model.FuzzingResult;
import com.microfuzzer.model.UserFile;
import com.microfuzzer.service.FuzzingService;
import com.microfuzzer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class FileController {
    
    @Autowired
    private FuzzingService fuzzingService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file,
                                             @RequestParam("username") String username) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Please select a file", "success", false));
            }
            
            String originalContent = new String(file.getBytes());
            String fileName = file.getOriginalFilename();
            
            FuzzingResult result = fuzzingService.fixSyntaxErrors(originalContent);
            
            // Save to database
            UserFile savedFile = userService.saveFile(username, fileName, originalContent, 
                                                    result.getFixedContent(), result.getErrors());
            
            Map<String, Object> response = new HashMap<>();
            response.put("originalContent", originalContent);
            response.put("fixedContent", result.getFixedContent());
            response.put("errors", result.getErrors());
            response.put("errorCount", result.getErrorCount());
            response.put("fileName", fileName);
            response.put("fileId", savedFile.getId());
            response.put("username", username);
            response.put("success", true);
            
            return ResponseEntity.ok(response);
            
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error processing file: " + e.getMessage(), "success", false));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Unexpected error: " + e.getMessage(), "success", false));
        }
    }
    
    @PostMapping("/fix-text")
    public ResponseEntity<?> fixTextContent(@RequestParam("content") String content,
                                           @RequestParam("username") String username) {
        try {
            if (content == null || content.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Please enter some text", "success", false));
            }
            
            FuzzingResult result = fuzzingService.fixSyntaxErrors(content);
            
            // Save to database
            UserFile savedFile = userService.saveFile(username, "text_input.txt", content, 
                                                    result.getFixedContent(), result.getErrors());
            
            Map<String, Object> response = new HashMap<>();
            response.put("originalContent", content);
            response.put("fixedContent", result.getFixedContent());
            response.put("errors", result.getErrors());
            response.put("errorCount", result.getErrorCount());
            response.put("fileName", "text_input.txt");
            response.put("fileId", savedFile.getId());
            response.put("username", username);
            response.put("success", true);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error processing text: " + e.getMessage(), "success", false));
        }
    }
    
    @GetMapping("/history")
    public ResponseEntity<?> getHistory(@RequestParam("username") String username) {
        try {
            List<UserFile> files = userService.getUserFiles(username);
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error loading history: " + e.getMessage()));
        }
    }
    
    @GetMapping("/file/{id}")
    public ResponseEntity<?> getFile(@PathVariable Long id) {
        try {
            UserFile file = userService.getFileById(id);
            if (file != null) {
                return ResponseEntity.ok(file);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error loading file: " + e.getMessage()));
        }
    }
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Micro Fuzzer is running");
        return ResponseEntity.ok(response);
    }
}