package com.microfuzzer.service;

import com.microfuzzer.model.UserFile;
import com.microfuzzer.repository.UserFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserFileRepository userFileRepository;
    
    public UserFile saveFile(String username, String fileName, String originalContent, 
                           String fixedContent, String errors) {
        UserFile userFile = new UserFile(username, fileName, originalContent, fixedContent, errors);
        return userFileRepository.save(userFile);
    }
    
    public List<UserFile> getUserFiles(String username) {
        return userFileRepository.findByUsernameOrderByUploadDateDesc(username);
    }
    
    public List<UserFile> searchUserFiles(String username, String fileName) {
        return userFileRepository.findByUsernameAndOriginalFileNameContainingIgnoreCaseOrderByUploadDateDesc(username, fileName);
    }
    
    public UserFile getFileById(Long id) {
        return userFileRepository.findById(id).orElse(null);
    }
}