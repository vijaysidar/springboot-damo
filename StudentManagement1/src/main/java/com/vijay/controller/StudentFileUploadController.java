package com.vijay.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vijay.model.Student;
import com.vijay.model.StudentFile;
import com.vijay.service.StudentService;

@Controller
public class StudentFileUploadController {
	
	@Autowired private StudentService  studentService;
	
@GetMapping("/")
public String getStudent(Model m) {
	 List<Student>  students=studentService.findAllStudent();
	    m.addAttribute("students",students);
		m.addAttribute("student",new Student());
		m.addAttribute("studentFiles",new ArrayList<StudentFile>());
		m.addAttribute("isAdd",true);
	return "view/student";
}
@PostMapping(value="/save")
public String saveStudent(@ModelAttribute Student student,RedirectAttributes redirectAttributes,Model m) {
	
	Student st=studentService.saveStudent(student);
	if(st!=null) {
		redirectAttributes.addFlashAttribute("successmessage","successfully inserted....");
		return "redirect:/";
	}
	else {
		m.addAttribute("error","sorry try again..");
		m.addAttribute("student",student);
		return "view/student";
	}
	
	
}
@PostMapping(value="/update")
public String updateStudent(@ModelAttribute Student student,RedirectAttributes redirectAttributes,Model m) {
	
	Student st=studentService.updateStudent(student);
	if(st!=null) {
		redirectAttributes.addFlashAttribute("successmessage","successfully inserted....");
		return "redirect:/";
	}
	else {
		m.addAttribute("error","sorry try again..");
		m.addAttribute("student",student);
		return "view/student";
	}
	
	
}
@GetMapping(value = "/editstudent/{studentId}")
public String editStudent(@PathVariable long studentId ,Model m) {
	
	Student student=studentService.getByStudentId(studentId);
	List<StudentFile> student_files=studentService.getFilesByStudentId(studentId);
	List<Student> students=studentService.findAllStudent();
	m.addAttribute("studentFiles", student_files);
	m.addAttribute("student", student);
	m.addAttribute("students", students);
	m.addAttribute("isAdd", false);
	return "view/student";
}
@GetMapping(value ="/deletestudent/{studentId}")
public String delete(@PathVariable long studentId,RedirectAttributes redirectAttributes) {
	
	studentService.deleteById(studentId);
	studentService.deleteFileByStudentId(studentId);
	redirectAttributes.addFlashAttribute("successmessage", "student deleted successfully..");
	return "redirect:/";

}
}
