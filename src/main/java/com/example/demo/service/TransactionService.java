package com.example.demo.service;

import com.example.demo.constant.ErrorCode;
import com.example.demo.domain.TransactionPo;
import com.example.demo.handler.BusinessException;
import com.example.demo.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransactionService {

    private final ConcurrentHashMap<String, TransactionPo> transactions = new ConcurrentHashMap<>();

    /**
     *
     */
    public TransactionPo createTransaction(TransactionPo transactionPo) {
        // 雪花算法 分布式id分片键， 递增顺序
        String transactionId = String.valueOf(IdUtils.getId());
        log.info("Create transaction id:{}", transactionId);
        transactionPo.setId(transactionId);

        if (transactions.putIfAbsent(transactionId, transactionPo) != null) {
            // 重复插入数据
            log.error("Transaction already exists: {}", transactionPo);
            throw new BusinessException(ErrorCode.EXIT_ERROR);
        }

        log.info("New transaction created: {}", transactionId);
        return transactionPo;
    }

    /**
     * 查询可以使用redis 缓存
     *  @Cacheable(value = "transactionId", key = "#id")
     *
     * @param id
     * @return
     */
    public TransactionPo getTransaction(String id) {
        return transactions.computeIfAbsent(id, k -> {
            throw new BusinessException(ErrorCode.SELECT_ERROR);
        });
    }

    /**
     * Get paginated transaction list
     *
     * @param page Page number (0-based)
     * @param size Page size
     * @return List of transactions
     */
    public List<TransactionPo> getTransactions(int page, int size) {
        return transactions.values().stream()
                .sorted(Comparator.comparing(TransactionPo::getTransactionTime).reversed())
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    /**
     * Update existing transaction
     *
     * @param id                 Transaction ID
     * @param updatedTransactionPo Updated transaction data
     * @return Updated transaction object
     */
    public TransactionPo updateTransaction(String id, TransactionPo updatedTransactionPo) {
        TransactionPo existing = getTransaction(id);
        existing.setAmount(updatedTransactionPo.getAmount());
        existing.setType(updatedTransactionPo.getType());
        return existing;
    }

    /**
     * Delete transaction by ID
     *
     * @param id Transaction ID
     */
    public void deleteTransaction(String id) {
        if (!transactions.remove(id, getTransaction(id))) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Transaction not found: " + id);
        }
        log.info("Transaction deleted: {}", id);
    }
}


