package com.seagalputra.cardealerapp.provider;

import com.seagalputra.cardealerapp.fetchers.CarFetchers;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import static graphql.schema.idl.TypeRuntimeWiring.*;
import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class GraphQLProvider {

    private GraphQL graphQL;

    @Value("classpath:schema.graphqls")
    private Resource graphQLSchema;

    private final CarFetchers carFetchers;

    public GraphQLProvider(CarFetchers carFetchers) {
        this.carFetchers = carFetchers;
    }

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @PostConstruct
    public void initializeSchema() throws IOException {
        Reader reader = new InputStreamReader(graphQLSchema.getInputStream(), UTF_8);
        String schemaDefinition = FileCopyUtils.copyToString(reader);

        GraphQLSchema schema = buildSchema(schemaDefinition);
        this.graphQL = GraphQL.newGraphQL(schema).build();
    }

    private GraphQLSchema buildSchema(String schemaFile) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("getCarById", carFetchers.getCarByIdFetcher())
                        .dataFetcher("getAllCar", carFetchers.getAllCar()))
                .build();
    }
}
