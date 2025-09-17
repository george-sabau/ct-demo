package at.ct.mock.demo;

import at.ct.mock.demo.server.CommercetoolsMockServerRuntimeInitializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoApplication.class)
                .profiles("demo") // enable to run with commerce tools mock
                .initializers(new CommercetoolsMockServerRuntimeInitializer())
                .run(args);
    }

}
