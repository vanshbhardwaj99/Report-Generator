package com.reportgenerator.reportgenerator.repository;

import com.reportgenerator.reportgenerator.entity.ReferenceData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferenceDataRepository extends MongoRepository<ReferenceData, String> {

    @Query("{'inputFileId' : ?0, $or: [{'refkey1' : ?1}, {'refkey2' : ?2}]}")
    public List<ReferenceData> findAllByInputFileIdAndRefkey1OrRefkey2(String inputFileId, String refKey1, String refKey2);
}
