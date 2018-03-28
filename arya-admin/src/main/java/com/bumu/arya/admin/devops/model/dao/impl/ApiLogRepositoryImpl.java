package com.bumu.arya.admin.devops.model.dao.impl;

import com.bumu.arya.admin.devops.model.dao.ApiLogRepository;
import com.bumu.arya.admin.devops.model.entity.ApiLogDocument;
import com.github.swiftech.swifttime.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Component
public class ApiLogRepositoryImpl implements ApiLogRepository {

    private static Logger logger = LoggerFactory.getLogger(ApiLogRepositoryImpl.class);

    @Autowired
    private MongoTemplate journalMongoTemplate;

    @PostConstruct
    public void init() {
        logger.debug(" mongoInfo: " + journalMongoTemplate.getDb().getName(), journalMongoTemplate.getDb().getMongo().getAddress());
    }

    @Override
    public ApiLogDocument findByUserIdLastAccess(String userId) {
        logger.debug("userId: " + userId);
        Query query = new Query();
        Criteria criteria = Criteria.where("contextMap.user_id").is(userId);
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "millis")));
        query.addCriteria(criteria);
        return journalMongoTemplate.findOne(query, ApiLogDocument.class);
    }


    @Override
    public List<ApiLogDocument> findLastVisitBetween(Time start, Time end) {
        logger.debug("Time range: " + start + " -> " + end);

        Criteria criteria = Criteria.where("contextMap.user_id").ne(null)
                .andOperator(Criteria.where("millis").gt(start.getTimeInMillis())
                        , Criteria.where("millis").lt(end.getTimeInMillis()));

        GroupBy groupBy = GroupBy.key("contextMap.user_id")
                .initialDocument("{contextMap:{user_id:''}, millis: 0}")
                .reduceFunction("function(doc, prev){if(doc.millis>prev.millis){prev.millis=doc.millis;prev.contextMap.user_id=doc.contextMap.user_id}}");


        GroupByResults<ApiLogDocument> logs = journalMongoTemplate.group(criteria, "API_ACCESS_LOG", groupBy, ApiLogDocument.class);

        List ret = new ArrayList();
        for (ApiLogDocument log : logs) {
            ret.add(log);
        }

        return ret;
    }
}
