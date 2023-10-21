package com.mbclandgroup.fitresume.repository;

import com.mbclandgroup.fitresume.model.Candidate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends MongoRepository<Candidate, String> {

}
