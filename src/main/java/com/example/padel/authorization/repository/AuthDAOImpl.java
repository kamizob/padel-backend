package com.example.padel.authorization.repository;

import com.example.padel.authorization.domain.User;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthDAOImpl implements AuthDAO {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AuthDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    @Override
    public int signUp(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("role", user.getRole().name())
                .addValue("isVerified", user.isVerified());
        String sql = """
                INSERT INTO app_user (id, email, password, first_name, last_name, role, is_verified)
                VALUES (:id, :email, :password, :firstName, :lastName, :role, :isVerified)""";
        return namedParameterJdbcTemplate.update(sql, params);
    }


}
