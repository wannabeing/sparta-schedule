package org.example.spartaschedule.repository;

import org.example.spartaschedule.dto.ScheduleResponseDto;
import org.example.spartaschedule.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * [Repo] 일정 생성 메서드
     * @param schedule 생성된 일정 객체
     * @return 생성된 일정 응답 객체
     */
    @Override
    public ScheduleResponseDto createSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        // tablaName: schedule, key: id
        jdbcInsert.withTableName("schedule")
                .usingGeneratedKeyColumns("id");

        // 현재시간
        LocalDateTime currentTime = LocalDateTime.now();

        // 데이터 SET
        Map<String, Object> parameters =  new HashMap<>();
        parameters.put("todo", schedule.getTodo());
        parameters.put("writer", schedule.getWriter()); // FIXME: 추후 작성자 ID로 변경
        parameters.put("password", schedule.getPassword());
        parameters.put("createdAt", currentTime);
        parameters.put("updatedAt", currentTime);

        // 저장 후, 생성된 key(id) 값
        Number scheduleId = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        // 새로운 응답 객체 생성 후, 반환
        return new ScheduleResponseDto(scheduleId.longValue(), schedule.getTodo(), schedule.getWriter(), currentTime, currentTime);
    }

    /**
     * [Repo] 전체 일정 리스트를 조회하고 반환하는 메서드
     * 수정일 내림차순 & 작성자명 오름차순 정렬
     *
     * @return 일정 응답 객체 리스트
     */
    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        String query = "SELECT * FROM schedule ORDER BY updated_at DESC, writer ASC";
        return jdbcTemplate.query(query, scheduleResponseDtoMapper());
    }

    /**
     * [Repo] 단일 일정을 조회하고 반환하는 메서드
     * @param id 일정 id
     * @return 조회된 일정 객체가 담긴 Optional 객체
     */
    @Override
    public Optional<ScheduleResponseDto> findScheduleById(Long id) {
        String query = "SELECT * FROM schedule WHERE id = ?";
        List<ScheduleResponseDto> result = jdbcTemplate.query(query, scheduleResponseDtoMapper(), id);

        return result.stream().findFirst();
    }

    /**
     * 조회한 행마다 ScheduleResponseDto 객체로 매핑해주는 메서드
     * @return 일정 응답 객체 리스트
     */
    private RowMapper<ScheduleResponseDto> scheduleResponseDtoMapper(){
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("todo"),
                        rs.getString("writer"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        };
    }
}
