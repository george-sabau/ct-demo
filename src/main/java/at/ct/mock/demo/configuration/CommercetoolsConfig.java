package at.ct.mock.demo.configuration;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.defaultconfig.ApiRootBuilder;
import io.vrap.rmf.base.client.oauth2.ClientCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommercetoolsConfig {

    @Bean
    public ProjectApiRoot commercetoolsClient(CommercetoolsProperties props) {
        return ApiRootBuilder.of()
                .defaultClient(
                        ClientCredentials.of().withClientId(props.getClientId()).withClientSecret(props.getClientSecret()).build(),
                        props.getAuthUrl(),
                        props.getApiUrl()
                )
                .build(props.getProjectKey());
    }
}
