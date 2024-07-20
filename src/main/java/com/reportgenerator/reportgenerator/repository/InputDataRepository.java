package com.reportgenerator.reportgenerator.repository;

import com.reportgenerator.reportgenerator.entity.InputData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InputDataRepository extends MongoRepository<InputData, String> {

    public List<InputData> findAllFileIdDistinctBy();
}
