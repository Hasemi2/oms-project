package com.dev.oms.orders;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.dev.oms.utils.DateTimeUtils.dateTimeOf;
import static java.util.Optional.ofNullable;

@Repository
public class JDBCOrderRepository implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public JDBCOrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Optional<Order> findById(long id) {
        List<Order> results = jdbcTemplate.query(
                "SELECT ord.seq , ord.user_seq , ord.product_seq, ord.review_seq , ord.product_seq " +
                        " , re.content, re.create_at , ord.state, ord.request_msg , ord.reject_msg " +
                        ", ord.completed_at , ord.rejected_at , ord.create_at" +
                        "  FROM " +
                        " orders ord" +
                        " LEFT JOIN reviews re" +
                        " ON ord.review_seq = re.seq" +
                        " WHERE ord.seq=?",
                mapper,
                id
        );
        return ofNullable(results.isEmpty() ? null : results.get(0));
    }

    @Override
    public List<Order> findAll(long offset, int size) {
        return jdbcTemplate.query(
                "SELECT ord.seq , ord.user_seq , ord.product_seq, ord.review_seq , ord.product_seq " +
                        " , re.content, re.create_at , ord.state, ord.request_msg , ord.reject_msg " +
                        ", ord.completed_at , ord.rejected_at , ord.create_at" +
                        "  FROM " +
                        " orders ord" +
                        " LEFT JOIN reviews re" +
                        " ON ord.review_seq = re.seq" +
                        " ORDER BY seq DESC " +
                        " LIMIT " + size +
                        " OFFSET  " + offset,
                mapper
        );
    }

    @Override
    public void update(Long reviewId, Long orderId) {
        jdbcTemplate.update(
                "UPDATE orders SET review_seq =? WHERE seq=?",
                reviewId,
                orderId
        );
    }

    @Override
    public void update(Order order) {
        jdbcTemplate.update(
                "UPDATE orders SET state = ? ,  reject_msg = ?, completed_at =? , rejected_at=? WHERE seq= ?",
                order.getState().toString(),
                order.getRejectMsg(),
                order.getCompletedAt(),
                order.getRejectedAt(),
                order.getSeq()
        );
    }


    static RowMapper<Order> mapper = (rs, rowNum) ->
            new Order.Builder()
                    .seq(rs.getLong("seq"))
                    .userSeq(rs.getLong("user_seq"))
                    .productSeq(rs.getLong("product_seq"))
                    .review(new Review(rs.getLong("review_seq"), rs.getLong("product_seq"), rs.getString("content"), dateTimeOf(rs.getTimestamp("create_at"))))
                    .state(OrderState.values()[rs.getInt("state")])
                    .requestMsg(rs.getString("request_msg"))
                    .rejectMsg(rs.getString("reject_msg"))
                    .completedAt(dateTimeOf(rs.getTimestamp("completed_at")))
                    .rejectedAt(dateTimeOf(rs.getTimestamp("rejected_at")))
                    .createAt(dateTimeOf(rs.getTimestamp("create_at")))
                    .build();


}




