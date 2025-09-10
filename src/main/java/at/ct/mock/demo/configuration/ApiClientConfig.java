package at.ct.mock.demo.configuration;

import at.ct.mock.demo.client.ApiClient;
import at.ct.mock.demo.client.api.DefaultApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public ApiClient apiClient(RestTemplate restTemplate) {
        final ApiClient apiClient = new ApiClient(restTemplate);
        apiClient.setBasePath("http://localhost:8080/api");
        return apiClient;
    }

    @Bean
    public DefaultApi customerClient(ApiClient apiClient) {
        return new DefaultApi(apiClient);
    }
}