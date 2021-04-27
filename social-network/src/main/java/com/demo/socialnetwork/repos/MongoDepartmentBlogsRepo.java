package com.demo.socialnetwork.repos;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.demo.socialnetwork.documents.MongoDepartmentBlogs;
import com.demo.socialnetwork.documents.MongoUserChats;
import com.demo.socialnetwork.documents.embedded.MongoBlog;
import com.demo.socialnetwork.documents.embedded.MongoChat;
import com.demo.socialnetwork.documents.embedded.MongoPersonalMessage;
import com.demo.socialnetwork.entities.UserProfile;

interface MongoDepartmentBlogsUtils {
	
	public void addBlog(String departmentId, MongoBlog blog);
}

class MongoDepartmentBlogsUtilsImpl implements MongoDepartmentBlogsUtils {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public void addBlog(String departmentId, MongoBlog blog) {
		
		blog.setTimestamp(new Date());
		
		Update depUpdate = new Update();
		depUpdate.addToSet("blogs", blog);
		Criteria depCriteria = Criteria.where("id").is(new ObjectId(departmentId));
		mongoTemplate.updateFirst(Query.query(depCriteria), depUpdate, MongoDepartmentBlogs.class);
	}
}


public interface MongoDepartmentBlogsRepo extends MongoRepository<MongoDepartmentBlogs, String>, MongoDepartmentBlogsUtils {

}
