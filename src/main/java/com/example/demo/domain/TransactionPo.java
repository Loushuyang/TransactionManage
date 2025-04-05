package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by loushuyang on 2025/4/3.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionPo {
    /**
     * 使用idworker 雪花算法 替代自增ID
     * 交易id
     */
    private String id;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 交易类型 消费 XF 或退款TK
     */
    private String type;

    /**
     * 交易时间
     */
    private LocalDateTime transactionTime = LocalDateTime.now();

    /**
     *  交易userid
     */
    private String userId;
}
