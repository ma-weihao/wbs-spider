package cn.quickweather.wbsspider.service.service;

import cn.quickweather.wbsspider.entity.exp.CommonUtils;
import cn.quickweather.wbsspider.entity.utils.ParamAssert;
import com.alibaba.fastjson.JSON;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created by maweihao on 5/20/21
 */
@Service
public class HttpFetchService {

    private CloseableHttpClient httpClient;

    private static final int CONNECTION_MAX_PER_ROUTE_SIZE = 100;

    private static final int CONNECT_MAX_SIZE = 200;

    private static final String DEFAULT_CHAR_SET = "UTF-8";

    private static final int DEFAULT_CONNECTION_TIMEOUT = 1000;

    private static final int DEFAULT_SOCKET_TIMEOUT = 2000;

    private static final int DEFAULT_REQUEST_TIMEOUT = 1000;

    private final Logger logger = LoggerFactory.getLogger(HttpFetchService.class);

    @PostConstruct
    public void init() {
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setMaxTotal(CONNECT_MAX_SIZE);
        manager.setDefaultMaxPerRoute(CONNECTION_MAX_PER_ROUTE_SIZE);
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(DEFAULT_REQUEST_TIMEOUT)
                .setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT)
                .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
                .build();
        httpClient = HttpClients.custom()
                .setConnectionManager(manager)
                .setDefaultRequestConfig(config)
                .build();
    }

    public <T> T post(String url, String body, Class<T> clazz) {
        try {
            String s = post(url, body);
            return JSON.parseObject(s, clazz);
        } catch (IOException e) {
            logger.error(String.format("post IO EXP %s(%s)", url, body), e);
            return null;
        } catch (Exception e) {
            logger.error(String.format("post EXP %s(%s)", url, body), e);
            return null;
        }
    }

    public String post(String url, String body) throws IOException {
        ParamAssert.nonBlank(url);
        HttpPost post = new HttpPost();
        post.setURI(URI.create(url));
        fillBody(post, body);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(post);
            String res = EntityUtils.toString(response.getEntity(), DEFAULT_CHAR_SET);
            return res;
        } finally {
            close(response);
        }
    }

    public <T> T get(String url, Map<String, String> params, Class<T> clazz) {
        try {
            String s = get(url, params);
            return JSON.parseObject(s, clazz);
        } catch (IOException e) {
            logger.error(String.format("GET IO EXP %s %s", url, JSON.toJSONString(params)), e);
            return null;
        } catch (Exception e) {
            logger.error(String.format("GET EXP %s %s", url, JSON.toJSONString(params)), e);
            return null;
        }
    }

    public String get(String url, Map<String, String> params) throws IOException {
        ParamAssert.nonBlank(url);
        URI uri = null;
        CloseableHttpResponse response = null;
        try {
            if (params != null && params.size() > 0) {
                URIBuilder uriBuilder = new URIBuilder(url);
                for (Map.Entry<String, String> entry: params.entrySet()) {
                    if (entry.getValue() != null) {
                        uriBuilder.addParameter(entry.getKey(), entry.getValue());
                    }
                }
                uri = uriBuilder.build();
            } else {
                uri = URI.create(url);
            }
            HttpGet httpGet = new HttpGet(uri);
            response = httpClient.execute(httpGet);
            String res = EntityUtils.toString(response.getEntity(), DEFAULT_CHAR_SET);
            return res;
        } catch (URISyntaxException e) {
            logger.error("ERROR URISyntaxException." + url, e);
            return null;
        } finally {
            close(response);
        }
    }

    private void fillBody(HttpPost post, String content) {
        if (!CommonUtils.isBlank(content)) {
            StringEntity entity = new StringEntity(content, "utf-8");
            entity.setContentEncoding(DEFAULT_CHAR_SET);
            entity.setContentType("application/json");
            post.setEntity(entity);
        }
    }

    private void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
    }
}
