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

import com.demo.socialnetwork.documents.MongoUserChats;
import com.demo.socialnetwork.documents.embedded.MongoChat;
import com.demo.socialnetwork.documents.embedded.MongoPersonalMessage;
import com.demo.socialnetwork.entities.UserProfile;

interface MongoUserChatsUtils {
	
	boolean chatExists(UserProfile user1, UserProfile user2);
	
	void createChat(UserProfile user1, UserProfile user2);
	
	void sendPersonalMessage(UserProfile from, UserProfile to, String text);
}

class MongoUserChatsUtilsImpl implements MongoUserChatsUtils {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public boolean chatExists(UserProfile user1, UserProfile user2) {
		
		Criteria existsCriteria = new Criteria().andOperator(
				Criteria.where("id").is(new ObjectId(user1.getMongoId())),
				Criteria.where("chats.correspUsername").is(user2.getUsername()));
		
	    return mongoTemplate.find(Query.query(existsCriteria), MongoUserChats.class).size() > 0;
	}

	@Override
	public void createChat(UserProfile user1, UserProfile user2) {
		
		MongoChat user1Chat = new MongoChat(user2.getUsername());
		
		Update user1Update = new Update();
		user1Update.addToSet("chats", user1Chat);
		Criteria user1Criteria = Criteria.where("id").is(new ObjectId(user1.getMongoId()));
		mongoTemplate.updateFirst(Query.query(user1Criteria), user1Update, MongoUserChats.class);
		
		MongoChat user2Chat = new MongoChat(user1.getUsername());
		
		Update user2Update = new Update();
		user2Update.addToSet("chats", user2Chat);
		Criteria user2Criteria = Criteria.where("id").is(new ObjectId(user2.getMongoId()));
		mongoTemplate.updateFirst(Query.query(user2Criteria), user2Update, MongoUserChats.class);
	}

	@Override
	public void sendPersonalMessage(UserProfile from, UserProfile to, String text) {

		MongoPersonalMessage fromMessage = new MongoPersonalMessage(false, new Date(), text);
		MongoPersonalMessage toMessage = new MongoPersonalMessage(true, new Date(), text);
		
		Update fromUpdate = new Update();
		fromUpdate.addToSet("chats.$.pms", fromMessage);
		Criteria fromCriteria = new Criteria().andOperator(
				Criteria.where("id").is(new ObjectId(from.getMongoId())),
				Criteria.where("chats.correspUsername").is(to.getUsername()));
		mongoTemplate.updateFirst(Query.query(fromCriteria), fromUpdate, MongoUserChats.class);
		
		Update toUpdate = new Update();
		toUpdate.addToSet("chats.$.pms", toMessage);
		Criteria toCriteria = new Criteria().andOperator(
				Criteria.where("id").is(new ObjectId(to.getMongoId())),
				Criteria.where("chats.correspUsername").is(from.getUsername()));
		mongoTemplate.updateFirst(Query.query(toCriteria), toUpdate, MongoUserChats.class);
	}
}


public interface MongoUserChatsRepo extends MongoRepository<MongoUserChats, String>, MongoUserChatsUtils {

}
