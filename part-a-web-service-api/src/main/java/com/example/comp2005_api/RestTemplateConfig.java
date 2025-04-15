/* Required so that Spring knows how to inject RestTemplate into ApiHelper
when running the application, instead of the mocked object used in ApiHelperTest. */

package com.example.comp2005_api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig
{
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
