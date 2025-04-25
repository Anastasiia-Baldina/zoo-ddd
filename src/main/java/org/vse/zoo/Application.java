package org.vse.zoo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;
import org.vse.zoo.application.ZooApplicationContext;
import org.vse.zoo.presentation.ZooPresentationContext;

@Import({ZooApplicationContext.class, ZooPresentationContext.class})
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .run(args);
    }
}