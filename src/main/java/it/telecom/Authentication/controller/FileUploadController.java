package it.telecom.Authentication.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.telecom.Authentication.service.FileUploadService;

@RestController
public class FileUploadController {
	
	@Autowired
	private FileUploadService fileUploadService;
	
	
	@PostMapping("/images")
	public ResponseEntity<Map<String, String>> uploadImages(@RequestParam("file") MultipartFile inputfile) throws Exception{
		
		fileUploadService.handleImageUpload(inputfile);
		Map<String, String> response = new HashMap<String, String>();

		response.put("result", "success");
		response.put("message", "uploaded images");
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	@PostMapping("/pdf")
	public ResponseEntity<Map<String, String>> uploadPdfFiles(@RequestParam("pdf") MultipartFile inputfile) throws Exception{
		
		fileUploadService.handlePdfUpload(inputfile);
		Map<String, String> response = new HashMap<String, String>();

		response.put("result", "success");
		response.put("message", "uploaded images");
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	

}
