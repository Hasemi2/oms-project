package com.dev.oms.orders;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static com.dev.oms.utils.DateTimeUtils.dateTimeOf;
import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Repository
public class JDBCReviewRepository implements ReviewRepository {

    private final JdbcTemplate jdbcTemplate;

    public JDBCReviewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long create(Long userId, Long productSeq, String content) {
        checkArgument(isNotEmpty(content), "content must be provided");
        checkArgument(
                isEmpty(content) || content.length() <= 1000,
                "content length must be less than 1000 characters"
        );

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO reviews(user_seq, product_seq, content) VALUES (? ,? ,?)" , Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, userId);
            ps.setLong(2, productSeq);
            ps.setString(3, content);
            return ps;
        }, keyHolder);

        Long seq = (long) keyHolder.getKeys().get("seq");

        return seq;
    }

    @Override
    public Optional<Review> findById(Long id) {
        List<Review> results = jdbcTemplate.query(
                "SELECT * FROM reviews WHERE seq=?",
                mapper,
                id
        );
        return ofNullable(results.isEmpty() ? null : results.get(0));
    }

    static RowMapper<Review> mapper = (rs, rowNum) ->
            new Review(rs.getLong("seq")
                    , rs.getLong("product_seq")
                    , rs.getString("content")
                    , dateTimeOf(rs.getTimestamp("create_at")));
}
