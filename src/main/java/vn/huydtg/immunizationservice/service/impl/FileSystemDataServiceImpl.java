package vn.huydtg.immunizationservice.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vn.huydtg.immunizationservice.domain.Administrator;
import vn.huydtg.immunizationservice.domain.Customer;
import vn.huydtg.immunizationservice.domain.Employee;
import vn.huydtg.immunizationservice.domain.FileSystemData;
import vn.huydtg.immunizationservice.repository.*;
import vn.huydtg.immunizationservice.security.SecurityUtils;
import vn.huydtg.immunizationservice.service.FileSystemDataService;
import vn.huydtg.immunizationservice.service.mapper.FileSystemDataMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class FileSystemDataServiceImpl implements FileSystemDataService {

    @Value("${app-system-file-folder}" + "image_avatar/")
    private String AVATAR_FOLDER;
    Logger logger = LoggerFactory.getLogger(FileSystemDataServiceImpl.class);

    private FileSystemDataRepository fileSystemDataRepository;

    private FileSystemDataMapper fileSystemDataMapper;

    private PatientRepository patientRepository;

    private CustomerRepository customerRepository;

    private EmployeeRepository employeeRepository;

    private AdministratorRepository administratorRepository;

    public FileSystemDataServiceImpl(FileSystemDataRepository fileSystemDataRepository, FileSystemDataMapper fileSystemDataMapper, PatientRepository patientRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository, AdministratorRepository administratorRepository) {
        this.fileSystemDataRepository = fileSystemDataRepository;
        this.fileSystemDataMapper = fileSystemDataMapper;
        this.patientRepository = patientRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.administratorRepository = administratorRepository;
    }


    @Override
    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<FileSystemData> fileSystemData = fileSystemDataRepository.findByName(fileName);
        if (fileSystemData.isEmpty()) {
            return new byte[0];
        }
        String filePath = fileSystemData.get().getFilePath();
        return Files.readAllBytes(new File(filePath).toPath());
    }

    @Override
    public byte[] findImageForUserFromFileSystem() throws IOException {
        UUID currentId = SecurityUtils.getUserId();
        String fileName = "";

        if(SecurityUtils.hasCurrentUserThisAuthority("ADMINISTRATOR")) {
            Optional<Administrator> administrator = administratorRepository.findFirstByUserId(currentId);
            if (administrator.isPresent()) {
                fileName = administrator.get().getAvatar();
            }
        }
        else if (SecurityUtils.hasCurrentUserAnyOfAuthorities("MANAGER", "PHYSICIAN", "STAFF")) {
            Optional<Employee> employee = employeeRepository.findFirstByUserId(currentId);
            if (employee.isPresent()) {
                fileName = employee.get().getAvatar();
            }
        }
        else if (SecurityUtils.hasCurrentUserThisAuthority("CUSTOMER")) {
            Optional<Customer> customer = customerRepository.findFirstByUserId(currentId);
            if (customer.isPresent()) {
                fileName = customer.get().getAvatar();
            }
        }
        if (fileName.equals("")) {
            return new byte[0];
        }
        Optional<FileSystemData> fileSystemData = fileSystemDataRepository.findByName(fileName);
        if (fileSystemData.isEmpty()) {
            return new byte[0];
        }
        String filePath = fileSystemData.get().getFilePath();
        return Files.readAllBytes(new File(filePath).toPath());
    }

    @Override
    public Integer uploadImageForPatientToFileSystem(MultipartFile file, UUID patientId) throws IOException {
        String avatarName = fileSystemUploadImage(file);
        return patientRepository.updateAvatar(avatarName, patientId);
    }

    @Override
    public Integer uploadImageForUserToFileSystem(MultipartFile file) throws IOException {
        UUID currentId = SecurityUtils.getUserId();
        String avatarName = fileSystemUploadImage(file);
        int recordsUpdated = 0;
        if(SecurityUtils.hasCurrentUserThisAuthority("ADMINISTRATOR")) {
            Optional<Administrator> administrator = administratorRepository.findFirstByUserId(currentId);
            if (administrator.isPresent()) {
                recordsUpdated += administratorRepository.updateAvatar(avatarName, administrator.get().getId());
            }
        }
        if (SecurityUtils.hasCurrentUserAnyOfAuthorities("MANAGER", "PHYSICIAN", "STAFF")) {
            Optional<Employee> employee = employeeRepository.findFirstByUserId(currentId);
            if (employee.isPresent()) {
                recordsUpdated += employeeRepository.updateAvatar(avatarName, employee.get().getId());
            }
        }
        if (SecurityUtils.hasCurrentUserThisAuthority("CUSTOMER")) {
            Optional<Customer> customer = customerRepository.findFirstByUserId(currentId);
            if (customer.isPresent()) {
                recordsUpdated += customerRepository.updateAvatar(avatarName, customer.get().getId());
            }
        }
        return recordsUpdated;
    }

    String fileSystemUploadImage (MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String timestamp = dateFormat.format(new Date());
        String newFileName = timestamp + "_" + originalFileName;
        String filePath = AVATAR_FOLDER + newFileName;
        FileSystemData fileSystemData = new FileSystemData();
        fileSystemData.setName(newFileName);
        fileSystemData.setType(file.getContentType());
        fileSystemData.setFilePath(filePath);
        FileSystemData result  = fileSystemDataRepository.save(fileSystemData);
        file.transferTo(new File(filePath));
        return fileSystemDataMapper.toDto(result).getName();
    }
}
