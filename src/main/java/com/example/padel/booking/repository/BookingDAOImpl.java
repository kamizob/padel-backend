package com.example.padel.booking.repository;

import com.example.padel.booking.domain.Booking;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class BookingDAOImpl implements BookingDAO {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BookingDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public int create(Booking booking) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", booking.id())
                .addValue("userId", booking.userId())
                .addValue("courtId", booking.courtId())
                .addValue("startTime", booking.startTime())
                .addValue("endTime", booking.endTime())
                .addValue("isActive", booking.isActive());

        String sql = """
                INSERT INTO booking (id, user_id, court_id, start_time, end_time, is_active)
                VALUES (:id, :userId, :courtId, :startTime, :endTime, :isActive)
                """;
        return namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public List<Booking> findByUserId(String userId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId);
        String sql = """
                SELECT * FROM booking
                WHERE user_id = :userId
                ORDER BY start_time ASC
        """;
        return namedParameterJdbcTemplate.query(sql, params, this::mapRow);

    }
    @Override
    public List<Booking> findByCourtId(String courtId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("courtId", courtId);
        String sql = """
                SELECT * FROM booking
                WHERE court_id = :courtId
                AND is_active = true
        """;
        return namedParameterJdbcTemplate.query(sql, params, this::mapRow);

    }


    private Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Booking(
                rs.getString("id"),
                rs.getString("user_id"),
                rs.getString("court_id"),
                rs.getObject("start_time", LocalDateTime.class),
                rs.getObject("end_time", LocalDateTime.class),
                rs.getBoolean("is_active")
        );
    }
    @Override
    public int cancelBooking(String bookingId, String userId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", bookingId)
                .addValue("userId", userId);
        String sql = """
                UPDATE booking
                SET is_active = false,
                    updated_at = CURRENT_TIMESTAMP
                WHERE id = :id
                AND user_id = :userId
                AND is_active = true
        """;
        return namedParameterJdbcTemplate.update(sql, params);
    }
    @Override
    public List<Booking> findUpcomingActiveBookings(LocalDateTime now, LocalDateTime threeHoursLater) {
        String sql = """
        SELECT * FROM booking
        WHERE is_active = true
          AND reminder_sent = false
          AND start_time BETWEEN :now AND :threeHoursLater
    """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("now", now)
                .addValue("threeHoursLater", threeHoursLater);
        return namedParameterJdbcTemplate.query(sql, params, this::mapRow);
    }

    @Override
    public int markReminderSent(String bookingId) {
        String sql = "UPDATE booking SET reminder_sent = true WHERE id = :id";
        return namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", bookingId));
    }

    @Override
    public List<Booking> findByUserIdPaged(String userId, int offset, int limit) {
        String sql = """
        SELECT * FROM booking
        WHERE user_id = :userId
        ORDER BY start_time DESC
        LIMIT :limit OFFSET :offset
        """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("limit", limit)
                .addValue("offset", offset);

        return namedParameterJdbcTemplate.query(sql, params, this::mapRow);
    }

    @Override
    public long countByUserId(String userId) {
        String sql = """
        SELECT COUNT(*) FROM booking
        WHERE user_id = :userId
        """;

        return namedParameterJdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource("userId", userId),
                Long.class
        );
    }



}
