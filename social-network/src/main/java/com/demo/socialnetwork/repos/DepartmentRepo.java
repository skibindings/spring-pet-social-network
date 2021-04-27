package com.demo.socialnetwork.repos;

import org.springframework.stereotype.Repository;

import com.demo.socialnetwork.entities.Department;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Integer>{

}
