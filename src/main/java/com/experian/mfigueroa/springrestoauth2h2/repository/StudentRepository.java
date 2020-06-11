package com.experian.mfigueroa.springrestoauth2h2.repository;


import com.experian.mfigueroa.springrestoauth2h2.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	

}
