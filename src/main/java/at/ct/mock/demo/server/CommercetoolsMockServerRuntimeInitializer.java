package at.ct.mock.demo.server;

import lombok.Getter;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.GenericContainer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Profile("demo")
@Component
public class CommercetoolsMockServerRuntimeInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

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
    public void initialize(ConfigurableApplicationContext context) {
        ensureStarted();
        configureServer(context);
        addHooks();
    }

    private static void configureServer(final ConfigurableApplicationContext context) {
        String baseUrl = "http://" + commercetoolsMockServer.getHost() + ":"
                + commercetoolsMockServer.getMappedPort(8989);

        // Override properties in the environment
        Map<String, Object> props = new HashMap<>();
        props.put("commercetools.auth-url", baseUrl + "/oauth/token");
        props.put("commercetools.api-url", baseUrl);

        context.getEnvironment()
                .getPropertySources()
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
