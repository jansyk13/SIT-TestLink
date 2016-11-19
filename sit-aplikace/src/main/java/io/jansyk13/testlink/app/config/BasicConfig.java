package io.jansyk13.testlink.app.config;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;

@Configuration
public class BasicConfig {

    @Bean
    public TestLinkAPI testLinkAPI(@Value("${testlink.url}") String url, @Value("${testlink.devkey}") String devKey) throws MalformedURLException {
        return new TestLinkAPI(new URL(url), devKey);
    }

}
