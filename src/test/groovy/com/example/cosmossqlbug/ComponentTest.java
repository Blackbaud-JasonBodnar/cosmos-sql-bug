package com.example.cosmossqlbug;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles({"local", "test", "componentTest"})
@ComponentScan(basePackages = "com.example.cosmossqlbug")
@Import({CosmosSqlBugApplication.class, AzureCosmosDbConfiguration.class})
@SpringBootTest(
        properties = {"server.port=10009", "management.port=10001"},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public @interface ComponentTest {

}
