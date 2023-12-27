package vn.huydtg.immunizationservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface FileSystemDataService {

    Integer uploadImageForUserToFileSystem(MultipartFile file) throws IOException;

    byte[] downloadImageFromFileSystem(String fileName) throws IOException;

    Integer uploadImageForPatientToFileSystem(MultipartFile file, UUID patientId) throws IOException;


    byte[] findImageForUserFromFileSystem() throws IOException;
}
