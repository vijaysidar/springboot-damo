package com.vijay.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.vijay.model.Student;
import com.vijay.model.StudentFile;
import com.vijay.repository.StudentFileRepository;
import com.vijay.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired private FileUploadPathService fileUploadPathService;
	@Autowired private	StudentFileRepository studentFileRepository;
	@Autowired private StudentRepository studentDao;
	@Autowired private ServletContext context;
	
	@Override
	public List<Student> findAllStudent() {
		
		return studentDao.findAll();
	}
	@Override
	public Student saveStudent(Student student) {
		student.setCreatedDate(new Date());
		Student dbst=studentDao.save(student);
		if(dbst!=null && dbst.getFiles()!=null && dbst.getFiles().size()>0) {
			for(MultipartFile file:dbst.getFiles()) {
				if(file!=null&& StringUtils.hasText(file.getOriginalFilename()));
				String fileName=file.getOriginalFilename();
				String modifiedfileName=FilenameUtils.getBaseName(fileName)+""+System.currentTimeMillis()
				+","+FilenameUtils.getExtension(fileName);
				File storeFile=fileUploadPathService.getFilePath(fileName,"images");
				if(storeFile!=null) {
					try {
						FileUtils.writeByteArrayToFile(storeFile,file.getBytes());
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
				StudentFile studentFile=new StudentFile();
				studentFile.setFileExtension(FilenameUtils.getExtension(fileName));
				studentFile.setFileName(fileName);
				studentFile.setModifiedFileName(modifiedfileName);
				studentFile.setStudent(dbst);
				studentFileRepository.save(studentFile);
			}
		}
		return dbst;
	}
	@Override
	public Student updateStudent(Student student) {
		student.setUpdatedDate(new Date());
		Student db_student=studentDao.save(student);
		
		if(student!=null&& student.getRemoveImages()!=null && student.getRemoveImages().size()>0 ) {
		
	studentFileRepository.deleteByStudentIdAndImageName(student.getId(),student.getRemoveImages());
	System.out.println(student.getRemoveImages().toString());
	   for(String file:student.getRemoveImages()) {
		   File db_files=new File(context.getRealPath("/images/"+File.separator+file));
		   if(db_files.exists()) {
			   db_files.delete();
		   }
	   }
	
		}
		if(db_student!=null && student.getFiles()!=null && student.getFiles().size()>0) {
			for(MultipartFile file:student.getFiles()) {
				
				if(file !=null && StringUtils.hasText(file.getOriginalFilename())) {
					String fileName=file.getOriginalFilename();
				    String modifiedName=FilenameUtils.getBaseName(fileName)+""+System.currentTimeMillis()
				    +","+FilenameUtils.getExtension(fileName);
				    
				  File db_file=  fileUploadPathService.getFilePath(fileName, "images");
				
				  if(db_file!=null) {
					  try {
					  FileUtils.writeByteArrayToFile(db_file, file.getBytes());
				  }catch(Exception e) {
					  e.printStackTrace();
				  }
				  }
				  
		StudentFile student_file=new StudentFile();
		student_file.setFileName(fileName);
		student_file.setModifiedFileName(modifiedName);
		student_file.setFileExtension(FilenameUtils.getExtension(fileName));
		student_file.setStudent(db_student);
		studentFileRepository.save(student_file);
				}
			}
		}
		
		return db_student;
	}
	@Override
	public Student getByStudentId(long sutdentId) {
		
		return studentDao.getOne(sutdentId);
	}
	@Override
	public List<StudentFile> getFilesByStudentId(long sutdentId) {
		
		return studentFileRepository.getFilesByStudentId(sutdentId);
	}
	@Override
	public void deleteById(long studentId) {
		
		studentDao.deleteById(studentId);
	}
	@Override
	public void deleteFileByStudentId(long studentId) {
		studentFileRepository.deleteByStudentId(studentId);
		List<StudentFile> files=studentFileRepository.getFilesByStudentId(studentId);

		for(StudentFile sf:files) {
	  String name=sf.getFileName();
	  System.out.println("hello");
	  File db_files=new File(context.getRealPath("/images/"+File.separator+name));
	   System.out.println(files+""+name);
	  if(db_files.exists()) {
		  db_files.delete();
	   }
	
	}
	}
}
