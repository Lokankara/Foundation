package com.validator.dao;

import com.validator.entity.Root;
import com.validator.exception.DaoException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class JdbcRootDao implements RootDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveRoot(final Root root) {
        String sql = "INSERT INTO root (root, equation_id) VALUES (?, ?)";
        boolean update = jdbcTemplate.update(sql, root.getRoot(), root.getEquation().getId()) > 0;
        log.info(String.valueOf(update));
    }

    @Override
    public List<Root> findAll() throws DaoException {
        try {
            String sql = "SELECT * FROM root";
            return jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Root.class));
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
