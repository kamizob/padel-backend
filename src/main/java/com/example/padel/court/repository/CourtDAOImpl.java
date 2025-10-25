package com.example.padel.court.repository;

import com.example.padel.court.domain.Court;
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
        Court court = new Court();
        court.setId(rs.getString("id"));
        court.setName(rs.getString("name"));
        court.setLocation(rs.getString("location"));
        court.setActive(rs.getBoolean("is_active"));
        court.setOpeningTime(rs.getObject("open_time", LocalTime.class));
        court.setClosingTime(rs.getObject("close_time", LocalTime.class));
        court.setSlotMinutes(rs.getInt("slot_minutes"));
        court.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        court.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        return court;
    }



}
