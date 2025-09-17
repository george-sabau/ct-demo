package at.ct.mock.demo.server;

import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.GenericContainer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;

@Profile("demo")
@Component
public class CommercetoolsMockServerRuntimeInitializer
        implements EnvironmentPostProcessor {

    @Getter
    private static final GenericContainer<?> commercetoolsMockServer =
            new GenericContainer<>("labdigital/commercetools-mock-server:latest")
                    .withExposedPorts(8989)
                    .withFileSystemBind("src/test/resources/fixtures", "/data")
                    .withStartupTimeout(Duration.ofSeconds(10));

    public static synchronized void ensureStarted() {
        if (!commercetoolsMockServer.isRunning()) {
            commercetoolsMockServer.start();
        }
    }

    @Override
    public void postProcessEnvironment(final ConfigurableEnvironment environment, final SpringApplication application) {
        if (!asList(environment.getActiveProfiles()).contains("demo")) {
            return; // skip configuration if not running in "demo" profile
        }

        ensureStarted();
        configureServer(environment);
        addHooks();
    }

    private static void configureServer(final ConfigurableEnvironment environment) {
        String baseUrl = "http://" + commercetoolsMockServer.getHost() + ":"
                + commercetoolsMockServer.getMappedPort(8989);

        // Override properties in the environment
        Map<String, Object> props = new HashMap<>();
        props.put("commercetools.auth-url", baseUrl + "/oauth/token");
        props.put("commercetools.api-url", baseUrl);

        environment.getPropertySources()
                .addFirst(new MapPropertySource("commercetoolsMockServerProps", props));
    }

    private static void addHooks() {
        // Add shutdown hook to stop the container when the JVM exits
        Runtime.getRuntime().addShutdownHook(new Thread(CommercetoolsMockServerRuntimeInitializer::stop));
    }

    public static void stop() {
        if (commercetoolsMockServer.isRunning()) {
            commercetoolsMockServer.stop();
        }
    }
}
