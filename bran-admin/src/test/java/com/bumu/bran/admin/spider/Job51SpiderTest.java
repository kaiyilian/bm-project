package com.bumu.bran.admin.spider;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author majun
 * @date 2017/11/7
 * @email 351264830@qq.com
 */
public class Job51SpiderTest {

    private static Logger logger = LoggerFactory.getLogger(Job51SpiderTest.class);

    /**
     * 条件
     *
     * @throws Exception
     */
    @Test
    public void searchJob() throws Exception {

        String job51url = "http://search.51job.com/jobsearch/search_result.php?";

        // 关键字
        String keyWord = "ios";

        List<NameValuePair> params = new LinkedList<NameValuePair>();
        // 关键字 例如 java
        params.add(new BasicNameValuePair("keyword", keyWord));
        // 地区 070300 苏州
        params.add(new BasicNameValuePair("jobarea", "070300"));
        String paramString = URLEncodedUtils.format(params, "utf-8");
        logger.info("paramString: " + paramString);
        job51url += paramString;
        Document doc =
                Jsoup.connect(job51url)
                        .get();

//      keyWord + ".{0,10}\s.{0,20}\s.{0,10}\s.{0,10}\s\d+-\d+
//        String regexTotalCount = "共\\d+条职位";
        keyWord +=".{0,10}\\s.{0,20}\\s.{0,10}\\s.{0,10}\\s\\d+-\\d+";

        Matcher matcher = Pattern
                .compile(keyWord, Pattern.CASE_INSENSITIVE)
                .matcher(doc.body().text());
        int i = 0;
        while (matcher.find()) {
            logger.info(matcher.group(), i++);
        }
//        String splitStr = "发布时间\\sjava.*上一页";
//        matcher = Pattern
//                .compile(splitStr, Pattern.CASE_INSENSITIVE)
//                .matcher(doc.body().text());
//        while (matcher.find()) {
//            String sub = matcher.group();
//
//        }
    }
}
