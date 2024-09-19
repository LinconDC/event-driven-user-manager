package br.com.usermanager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "users")
public record User(
        @Id String id,
        @Field("userId") String userId,
        @Field("name") String name,
        @Field("email") String email,
        @Field("secret") Secret secret,
        @Field("createDate") LocalDate createDate,
        @Field("login") List<Login> login,
        @Field("session") String session,
        @Field("token") String token
) {}

