package vn.huydtg.immunizationservice.web.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.huydtg.immunizationservice.repository.FileSystemDataRepository;
import vn.huydtg.immunizationservice.repository.PatientRepository;
import vn.huydtg.immunizationservice.repository.UserRepository;
import vn.huydtg.immunizationservice.security.SecurityUtils;
import vn.huydtg.immunizationservice.service.FileSystemDataService;
import vn.huydtg.immunizationservice.web.rest.errors.BadRequestAlertException;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AvatarResource {

    Logger logger = LoggerFactory.getLogger(AvatarResource.class);

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "REST_AVATAR";

    private final FileSystemDataService fileSystemDataService;

    private final FileSystemDataRepository fileSystemDataRepository;

    private final PatientRepository patientRepository;

    private final UserRepository userRepository;

    public AvatarResource(FileSystemDataService fileSystemDataService, FileSystemDataRepository fileSystemDataRepository, PatientRepository patientRepository, UserRepository userRepository) {
        this.fileSystemDataService = fileSystemDataService;
        this.fileSystemDataRepository = fileSystemDataRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/avatar/user")
    public ResponseEntity<?> uploadAvatarForUserToFIleSystem(@RequestParam("image") MultipartFile file) throws IOException {
        UUID currentUserId = SecurityUtils.getUserId();
        assert currentUserId != null;
        if (!userRepository.existsById(currentUserId)) {
            throw new BadRequestAlertException("Not found user", ENTITY_NAME, "usernotexist");
        }
        int uploadImage = fileSystemDataService.uploadImageForUserToFileSystem(file);
        if (uploadImage > 0) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("image uploaded");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("can not upload image");
    }

    @GetMapping("/avatar/{fileName}")
    public ResponseEntity<?> downloadAvatarFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData= fileSystemDataService.downloadImageFromFileSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @PostMapping("/avatar/patient/{patientId}")
    public ResponseEntity<?> uploadAvatarForPatientToFIleSystem(@RequestParam("image") MultipartFile file, @PathVariable UUID patientId) throws IOException {
        logger.info(file.toString());
        if (!patientRepository.existsById(patientId)) {
            throw new BadRequestAlertException("Not found patient", ENTITY_NAME, "patientnotexist");
        }

        int uploadImage = fileSystemDataService.uploadImageForPatientToFileSystem(file, patientId);
        if (uploadImage > 0) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("image uploaded");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("can not upload image");
    }

    @GetMapping("/avatar/by-token")
    public ResponseEntity<?> findAvatarForUserFromFileSystem() throws IOException {
        byte[] imageData= fileSystemDataService.findImageForUserFromFileSystem();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

}
