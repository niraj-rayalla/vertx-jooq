package io.github.jklingsporn.vertx.jooq.generate.classic.jdbc.regular;

import io.github.jklingsporn.vertx.jooq.generate.AbstractVertxGeneratorTest;
import io.github.jklingsporn.vertx.jooq.generate.HsqldbConfigurationProvider;
import org.jooq.codegen.VertxGeneratorStrategy;
import io.github.jklingsporn.vertx.jooq.generate.classic.ClassicJDBCVertxGenerator;

/**
 * Created by jklingsporn on 17.09.16.
 */
public class GeneratorTest extends AbstractVertxGeneratorTest{


    public GeneratorTest() {
        super(ClassicJDBCVertxGenerator.class, VertxGeneratorStrategy.class,"classic.jdbc.regular", HsqldbConfigurationProvider.getInstance());
    }

}
