package com.demo.socialnetwork.services;

import com.demo.socialnetwork.documents.MongoDepartmentBlogs;
import com.demo.socialnetwork.documents.embedded.MongoBlog;
import com.demo.socialnetwork.entities.Department;

public interface DepartmentService {
	
	public Department getDepartmentInfo (int id);
	
	public MongoDepartmentBlogs getDepartmentPosts (String mongoId);
	
	public void addBlog(int departmentId, MongoBlog blog);
	
	public void updateDepartment(Department department);
	
	public void addMember(int departmentId, String username);
	
	public void removeMember(int departmentId, String username);
}
