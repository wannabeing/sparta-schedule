package org.example.spartaschedule.repository;

import org.example.spartaschedule.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * [Repo] 유저를 조회하는 메서드
     * @param id 유저 id
     * @return 조회된 유저 객체가 담긴 Optional 객체
     */
    @Override
    public Optional<User> findUserById(Long id) {
        String query = "SELECT * FROM user WHERE id = ?";
        List<User> result = jdbcTemplate.query(query, userRowMapper(), id);

        return result.stream().findFirst();
    }

    /**
     * 조회한 행마다 User 객체로 매핑해주는 메서드
     * @return 유저 객체 리스트
     */
    private RowMapper<User> userRowMapper(){
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        };
    }
}
