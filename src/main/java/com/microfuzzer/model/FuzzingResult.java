package com.microfuzzer.model;

public class FuzzingResult {
    private String fixedContent;
    private String errors;
    private int errorCount;
    
    public FuzzingResult(String fixedContent, String errors, int errorCount) {
        this.fixedContent = fixedContent;
        this.errors = errors;
        this.errorCount = errorCount;
    }
    
    // Getters and Setters
    public String getFixedContent() { return fixedContent; }
    public void setFixedContent(String fixedContent) { this.fixedContent = fixedContent; }
    
    public String getErrors() { return errors; }
    public void setErrors(String errors) { this.errors = errors; }
    
    public int getErrorCount() { return errorCount; }
    public void setErrorCount(int errorCount) { this.errorCount = errorCount; }
}