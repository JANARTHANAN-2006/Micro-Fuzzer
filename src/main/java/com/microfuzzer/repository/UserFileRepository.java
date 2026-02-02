package com.microfuzzer.repository;

import com.microfuzzer.model.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserFileRepository extends JpaRepository<UserFile, Long> {
    List<UserFile> findByUsernameOrderByUploadDateDesc(String username);
    List<UserFile> findByUsernameAndOriginalFileNameContainingIgnoreCaseOrderByUploadDateDesc(String username, String fileName);
}