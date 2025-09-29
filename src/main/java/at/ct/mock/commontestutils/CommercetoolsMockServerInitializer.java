package at.ct.mock.commontestutils;

import lombok.Getter;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.GenericContainer;

import java.time.Duration;

public class CommercetoolsMockServerInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext>, BeforeAllCallback, AfterAllCallback {

    @Getter
    private static final GenericContainer<?> commercetoolsMockServer =
            new GenericContainer<>("labdigital/commercetools-mock-server:latest")
                    .withExposedPorts(8989)
                    .withFileSystemBind("src/main/resources/fixtures", "/data")
                    .withStartupTimeout(Duration.ofSeconds(10));


    public static synchronized void ensureStarted() {
        if (!commercetoolsMockServer.isRunning()) {
            commercetoolsMockServer.start();
        }
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        ensureStarted();
    }

    @Override
    public void afterAll(ExtensionContext context) {
        stop();
    }

    @Override
    public void initialize(ConfigurableApplicationContext context) {
       ensureStarted();

        var baseUrl = "http://" + commercetoolsMockServer.getHost() + ":"
                + commercetoolsMockServer.getMappedPort(8989);

        TestPropertyValues.of(
                "commercetools.auth-url=" + baseUrl + "/oauth/token",
                "commercetools.api-url=" + baseUrl
        ).applyTo(context.getEnvironment());
    }

    public static void stop() {
        if (commercetoolsMockServer.isRunning()) {
            commercetoolsMockServer.stop();
        }
    }
}
