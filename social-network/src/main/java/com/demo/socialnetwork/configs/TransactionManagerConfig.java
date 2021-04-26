package com.demo.socialnetwork.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TransactionManagerConfig {
	
	@Bean("mongotrans")
	public MongoTransactionManager mongoTransactionManager(MongoDatabaseFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}
	
	@Bean("jpatrans")
	public JpaTransactionManager jpaTransactionManager() {
		return new JpaTransactionManager();
	}
	
	@Bean("transactionManager")
	public PlatformTransactionManager chainedTransactionManager(
			@Qualifier("mongotrans") MongoTransactionManager tm1, 
			@Qualifier("jpatrans") JpaTransactionManager tm2) {
		ChainedTransactionManager transactionManager = new ChainedTransactionManager(tm1, tm2);
		return transactionManager;
	}
}
