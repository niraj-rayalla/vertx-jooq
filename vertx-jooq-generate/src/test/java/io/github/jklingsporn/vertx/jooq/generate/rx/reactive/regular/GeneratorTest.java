package io.github.jklingsporn.vertx.jooq.generate.rx.reactive.regular;

import io.github.jklingsporn.vertx.jooq.generate.AbstractVertxGeneratorTest;
import io.github.jklingsporn.vertx.jooq.generate.PostgresConfigurationProvider;
import org.jooq.codegen.VertxGeneratorStrategy;
import io.github.jklingsporn.vertx.jooq.generate.rx.RXReactiveVertxGenerator;

/**
 * Created by jklingsporn on 17.09.16.
 */
public class GeneratorTest extends AbstractVertxGeneratorTest{


    public GeneratorTest() {
        super(RXReactiveVertxGenerator.class, VertxGeneratorStrategy.class,"rx.reactive.regular", PostgresConfigurationProvider.getInstance());
    }

}
