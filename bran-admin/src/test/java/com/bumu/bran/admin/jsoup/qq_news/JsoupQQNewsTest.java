package com.bumu.bran.admin.jsoup.qq_news;

import com.google.gson.Gson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author majun
 * @date 2017/11/3
 * @email 351264830@qq.com
 */
public class JsoupQQNewsTest {

    private static Logger logger = LoggerFactory.getLogger(JsoupQQNewsTest.class);

    private static String URL = "http://news.open.qq.com/cgi-bin/article.php?";

    private static String SITE_SPORT = "sport";

    /**
     * 要闻
     *
     * @throws Exception
     */
    @Test
    public void news() throws Exception {
        Document doc = Jsoup.connect("http://news.qq.com/").get();

        logger.info("doc: " + doc.title());
        Elements elements = doc.select(".news .con .item .Q-tpList");
        if (elements.isEmpty()) {
            return;
        }
        // 要闻
        logger.info("要闻");
        int i = 1;
        for (Element element : elements.subList(0, 5)) {
            logger.info(i + ": ");
            // 图片
            String imgPath = element.select("img").attr("src");
            logger.info("图片: " + imgPath);
            // 标题
            String titlePath = element.select(".text .linkto").text();
            logger.info("标题: " + titlePath);
            // 链接
            String hrefPath = element.select(".text .linkto").attr("href");
            logger.info("链接: " + hrefPath);
            i++;
        }
    }

    /**
     * 娱乐新闻
     *
     * @throws Exception
     */
    @Test
    public void entertainmentNews() throws Exception {
        Document doc = Jsoup.connect("http://ent.qq.com/").get();

        logger.info("doc: " + doc.title());
        Elements elements = doc.select(".ttMod .con .Q-tpList");
        if (elements.isEmpty()) {
            return;
        }
        // 娱乐新闻
        logger.info("娱乐新闻");
        int i = 1;
        for (Element element : elements.subList(0, 5)) {
            logger.info(i + ": ");
            // 图片
            String imgPath = element.select(".pic").attr("href");
            logger.info("图片: " + imgPath);
            // 标题
            String titlePath = element.select(".linkto").text();
            logger.info("标题: " + titlePath);
            // 链接
            String hrefPath = element.select(".linkto").attr("href");
            logger.info("链接: " + hrefPath);
            i++;
        }
    }

    /**
     * 企业-科技新闻
     *
     * @throws Exception
     */
    @Test
    public void sportsNews() throws Exception {
        Document doc = Jsoup.connect("http://sports.qq.com/").get();

        logger.info("doc: " + doc.title());
        Elements elements = doc.select(".extra .scr_fandl .scr_focus .focus_con .item");
        if (elements.isEmpty()) {
            return;
        }
        // 娱乐新闻
        logger.info("体育新闻");
        int i = 1;
        for (Element element : elements.subList(0, 5)) {
            logger.info(i + ": ");
            // 图片
            String imgPath = element.child(0).child(0).attr("src");
            logger.info("图片: " + imgPath);
            // 标题
            String titlePath = element.child(0).select(".shadow").text();
            logger.info("标题: " + titlePath);
            // 链接
            String hrefPath = element.child(0).attr("href");
            logger.info("链接: " + hrefPath);
            i++;
        }
    }

    /**
     * 体育新闻
     *
     * @throws Exception
     */
    @Test
    public void News() throws Exception {
        Document doc = Jsoup.connect("http://tech.qq.com/").get();

        logger.info("doc: " + doc.title());
        Elements elements = doc.select(".hotlist .first .Q-tpList");
        if (elements.isEmpty()) {
            return;
        }
        // 娱乐新闻
        logger.info("科技新闻");
        int i = 1;
        for (Element element : elements.subList(0, 5)) {
            logger.info(i + ": ");
            // 图片
            String imgPath = CollectionUtils.isNotEmpty(element.select(".zutu1")) ? element.select(".zutu1").get(0).attr("src") : element.select(".zutu0").attr("src");
            logger.info("图片: " + imgPath);
            // 标题
            String titlePath = element.select(".Q-tpListInner .itemtxt").first().child(0).text();
            logger.info("标题: " + titlePath);
            // 链接
            String hrefPath = element.select(".Q-tpListInner .itemtxt").first().child(0).child(0).attr("href");
            logger.info("链接: " + hrefPath);
            i++;
        }
    }

    /**
     * 模拟请求获取分类新闻信息
     * @throws Exception
     */
    private void httpGet() throws Exception {
        NewsHttpHelper helper = new NewsHttpHelper(URL, SITE_SPORT);
        HttpGet get = helper.initHttpGet();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpResponse response = httpclient.execute(get);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);
        List<Map<String, String>> resloveResult = helper.resloveNewsHttpResult(result);
        httpclient.close();
    }


    private class NewsHttpHelper{

        String site = "";

        String url = "";

        final String cnt = "46";

        final String of = "json";

        final String callback = "jQuery1120037667124696923704_1509950414085";

        final String referer = "http://news.qq.com/";

        final String cookie = "_qpsvr_localtk=tk5770; pgv_pvi=7903833088; pgv_info=ssid=s7206322322; pgv_pvid=379907599";

        final String host = "news.open.qq.com";

        final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36";

        public NewsHttpHelper(String url, String site) {
            this.url = url;
            this.site = site;
        }

        public HttpGet initHttpGet() throws Exception{
            HttpGet get = new HttpGet();
            List<NameValuePair> params = new LinkedList<NameValuePair>();
            params.add(new BasicNameValuePair("site", site));
            params.add(new BasicNameValuePair("cnt", cnt));
            params.add(new BasicNameValuePair("of", of));
            params.add(new BasicNameValuePair("callback", callback));
            get.addHeader("Referer", referer);
            get.addHeader("Cookie", cookie);
            get.addHeader("Host", host);
            get.addHeader("User-Agent", userAgent);
            String paramString = URLEncodedUtils.format(params, "utf-8");
            logger.info("paramString: " + paramString);
            this.url += paramString;
            logger.info("url: " + url);
            get.setURI(new URI(this.url));
            return get;
        }

        public List<Map<String, String>> resloveNewsHttpResult(String newsHttpResult) {
            logger.info("httpresult: " + newsHttpResult);
            //其中括号内的内容是有效的JSON数据
            if (StringUtils.isNotBlank(newsHttpResult)) {
                newsHttpResult = newsHttpResult.substring(newsHttpResult.indexOf("(") + 1, newsHttpResult.lastIndexOf(")"));
            }
            Map<String, String> MapResult = new Gson().fromJson(newsHttpResult, Map.class);
            List<Map<String, String>> result = new Gson().fromJson(MapResult.get("data"), ArrayList.class);
            return result;
        }

    }

    /**
     * 今日头条
     *
     * @throws Exception
     */
    @Test
    public void testHttpUnit() throws Exception {
        getToutiaoNews(TouTiaoType.values()[0].getUrl(), TouTiaoType.values()[0].getTypeName());
    }

    private void getToutiaoNews(String url, String typeName) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet();
        httpGet.setURI(new URI(url));
        CloseableHttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

        Matcher matcher = pattern.matcher(result);

        char ch;

        while (matcher.find()) {

            ch = (char) Integer.parseInt(matcher.group(2), 16);

            result = result.replace(matcher.group(1), ch+"" );

        }


        Map<String, Object> resultMap = new Gson().fromJson(result, Map.class);
        List<Map<String, String>> datas = (ArrayList) resultMap.get("data");
        System.out.println(result);

    }

    enum TouTiaoType {

        teach("https://www.toutiao.com/api/pc/feed/?category=news_tech&utm_source=toutiao", "科技"),

        entertainment("https://www.toutiao.com/api/pc/feed/?category=news_entertainment&utm_source=toutiao", "娱乐"),

        society("https://www.toutiao.com/api/pc/feed/?category=news_society&utm_source=toutiao", "社会"),

        game("https://www.toutiao.com/api/pc/feed/?category=news_game&utm_source=toutiao", "游戏"),

        sport("https://www.toutiao.com/api/pc/feed/?category=news_sports&utm_source=toutiao", "体育"),

        funny("ttps://www.toutiao.com/api/pc/feed/?category=funny&utm_source=toutiao", "搞笑");


        private String url;

        private String typeName;

        TouTiaoType(String url, String typeName) {
            this.url = url;
            this.typeName = typeName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }
    }

}
