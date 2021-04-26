package com.demo.socialnetwork.documents;

import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import com.demo.socialnetwork.documents.embedded.MongoBlog;
import com.demo.socialnetwork.documents.embedded.MongoChat;

@Document(collation = "departments_blogs")
public class MongoDepartmentBlogs {
	
	@Id
	private String id;
	
	private List<MongoBlog> blogs;
	
	public MongoDepartmentBlogs() {
		
	}

	public MongoDepartmentBlogs(List<MongoBlog> blogs) {
		super();
		this.blogs = blogs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<MongoBlog> getBlogs() {
		return blogs;
	}

	public void setBlogs(List<MongoBlog> blogs) {
		this.blogs = blogs;
	}
}
