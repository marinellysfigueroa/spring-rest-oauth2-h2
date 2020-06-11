package com.experian.mfigueroa.springrestoauth2h2.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.experian.mfigueroa.springrestoauth2h2.model.Student;
import com.experian.mfigueroa.springrestoauth2h2.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {
	@Autowired
	private StudentRepository studentRepository;
	@GetMapping("/students")
	public List<Student> retrieveAllStudents()
	{
		return studentRepository.findAll();
	}
	@GetMapping("/students/{id}")
	public ResponseEntity<Student> retrieveStudent(@PathVariable long id)  {
		Optional<Student> student = studentRepository.findById(id);
		if (!student.isPresent())
		{
			return new ResponseEntity<Student>((Student) null, HttpStatus.NOT_FOUND);
		}else
		{
			return new ResponseEntity<Student>(student.get(), HttpStatus.OK);
		}
	}
	@DeleteMapping("/students/{id}")
	public HttpEntity<String> deleteStudent(@PathVariable long id) {

		Optional<Student> student = studentRepository.findById(id);

		if (student.isPresent())
		{
			studentRepository.deleteById(id);
			return new ResponseEntity<String>("El estudiante "+student.get().getName()+" "+student.get().getLastName()+" ha sido eliminado", HttpStatus.OK);
		}else
		{
			return new ResponseEntity<String>("No existe el estudiante "+id, HttpStatus.NOT_FOUND);
		}

	}
	@PostMapping("/students")
	public ResponseEntity<String> createStudent(@RequestBody Student student) {

		if (student!=null && student.getLastName()!=null && student.getLastName()!=null && student.getIdentificationNumber()!=null)
		{
			Student savedStudent = studentRepository.save(student);

			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(savedStudent.getId()).toUri();

			ResponseEntity.created(location).build();

			return new ResponseEntity<String>("El estudiante "+student.getName()+" "+student.getLastName()+" ha sido creado", HttpStatus.OK);
		}else
		{
			return new ResponseEntity<String>("Verifique que todos los campos estén completos", HttpStatus.BAD_REQUEST);
		}

	}
	@PutMapping("/students/{id}")
	public HttpEntity<String> updateStudent(@RequestBody Student student, @PathVariable long id) {

		Optional<Student> studentOptional = studentRepository.findById(id);
		if (!studentOptional.isPresent())
			return new ResponseEntity<String>("No existe el estudiante "+id, HttpStatus.NOT_FOUND);
		student.setId(id);
		studentRepository.save(student);
		ResponseEntity.noContent().build();
		return new ResponseEntity<String>("El estudiante "+student.getName()+" "+student.getLastName()+" ha sido modificado", HttpStatus.OK);
	}

}
