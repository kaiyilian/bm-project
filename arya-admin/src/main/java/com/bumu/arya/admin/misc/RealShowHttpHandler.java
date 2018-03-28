package com.bumu.arya.admin.misc;

import com.bumu.arya.Utils;
import com.bumu.arya.admin.misc.controller.command.RealShowCommand;
import com.bumu.arya.webservice.Poster;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author majun
 * @date 2017/3/9
 */
@Component
public class RealShowHttpHandler implements Poster<RealShowCommand> {

	public static final String secret = "78a6a2098943502142660925219bcebc";
	private static final String token = "755d3ee5b57a23e132dc0cbf9d882f99";
	private static Logger logger = LoggerFactory.getLogger(RealShowHttpHandler.class);
	@Value(value = "${web.service.real.show.url}")
	private String url;

	@Override
	public String post(RealShowCommand params) throws IOException {

		logger.info("请求参数: " + params.toString());
		logger.info("url: " + url);
		HttpPost post = new HttpPost(url);

		post.addHeader("Content-Type", "application/json");
		post.addHeader("accessToken", token);
		String sign = sign(params.toRequest() + "secret=" + RealShowHttpHandler.secret);
		post.addHeader("sign", sign);

		post.setEntity(new StringEntity(params.toJson(), HTTP.UTF_8));

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpResponse response = httpclient.execute(post);
		HttpEntity entity = response.getEntity();
		String result = EntityUtils.toString(entity);
		logger.info("result :" + result);
		httpclient.close();
		return result;
	}

	public String sign(String queryString) {
		logger.info("sign before str: " + queryString);
		logger.info("length: " + queryString.length());
		String sign = Utils.md5(queryString);
		logger.info("sign: " + sign);
		return sign;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
