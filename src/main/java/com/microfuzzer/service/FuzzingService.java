package com.microfuzzer.service;

import com.microfuzzer.model.FuzzingResult;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.List;

@Service
public class FuzzingService {
    
    public FuzzingResult fixSyntaxErrors(String content) {
        // Validation for 10 to 100,000 characters
        if (content == null || content.length() < 10) {
            return new FuzzingResult(content, "❌ Error: Input too short (minimum 10 characters required).", 0);
        }
        if (content.length() > 100000) {
            return new FuzzingResult(content, "❌ Error: Input too long (maximum 100,000 characters allowed).", 0);
        }

        StringBuilder fixedContent = new StringBuilder(content);
        List<String> errors = new ArrayList<>();
        int errorCount = 0;
        
        ErrorPattern[] patterns = {
            new ErrorPattern("(\\w+)\\s*\\n", "$1;\n", "Missing semicolon"),
            new ErrorPattern(";;", ";", "Extra semicolon"),
            new ErrorPattern("(\\w+)\\s+\\w+\\s*;", "$1();", "Missing parentheses in function call"),
            new ErrorPattern("\"([^\"]*)$", "\"$1\"", "Unclosed double quote"),
            new ErrorPattern("'([^']*)$", "'$1'", "Unclosed single quote"),
            new ErrorPattern("(if|for|while|function)\\s*\\([^)]+\\)\\s*\\n", "$1() {\n", "Missing opening brace"),
            new ErrorPattern(",,\\s*", ", ", "Extra comma"),
            new ErrorPattern("(\\d+)\\s+(\\d+)", "$1 + $2", "Missing operator between numbers"),
            new ErrorPattern("/\\*([^*]*(\\*[^/])?)*$", "/* $1 */", "Unclosed multi-line comment")
        };
        
        for (ErrorPattern pattern : patterns) {
            Pattern p = Pattern.compile(pattern.regex);
            Matcher m = p.matcher(fixedContent.toString());
            StringBuffer sb = new StringBuffer();
            
            while (m.find()) {
                errorCount++;
                int lineNum = getLineNumber(fixedContent.toString(), m.start());
                errors.add("Line " + lineNum + ": " + pattern.errorMessage + " - Fixed: '" + m.group() + "'");
                
                m.appendReplacement(sb, pattern.replacement);
            }
            m.appendTail(sb);
            fixedContent = new StringBuilder(sb.toString());
        }
        
        String errorReport = String.join("\n", errors);
        if (errors.isEmpty()) {
            errorReport = "No syntax errors found!";
        }
        
        return new FuzzingResult(fixedContent.toString(), errorReport, errorCount);
    }
    
    // Fixed this to prevent "Range out of bounds" errors
    private int getLineNumber(String content, int position) {
        if (content == null || position <= 0) return 1;
        int lineCount = 1;
        for (int i = 0; i < position && i < content.length(); i++) {
            if (content.charAt(i) == '\n') lineCount++;
        }
        return lineCount;
    }
    
    private static class ErrorPattern {
        String regex;
        String replacement;
        String errorMessage;
        
        ErrorPattern(String regex, String replacement, String errorMessage) {
            this.regex = regex;
            this.replacement = replacement;
            this.errorMessage = errorMessage;
        }
    }
}