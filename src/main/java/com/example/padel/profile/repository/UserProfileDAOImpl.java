package com.example.padel.profile.repository;

import com.example.padel.profile.api.response.UserProfileResponse;
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
    @Override
    public UserProfileResponse findProfileById(String userId) {
        String sql = """
        SELECT id, email, first_name, last_name, role, is_verified
        FROM app_user
        WHERE id = :id
    """;

        MapSqlParameterSource params = new MapSqlParameterSource("id", userId);

        return namedParameterJdbcTemplate.query(sql, params, rs -> {
            if (rs.next()) {
                return new UserProfileResponse(
                        rs.getString("id"),
                        rs.getString("email"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("role"),
                        rs.getBoolean("is_verified")
                );
            }
            return null;
        });
    }
    @Override
    public String findPasswordHashByUserId(String userId) {
        String sql = "SELECT password FROM app_user WHERE id = :id";
        return namedParameterJdbcTemplate.query(sql,
                new MapSqlParameterSource("id", userId),
                rs -> rs.next() ? rs.getString("password") : null);
    }



}
