package com.example.padel.authorization.repository;

import com.example.padel.authorization.domain.User;
import com.example.padel.authorization.domain.enums.Role;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    @Override
    public User findByEmail(String email) {
        String sql = "SELECT * FROM app_user WHERE email = :email";
        MapSqlParameterSource params = new MapSqlParameterSource("email", email);

        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) ->
                new User(
                        rs.getString("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        Role.valueOf(rs.getString("role")),
                        rs.getBoolean("is_verified")
                )
        ).stream().findFirst().orElse(null);
    }
    @Override
    public int verifyUserByEmail(String email) {
        String sql = "UPDATE app_user SET is_verified = true WHERE email = :email";
        MapSqlParameterSource params = new MapSqlParameterSource("email", email);
        return namedParameterJdbcTemplate.update(sql, params);
    }
    @Override
    public User findById(String id) {
        String sql = "SELECT * FROM app_user WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) ->
                new User(
                        rs.getString("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        Role.valueOf(rs.getString("role")),
                        rs.getBoolean("is_verified")
                )
        ).stream().findFirst().orElse(null);
    }
    @Override
    public int updateUserRole(String userId, Role newRole) {
        String sql = """
        UPDATE app_user
        SET role = :role
        WHERE id = :id
    """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("role", newRole.name())
                .addValue("id", userId);

        return namedParameterJdbcTemplate.update(sql, params);
    }
    @Override
    public List<User> findAllUsers() {
        String sql = "SELECT * FROM app_user ORDER BY created_at ASC";

        return namedParameterJdbcTemplate.query(sql, (rs, rowNum) ->
                new User(
                        rs.getString("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        Role.valueOf(rs.getString("role")),
                        rs.getBoolean("is_verified")
                )
        );
    }
    @Override
    public List<User> findAllPaged(int offset, int limit) {
        String sql = """
        SELECT * FROM app_user
        ORDER BY created_at ASC
        LIMIT :limit OFFSET :offset
    """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("limit", limit)
                .addValue("offset", offset);

        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) ->
                new User(
                        rs.getString("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        Role.valueOf(rs.getString("role")),
                        rs.getBoolean("is_verified")
                )
        );
    }

    @Override
    public long countAllUsers() {
        String sql = "SELECT COUNT(*) FROM app_user";
        return namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Long.class);
    }






}
