package com.hardware.business.interceptor;

import org.iframework.commons.utils.log.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Set;

/**
 * <br>
 * RestTemplate日志拦截器，方便查看请求和响应内容
 * @author sunzhongshuai
 */
public class LoggingRequestInterceptor  implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);
        log(request,body,response);
        return response;
    }

    private void log(HttpRequest request, byte[] body, ClientHttpResponse response) throws IOException {
        Log.i("===========================请求第三方接口开始===========================");
        HttpHeaders httpHeaders=request.getHeaders();
        Set<String> keys = httpHeaders.keySet();
        Iterator<String> iterator= keys.iterator();
        StringBuilder sb=new StringBuilder("headers:\n");
        sb.append("请求url:");
        sb.append(request.getURI().toString());
        sb.append("\n");
        sb.append("请求方法:");
        sb.append(request.getMethod());
        sb.append("\n");
        sb.append("headers:");
        sb.append("\n");
        while(iterator.hasNext()){
            String key=iterator.next();
            sb.append(key);
            sb.append(":");
            sb.append(httpHeaders.get(key));
            sb.append("\n");
        }
        sb.append("Request body: ");
        sb.append(new String(body, "UTF-8"));
        Log.i(sb.toString());

        InputStream in = response.getBody();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[4096];

        int count;
        while((count = in.read(data, 0, 4096)) != -1) {
            outStream.write(data, 0, count);
        }

        Log.i("接口返回：");
        Log.i(new String(outStream.toByteArray(), "UTF-8"));
        Log.i("===========================请求第三方接口结束===========================");
        in.close();
        outStream.close();
    }
}
