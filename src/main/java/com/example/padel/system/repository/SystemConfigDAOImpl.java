package com.example.padel.system.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SystemConfigDAOImpl implements SystemConfigDAO {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public SystemConfigDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public boolean isAdminInitialized() {
        String sql = "SELECT admin_initialized FROM system_config LIMIT 1";
        Boolean flag = namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Boolean.class);
        return flag != null && flag;
    }

    @Override
    public int markAdminInitialized() {
        String sql = "UPDATE system_config SET admin_initialized = TRUE";
        return namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource());
    }
}
