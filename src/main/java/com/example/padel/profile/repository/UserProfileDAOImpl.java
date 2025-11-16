package com.example.padel.profile.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserProfileDAOImpl implements UserProfileDAO {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserProfileDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    @Override
    public int updateProfile(String userId, String firstName, String lastName, String encodedPassword) {
        StringBuilder sql = new StringBuilder("UPDATE app_user SET ");
        MapSqlParameterSource params = new MapSqlParameterSource("id", userId);

        boolean firstField = true;

        if (firstName != null && !firstName.isBlank()) {
            sql.append("first_name = :firstName");
            params.addValue("firstName", firstName);
            firstField = false;
        }

        if (lastName != null && !lastName.isBlank()) {
            if (!firstField) sql.append(", ");
            sql.append("last_name = :lastName");
            params.addValue("lastName", lastName);
            firstField = false;
        }

        if (encodedPassword != null) {
            if (!firstField) sql.append(", ");
            sql.append("password = :password");
            params.addValue("password", encodedPassword);
        }
        sql.append(", updated_at = CURRENT_TIMESTAMP WHERE id = :id");
        return namedParameterJdbcTemplate.update(sql.toString(), params);

    }


}
