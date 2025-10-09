package it.telecom.Authentication.service;


import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;






@Service
public class FileUploadService {
	
	
	@Value("${file.upload.images.path}") 
	String imageUploadPath;
	

	
	//fileclean, get extension, check size, unique image name
	public void handleImageUpload(MultipartFile inputfile) throws Exception {
		
		int maxSize = 5*1024*1024;
		
		
		
		
		
		String filename = StringUtils.cleanPath(inputfile.getOriginalFilename());
		
		String filetype = StringUtils.getFilenameExtension(filename);
		System.out.println(filetype);
				String[] allowedType = {"jpeg", "gif", "jpg"};
		
		boolean isTypeAllowed = Arrays.stream(allowedType).anyMatch(filetype::equals);
		
		if (isTypeAllowed == false) {
			
			throw new Exception( filetype + " file not allowed");
		}
		
		
		if (inputfile.getSize()> maxSize) {
			
			throw new Exception("file size exceeded");
		}
		
		System.out.println(imageUploadPath);
		
		String imageName = UUID.randomUUID().toString() + "." + filetype;
		
		System.out.println(imageName);
		
		Path uploadPath = Paths.get(imageUploadPath + imageName);
		
		Files.copy(inputfile.getInputStream(), uploadPath);
		

		
	}
	
	@Value("${file.upload.pdf.path}") 
	String pdfUploadPath;
	
	//file clean, get extension, validation, size, setpath and copy in folder
	public void handlePdfUpload(MultipartFile inputFile) throws Exception {
		
		int MAX_SIZE = 10*1024*1024;
		

		
		String	filename = StringUtils.cleanPath(inputFile.getOriginalFilename());
		
		
		
		String fileType = StringUtils.getFilenameExtension(filename);
		
		String getFileType = "pdf";
		
		if(getFileType.equals(fileType) == false) {
			throw new Exception( "file type not supported");
			
		}
		if (inputFile.getSize() > MAX_SIZE) {
			throw new Exception("only 10mb allowed");
			
		}
		
		String uploadFileName = UUID.randomUUID()+"."+fileType;
		
		Path uploadPath =Paths.get(pdfUploadPath+uploadFileName);
		
		Files.copy(inputFile.getInputStream(), uploadPath );
		
		
		
		
		
		
		
		
		
		
	}

	
		
	
	
	

}
