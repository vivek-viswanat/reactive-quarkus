package org.acme;

import io.vertx.axle.pgclient.PgPool;
import io.vertx.axle.sqlclient.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

public class User {

    long id;
    String name;

    public User() {

    }

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    private static User from(Row row) {
        return new User(row.getLong("user_id"), row.getString("name"));
    }

    public static CompletionStage<List<User>> findAll(PgPool client) {
        return client.query("SELECT user_id, name FROM users ORDER BY name ASC").thenApply(pgRowSet -> {
            List<User> list = new ArrayList<>(pgRowSet.size());
            for (Row row : pgRowSet) {
                list.add(from(row));
            }
            return list;
        });
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
