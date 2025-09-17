package at.ct.mock.demo.fixtures;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Profile("demo")
@Component
@RequiredArgsConstructor
public class CommercetoolsFixtureLoaderRunner implements ApplicationRunner {

    private final CommercetoolsFixtureLoader fixtureLoader;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        fixtureLoader.load(FixtureScenario.FULL);
    }
}