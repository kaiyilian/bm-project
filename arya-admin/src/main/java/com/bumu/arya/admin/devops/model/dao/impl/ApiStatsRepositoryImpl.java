package com.bumu.arya.admin.devops.model.dao.impl;

import com.bumu.arya.admin.devops.model.ApiVisits;
import com.bumu.arya.admin.devops.model.ApiVisitsRatio;
import com.bumu.arya.admin.devops.model.dao.ApiStatsRepository;
import com.bumu.arya.admin.devops.model.entity.ApiLogDocument;
import com.bumu.arya.admin.devops.result.DimensionData;
import com.bumu.arya.admin.devops.result.PvUvDimensionData;
import com.bumu.arya.admin.devops.result.TotalVisitsResult;
import com.bumu.common.util.TimestampUtils;
import com.github.swiftech.swifttime.Time;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators.Gt;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators.Cond;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.ArithmeticOperators.*;
import static org.springframework.data.mongodb.core.aggregation.DateOperators.DateToString;
import static org.springframework.data.mongodb.core.aggregation.DateOperators.DayOfWeek;
import static org.springframework.data.mongodb.core.aggregation.StringOperators.Substr;

/**
 * @author Allen 2017-11-27
 **/
@Component
public class ApiStatsRepositoryImpl implements ApiStatsRepository {

    Logger log = LoggerFactory.getLogger(ApiStatsRepositoryImpl.class);

    long start = System.currentTimeMillis();
    long time = System.currentTimeMillis();
    Map<String, Long> profile = new TreeMap<>();

    @Autowired
    MongoTemplate mongoTemplate;

    TagFormatter dateTagFormatter = new TagFormatter() {
        @Override
        public String format(String original) {
            return StringUtils.substring(original, 5);
        }
    };

    TagFormatter weekTagFormatter = new TagFormatter() {
        @Override
        public String format(String original) {
            int dayOfWeek = Integer.parseInt(original);
            Time time = new Time().setToFirstDayOfCurrentWeek().increaseDates(dayOfWeek - 1);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            simpleDateFormat.applyPattern("E");
            return simpleDateFormat.format(new Date(time.getTimeInMillis()));
        }
    };


//    TimeTagFormatter dayHourTagFormatter = new TimeTagFormatter("dd-HH:00");


    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @PostConstruct
    public void init() {
        log.info(mongoTemplate.getDb().getName());
        log.info(String.valueOf(mongoTemplate.getDb().getMongo().getAddress()));
    }

    public static final long HARD_OFFSET = 0;// 1000L * 60L * 60L * 24L * 365L;

    @Override
    public TotalVisitsResult findTotalVisitsToday() {
        long begin = new Time().truncateAtDate().getTimeInMillis();
        long count = countPvSince(begin);
        return new TotalVisitsResult("今天访问PV总数", count);
    }

    @Override
    public TotalVisitsResult findTotalVisitsThisWeek() {
        long begin = new Time().truncateAtWeek().getTimeInMillis();
        long count = countPvSince(begin);
        return new TotalVisitsResult("本周访问PV总数", count);
    }

    @Override
    public TotalVisitsResult findTotalVisitsThisMonth() {
        long begin = new Time().truncateAtMonth().getTimeInMillis();
        long count = countPvSince(begin);
        return new TotalVisitsResult("本月访问PV总数", count);
    }

    @Override
    public TotalVisitsResult findTotalVisitsThisYear() {
        long begin = new Time().truncateAtYear().getTimeInMillis();
        long count = countPvSince(begin);
        return new TotalVisitsResult("今年访问PV总数", count);
    }


    @Override
    public TotalVisitsResult findTotalVisitsAll() {
        long count = mongoTemplate.count(null, ApiLogDocument.class);
        return new TotalVisitsResult("访问PV总数", count);
    }

    /**
     * 统计指定时间至今的所有访问支付
     *
     * @param begin 查询开始时间的时间戳
     * @return
     */
    private long countPvSince(long begin) {
        long end = System.currentTimeMillis() - HARD_OFFSET;
        begin -= HARD_OFFSET;
        int offset = TimestampUtils.toHours(end - begin);
        System.out.printf("时间段：%s - %s%n", new Time(begin), new Time(end));
        System.out.printf("时长：%d小时%n", offset);
        Criteria criteria = Criteria.where("millis").gt(begin)
                .andOperator(Criteria.where("millis").lt(end));
        Query query = new Query().addCriteria(criteria);
        return mongoTemplate.count(query, ApiLogDocument.class);
    }

    @Override
    public PvUvDimensionData findVisitsLast24Hours() {
        long cutoff = System.currentTimeMillis() - 16 * 60 * 60 * 1000;
        log.info(String.valueOf(cutoff));
        log.info(new Date(cutoff).toString());

        Floor millis = Floor.floorValueOf(Divide.valueOf("millis").divideBy(60 * 60 * 1000));

        Aggregation agg = newAggregation(
                project("millis")
                        .and(millis).as("hourInMillis")
//                        .and(dateFormatOps).as("dateHour")
                        .and("contextMap.imei").as("imei"),
                match(Criteria.where("millis").gte(cutoff)),
                group("hourInMillis", "imei")
                        .count().as("ppv"),
//                sort(ASC, "dateHour"),
                group("hourInMillis")
                        .sum("ppv").as("pv")
                        .count().as("uv")
                        .first("hourInMillis").as("tag"),
                sort(ASC, "tag")
        );

        profileBegin("findVisitsLast24Hours");
        AggregationResults<ApiVisits> visits = mongoTemplate.aggregate(agg, "API_ACCESS_LOG", ApiVisits.class);
        profileEnd("findVisitsLast24Hours");

        Time t = new Time().truncateAtHour();
        List<String> tags = new ArrayList<>(24);
        for (int i = 0; i < 24; i++) {
            t.increaseHours(-1);
            tags.add(String.valueOf(t.getTimeInMillis() / (60 * 60 * 1000)) + ".0");
        }

        Collection<ApiVisits> apiVisits = fillUp(visits.getMappedResults(), tags, new TimeTagFormatter("dd-HH:00"));

        return convertToChartsFormat(new ArrayList<>(apiVisits));
    }

    @Override
    public PvUvDimensionData findVisitsLast30Days() {
        Time cutoffTime = new Time().truncateAtDate().increaseDates(-30);
        long cutoff = cutoffTime.getTimeInMillis() + 8 * 60 * 60 * 1000;
        log.info(cutoffTime.toString());

        DateToString dateFormatOps = DateToString.dateOf("date").toString("%Y-%m-%d");

        Aggregation agg = newAggregation(
                project("millis")
                        .and(dateFormatOps).as("dayOfMonth")
                        .and("contextMap.imei").as("imei"),
                match(Criteria.where("millis").gte(cutoff)),
                group("dayOfMonth", "imei")
                        .count().as("ppv"),
                group("dayOfMonth")
                        .sum("ppv").as("pv")
                        .count().as("uv")
                        .first("dayOfMonth").as("tag"),
                sort(ASC, "tag")
        );

        profileBegin("findVisitsLast30Days");
        AggregationResults<ApiVisits> visits = mongoTemplate.aggregate(agg, "API_ACCESS_LOG", ApiVisits.class);
        profileEnd("findVisitsLast30Days");

        Time t = new Time();
        List<String> tags = new ArrayList<>(30);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyy-MM-dd");
        for (int i = 0; i < 30; i++) {
            t.increaseDates(-1);
            tags.add(simpleDateFormat.format(new Date(t.getTimeInMillis())));
        }

        Collection<ApiVisits> apiVisits = fillUp(visits.getMappedResults(), tags, dateTagFormatter);

        return convertToChartsFormat(new ArrayList<>(apiVisits));
    }


    @Override
    public PvUvDimensionData findVisitsHourly() {
        Add hourInUTC8 = Add.valueOf(DateOperators.dateOf("date").hour()).add(8);

        Cond hourIn24 = Cond.when(Gt.valueOf(hourInUTC8).greaterThanValue(23))
                .thenValueOf(Subtract.valueOf(hourInUTC8).subtract(24))
                .otherwiseValueOf(hourInUTC8);

        Aggregation agg = newAggregation(
                project("millis")
                        .and(hourIn24).as("hourInUTC8")
                        .and("contextMap.imei").as("imei"),
                group("hourInUTC8", "imei")
                        .count().as("ppv"),
                group("hourInUTC8")
                        .sum("ppv").as("pv")
                        .count().as("uv")
                        .first("hourInUTC8").as("tag"),
                sort(ASC, "tag")
        );

        profileBegin("findVisitsHourly");
        AggregationResults<ApiVisits> visits = mongoTemplate.aggregate(agg, "API_ACCESS_LOG", ApiVisits.class);
        profileEnd("findVisitsHourly");

        List<String> tags = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            tags.add(StringUtils.leftPad(String.valueOf(i), 2, '0'));
        }
        Collection<ApiVisits> apiVisits = fillUpWithKeyPadding(visits.getMappedResults(), tags, new TagFormatter() {
            @Override
            public String format(String original) {
                return StringUtils.leftPad(original, 2, '0') + ":00";
            }
        });
        return convertToChartsFormat(new ArrayList<>(apiVisits));
    }


    @Override
    public PvUvDimensionData findVisitsWeekly() {
        Substr ops = Substr.valueOf(DayOfWeek.dayOfWeek("date")).substring(0, -1);

        Aggregation agg = newAggregation(
                project("millis")
                        .and(ops).as("dayOfWeek")
                        .and("contextMap.imei").as("imei"),
                group("dayOfWeek", "imei")
                        .count().as("ppv"),
                group("dayOfWeek")
                        .sum("ppv").as("pv")
                        .count().as("uv")
                        .first("dayOfWeek").as("tag"),
                sort(ASC, "tag")
        );

        profileBegin("findVisitsWeekly");
        AggregationResults<ApiVisits> visits = mongoTemplate.aggregate(agg, "API_ACCESS_LOG", ApiVisits.class);
        profileEnd("findVisitsWeekly");

        List<String> tags = Arrays.asList("1", "2", "3", "4", "5", "6", "7");

        Collection<ApiVisits> apiVisits = fillUp(visits.getMappedResults(), tags, weekTagFormatter);

        return convertToChartsFormat(new ArrayList<>(apiVisits));
    }


    @Override
    public PvUvDimensionData findVisitsByDeviceName() {
        Aggregation agg = newAggregation(
                project().and("contextMap.device_name").as("device_name")
                        .and("contextMap.imei").as("imei"),
                group("device_name", "imei")
                        .count().as("ppv"),
                group("device_name")
                        .sum("ppv").as("pv")
                        .count().as("uv")
                        .first("device_name").as("tag"),
                sort(DESC, "pv")
        );

        profileBegin("findVisitsByDeviceName");
        AggregationResults<ApiVisits> visits = mongoTemplate.aggregate(agg, "API_ACCESS_LOG", ApiVisits.class);
        profileEnd("findVisitsByDeviceName");

        return convertToChartsFormat(visits.getMappedResults());
    }

    @Override
    public PvUvDimensionData findVisitsByVersion() {
        Aggregation agg = newAggregation(
                project().and("contextMap.app_version").as("app_version")
                        .and("contextMap.imei").as("imei"),
                group("app_version", "imei")
                        .count().as("ppv"),
                group("app_version")
                        .sum("ppv").as("pv")
                        .count().as("uv")
                        .first("app_version").as("tag"),
                sort(ASC, "tag")
        );

        profileBegin("findVisitsByVersion");
        AggregationResults<ApiVisits> visits = mongoTemplate.aggregate(agg, "API_ACCESS_LOG", ApiVisits.class);
        profileEnd("findVisitsByVersion");

        return convertToChartsFormat(visits.getMappedResults());
    }

    /**
     * 填充2位的hour，然后格式化
     *
     * @param visits
     * @param tags
     * @param tagFormatter
     * @return
     */
    private Collection<ApiVisits> fillUpWithKeyPadding(List<ApiVisits> visits, List<String> tags,
                                                       TagFormatter tagFormatter) {
        Map<String, ApiVisits> m = new TreeMap<>();
        for (String tag : tags) {
            m.put(tag, new ApiVisits(tag));
        }
        // 填充
        for (ApiVisits visit : visits) {
            m.put(StringUtils.leftPad(visit.getTag(), 2, '0'), visit);
        }
        // 格式化Tag（不包括临时key）
        if (tagFormatter != null) {
            for (String key : m.keySet()) {
//                log.info("format: " + key);
                ApiVisits item = m.get(key);
                item.setTag(tagFormatter.format(item.getTag()));
            }
        }
        return m.values();
    }


    /**
     * 填充后格式化 tag
     *
     * @param visits
     * @param tags
     * @param tagFormatter
     * @return
     */
    private Collection<ApiVisits> fillUp(List<ApiVisits> visits, List<String> tags, TagFormatter tagFormatter) {
        Map<String, ApiVisits> m = new TreeMap<>();
        // 初始化
        for (String tag : tags) {
            m.put(tag, new ApiVisits(tag));
        }
        // 填充
        for (ApiVisits visit : visits) {
//            log.info(" ##" + visit.getTag());
            m.put(visit.getTag(), visit);
        }
        // 格式化Tag（不包括临时key）
        if (tagFormatter != null) {
            for (String key : m.keySet()) {
                ApiVisits item = m.get(key);
                item.setTag(tagFormatter.format(item.getTag()));
            }
        }
        return m.values();
    }


    @Override
    public List<ApiVisitsRatio> findApiPvUv() {
        Aggregation agg = newAggregation(
                project("message")
//                        .and(DayOfWeek.dayOfWeek("date")).as("hour")
                        .and("contextMap.imei").as("imei"),
                group("message", "imei")
                        .count().as("ppv"),
                group("message")
                        .sum("ppv").as("pv")
                        .count().as("uv")
                        .first("message").as("tag"),
                sort(ASC, "message")
        );
        profileBegin("findApiPvUv");
        AggregationResults<ApiVisitsRatio> visits = mongoTemplate.aggregate(agg, "API_ACCESS_LOG", ApiVisitsRatio.class);
        profileEnd("findApiPvUv");

        List<ApiVisitsRatio> mappedResults = visits.getMappedResults();

        log.info("总数：" + mappedResults.size());
        long totalPv = 0;
        long totalUv = 0;
        for (ApiVisitsRatio i : mappedResults) {
            totalPv += i.getPv();
            totalUv += i.getUv();
        }

        System.out.printf("PV 总数%d, UV 总数%d%n", totalPv, totalUv);

        for (ApiVisitsRatio i : mappedResults) {
            i.setPvRatio(((float) i.getPv() * 100 / totalPv));
            i.setUvRatio(((float) i.getUv() * 100 / totalUv));
            System.out.printf("%5s %5f %5s %5f %s%n", i.getPv(), i.getPvRatio(), i.getUv(), i.getUvRatio(), i.getTag());
        }

        return mappedResults;
    }

    private PvUvDimensionData convertToChartsFormat(List<ApiVisits> mappedResults) {
        List<String> dimensions = new ArrayList<>();
        List<Long> pvValues = new ArrayList<>();
        List<Long> uvValues = new ArrayList<>();

        log.info("总数：" + mappedResults.size());

        System.out.printf("%5s %5s %s%n", "PV", "UV", "TAG");
        for (ApiVisits av : mappedResults) {
            System.out.printf("%5d %5d %s%n", av.getPv(), av.getUv(), av.getTag());
            dimensions.add(av.getTag());
            pvValues.add(av.getPv());
            uvValues.add(av.getUv());
        }

        PvUvDimensionData pvUvDimensionData = new PvUvDimensionData();
        pvUvDimensionData.setDimensions(dimensions);
        {
            DimensionData<Long> dimensionData = new DimensionData<>();
            dimensionData.setName("PV");
            dimensionData.setType("integer");
            dimensionData.setValues(pvValues);
            pvUvDimensionData.setPvDimensionData(dimensionData);
        }
        {
            DimensionData<Long> dimensionData = new DimensionData<>();
            dimensionData.setName("UV");
            dimensionData.setType("integer");
            dimensionData.setValues(uvValues);
            pvUvDimensionData.setUvDimensionData(dimensionData);
        }
        return pvUvDimensionData;
    }

    public static void main(String[] args) {
        System.out.println(String.format("%td - %tk", Calendar.getInstance(), Calendar.getInstance()));
    }

    public void testTime() {
        // 从时间戳转换成日期
        // 排序后处理tag（转换成数字）
        // 从时间中取出在处理小时

//        Concat dataHourOps =
//                Concat.valueOf(
//                        Substr.valueOf((dateOf("date").dayOfMonth())).substring(0, -1)).concat("-")
//                        .concatValueOf(Substr.valueOf(dateOf("date").hour()).substring(0, -1))
//                        .concat(":00");

        Floor millis = Floor.floorValueOf(Divide.valueOf("millis").divideBy(60 * 60 * 1000));

        DateToString dateFormatOps = DateToString.dateOf("date").toString("%Y-%m-%d %H:00:00");

        Aggregation agg = newAggregation(
                project("millis")
                        .and(millis).as("hourInMillis")
                        .and(dateFormatOps).as("hour"),
                group("hourInMillis")
                        .count().as("pv")
                        .first("hourInMillis").as("tag"),
                sort(ASC, "tag")
        );
        AggregationResults<ApiVisitsRatio> visits = mongoTemplate.aggregate(agg, "API_ACCESS_LOG", ApiVisitsRatio.class);

//        for (ApiVisitsRatio visit : visits) {
//            System.out.printf("%s -> %d%n", visit.getTag(), visit.getPv());
//            System.out.println(new Time((long) Float.parseFloat(visit.getTag()) * 60 * 60 * 1000));
//        }

    }


    public interface TagFormatter {

        /**
         * 要把统计结果中的原始 Tag 格式化成可以显示的
         *
         * @param original
         * @return
         */
        String format(String original);
    }


    /**
     * 格式化收缩为小时的时间戳
     */
    public static class TimeTagFormatter implements TagFormatter {
        String format;

        public TimeTagFormatter(String format) {
            this.format = format;
        }

        @Override
        public String format(String original) {
            Time time = new Time((long) Float.parseFloat(original) * 60 * 60 * 1000);
//            System.out.println("  " + time);
//            System.out.println("  " + original);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            simpleDateFormat.applyPattern(format);
            return simpleDateFormat.format(time.getTime());
        }
    }

    private void profileBegin(String name) {
        time = System.currentTimeMillis();
        profile.put(name, time);
    }

    private void profileEnd(String name) {
        Long pStart = profile.get(name);
        if (pStart == null || pStart == 0) {
            return;
        }
        profile.put(name, System.currentTimeMillis() - pStart);
        System.out.printf("%30s = %s%n", name, (float) pStart / 1000);
    }

    public void printProfiles() {
        for (String name : profile.keySet()) {
            System.out.printf("%30s = %s%n", name, (float) profile.get(name) / 1000);
        }

        long interval = System.currentTimeMillis() - start;
        System.out.printf("  总耗时：%fs%n", (float) interval / (float) 1000);
    }

    public void printProfile(String profileName) {
        Long aLong = profile.get(profileName);
        System.out.printf("%30s = %s%n", profileName, (float) aLong / 1000);
    }

}
