package com.example.padel.court.repository;

import com.example.padel.court.domain.Court;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public class CourtDAOImpl implements CourtDAO {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CourtDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    @Override
    public List<Court> findAllActive() {
        String sql = """
                SELECT * FROM court
                WHERE is_active = true
                ORDER BY created_at ASC""";
        return namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> mapRowToCourt(rs));


    }
    private Court mapRowToCourt(ResultSet rs) throws SQLException {
        return new Court(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("location"),
                rs.getBoolean("is_active"),
                rs.getObject("open_time", LocalTime.class),
                rs.getObject("close_time", LocalTime.class),
                rs.getInt("slot_minutes")
        );
    }

    @Override
    public int createCourt(Court court) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id",  court.id())
                .addValue("name",  court.name())
                .addValue("location",  court.location())
                .addValue("is_active", court.isActive())
                .addValue("open_time", court.openingTime())
                .addValue("close_time", court.closingTime())
                .addValue("slot_minutes", court.slotMinutes());

        String sql = """
                INSERT INTO court (id, name, location, is_active, open_time, close_time, slot_minutes)
                VALUES (:id, :name, :location, :is_active, :open_time, :close_time, :slot_minutes)
        """;
        return namedParameterJdbcTemplate.update(sql, params);
    }
    @Override
    public int updateCourtActivity(String id, boolean isActive) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id",  id)
                .addValue("is_active", isActive);
        String sql = """
                UPDATE court 
                SET is_active = :is_active,
                    updated_at = CURRENT_TIMESTAMP
                WHERE id = :id""";
        return namedParameterJdbcTemplate.update(sql, params);
    }
    @Override
    public Court findCourtById(String id) {
        String sql = "SELECT * FROM court WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.query(sql, params, rs -> rs.next() ? mapRowToCourt(rs) : null);
    }
    @Override
    public List<Court> findAll() {
        String sql = "SELECT * FROM court ORDER BY created_at ASC";
        return namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> mapRowToCourt(rs));
    }
    @Override
    public int updateCourtSchedule(String id, LocalTime openTime, LocalTime closeTime, int slotMinutes) {
        String sql = """
                UPDATE court
                SET open_time = :openTime,
                    close_time = :closeTime,
                    slot_minutes = :slotMinutes,
                    updated_at = CURRENT_TIMESTAMP
                WHERE id = :id
    """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("openTime", openTime)
                .addValue("closeTime", closeTime)
                .addValue("slotMinutes", slotMinutes);

        return namedParameterJdbcTemplate.update(sql, params);
    }
    @Override
    public List<Court> findPaged(int page, int size) {
        String sql = """
        SELECT * FROM court
        ORDER BY created_at ASC
        LIMIT :limit OFFSET :offset
    """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("limit", size)
                .addValue("offset", (page - 1) * size);
        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> mapRowToCourt(rs));
    }

    @Override
    public int countCourts() {
        String sql = "SELECT COUNT(*) FROM court";
        return namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Integer.class);
    }
    @Override
    public List<Court> findPagedActive(int page, int size) {
        String sql = """
        SELECT * FROM court
        WHERE is_active = true
        ORDER BY created_at ASC
        LIMIT :limit OFFSET :offset
    """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("limit", size)
                .addValue("offset", (page - 1) * size);

        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> mapRowToCourt(rs));
    }

    @Override
    public int countActiveCourts() {
        String sql = "SELECT COUNT(*) FROM court WHERE is_active = true";
        return namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Integer.class);
    }






}
