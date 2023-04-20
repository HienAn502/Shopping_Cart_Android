package com.example.tutorial3.Repository;

import com.example.tutorial3.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee, Long>  {
}
