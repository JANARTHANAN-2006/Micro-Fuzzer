package com.microfuzzer.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_files")
public class UserFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String originalFileName;
    
    @Column(columnDefinition = "LONGTEXT")
    private String originalContent;
    
    @Column(columnDefinition = "LONGTEXT")
    private String fixedContent;
    
    @Column(columnDefinition = "LONGTEXT")
    private String errorsFound;
    
    private LocalDateTime uploadDate;
    
    // Constructors
    public UserFile() {}
    
    public UserFile(String username, String originalFileName, String originalContent, 
                    String fixedContent, String errorsFound) {
        this.username = username;
        this.originalFileName = originalFileName;
        this.originalContent = originalContent;
        this.fixedContent = fixedContent;
        this.errorsFound = errorsFound;
        this.uploadDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getOriginalFileName() { return originalFileName; }
    public void setOriginalFileName(String originalFileName) { this.originalFileName = originalFileName; }
    
    public String getOriginalContent() { return originalContent; }
    public void setOriginalContent(String originalContent) { this.originalContent = originalContent; }
    
    public String getFixedContent() { return fixedContent; }
    public void setFixedContent(String fixedContent) { this.fixedContent = fixedContent; }
    
    public String getErrorsFound() { return errorsFound; }
    public void setErrorsFound(String errorsFound) { this.errorsFound = errorsFound; }
    
    public LocalDateTime getUploadDate() { return uploadDate; }
    public void setUploadDate(LocalDateTime uploadDate) { this.uploadDate = uploadDate; }
}