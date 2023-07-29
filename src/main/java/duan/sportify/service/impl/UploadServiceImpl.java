package duan.sportify.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import duan.sportify.service.UploadService;
import javax.servlet.ServletContext;
@Service
public class UploadServiceImpl implements UploadService{
	@Autowired
	ServletContext app;
	@Override
	public File save(MultipartFile file, String folder, String destinationDir) {
		// TODO Auto-generated method stub
		
		File sourceDir = new File(app.getRealPath("/user/" + folder));
	    if (!sourceDir.exists()) {
	        sourceDir.mkdirs();
	    }
	    String s = System.currentTimeMillis() + "_" + file.getOriginalFilename();
	    String name = Integer.toHexString(s.hashCode()) + s.substring(s.lastIndexOf("."));

	    try {
	        File sourceFile = new File(sourceDir, name);
	        file.transferTo(sourceFile);

	        File destinationDirFile = new File(destinationDir);
	        if (!destinationDirFile.exists()) {
	            destinationDirFile.mkdirs();
	        }

	        File destinationFile = new File(destinationDirFile, name);
	        Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

	        // In đường dẫn tuyệt đối của tệp đã sao chép vào thư mục đích
	        System.out.println(destinationFile.getAbsolutePath());

	        return destinationFile;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
}
