package com.mbclandgroup.fitresume.repository.impl;

import com.mbclandgroup.fitresume.model.Candidate;
import com.mbclandgroup.fitresume.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CandidateRepositoryImpl {

    private final CandidateRepository candidateRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public CandidateRepositoryImpl(CandidateRepository candidateRepository, MongoTemplate mongoTemplate) {
        this.candidateRepository = candidateRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public void createData(Candidate candidate){
        candidateRepository.save(candidate);
    }

    public List<Candidate> readData(String fileName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("fileName").is(fileName));
        List<Candidate> result = mongoTemplate.find(query, Candidate.class);
        return result;
    }
}
