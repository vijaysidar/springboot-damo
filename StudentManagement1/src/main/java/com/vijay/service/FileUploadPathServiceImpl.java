package com.vijay.service;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class FileUploadPathServiceImpl implements FileUploadPathService {

	@Autowired private ServletContext context;
	@Override
	public File getFilePath(String fileName, String path) {
		Boolean exist=new File(context.getRealPath("/"+path+"/")).exists();
		System.out.println("hello");
		if(!exist) {
			new File(context.getRealPath("/"+path+"/")).mkdir();
		}
		String modifiedFileName=context.getRealPath("/"+path+"/"+File.separator+fileName);
		//sSystem.out.println(modifiedFileName);
		File f=new File(modifiedFileName);
		return f;
	}

}
