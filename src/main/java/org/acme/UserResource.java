package org.acme;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletionStage;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private static int hitcounter = 0;

    static final Logger LOG = LoggerFactory.getLogger(UserResource.class);

    @Inject
    io.vertx.axle.pgclient.PgPool client;

    @Inject
    @ConfigProperty(name = "test.schema.create", defaultValue = "true")
    boolean schemaCreate;

    @PostConstruct
    void config() {
        if (schemaCreate) {
            initdb();
        }
    }

    private void initdb() {
        client.query("DROP TABLE IF EXISTS users")
                .thenCompose(r -> client.query("CREATE TABLE public.users (user_id int4 NOT NULL GENERATED ALWAYS AS IDENTITY, name varchar(50) NULL)"))
                .thenCompose(r -> client.query("INSERT INTO users (name) VALUES ('raj')"))
                .thenCompose(r -> client.query("INSERT INTO users (name) VALUES ('hulk')"))
                .thenCompose(r -> client.query("INSERT INTO users (name) VALUES ('spiderman')"))
                .thenCompose(r -> client.query("INSERT INTO users (name) VALUES ('superman')"))
                .thenCompose(r -> client.query("INSERT INTO users (name) VALUES ('batman')"))
                .thenCompose(r -> client.query("INSERT INTO users (name) VALUES ('ironman')"))
                .thenCompose(r -> client.query("INSERT INTO users (name) VALUES ('heman')"))
                .toCompletableFuture()
                .join();
    }

    @GET
    public CompletionStage<Response> get() {

        LOG.info("Request to load users" + (++hitcounter));

        return User.findAll(client)
                .thenApply(Response::ok)
                .thenApply(Response.ResponseBuilder::build);
    }

}
