package duan.sportify.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
	public File save(MultipartFile file, String folder, String destinationDir) ;
}
