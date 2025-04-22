package com.example.lotteon.repository.admin;

import com.example.lotteon.entity.admin.config.ConfigDocument;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminConfigRepository extends MongoRepository<ConfigDocument, UUID> {

}
