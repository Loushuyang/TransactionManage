package com.example.demo.service;

import com.example.demo.domain.TransactionPo;
import com.example.demo.handler.BusinessException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    ConcurrentHashMap<String, TransactionPo> transactionMap = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, TransactionPo> transactions;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        TransactionPo transactionPo = new TransactionPo();
        transactionPo.setType("XF");
        transactionPo.setUserId("sss");
        transactionPo.setAmount(new BigDecimal(0));
        transactionPo.setId("111");
        transactionMap.put(transactionPo.getId(), transactionPo);

        transactionService = new TransactionService();
        transactions = new ConcurrentHashMap<>();
        // 使用反射设置私有字段
        try {
            java.lang.reflect.Field field = TransactionService.class.getDeclaredField("transactions");
            field.setAccessible(true);
            field.set(transactionService, transactions);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testcreateTransaction_fail() {
        TransactionPo transactionPo = new TransactionPo();
        transactionPo.setType("XF");
        transactionPo.setUserId("sss");
        transactionPo.setAmount(new BigDecimal(0));
        transactionPo.setId("111");
        try {
            transactionService.createTransaction(transactionPo);
        } catch (BusinessException ex) {
            Assert.assertEquals(1008, ex.getCode());
        }
    }

    @Test
    public void testcreateTransaction_success() {
        TransactionPo transactionPo = new TransactionPo();
        transactionPo.setType("XF");
        transactionPo.setUserId("sss");
        transactionPo.setAmount(new BigDecimal(0));
        transactionPo.setId("111111");
        TransactionPo po = transactionService.createTransaction(transactionPo);
        Assert.assertEquals(transactionPo.getId(), po.getId());
    }

    @Test
    public void getTransactionById() {
        TransactionPo transactionPo = new TransactionPo();
        transactionPo.setId("111");
        try {
            TransactionPo po = transactionService.getTransaction(transactionPo.getId());
        } catch (BusinessException ex) {
            Assert.assertEquals(1006, ex.getCode());
        }
    }


    @Test
    public void updateById() {
        TransactionPo transactionPo = new TransactionPo();
        transactionPo.setId("444");
        transactionPo.setAmount(new BigDecimal(0));
        transactionPo.setType("XF");
        transactionPo.setUserId("test");

        transactionMap.put(transactionPo.getId(), transactionPo);

        TransactionPo transactionPo1 = new TransactionPo();
        transactionPo1.setId("444");
        transactionPo1.setAmount(new BigDecimal(3));
        transactionPo1.setType("XF");
        transactionPo1.setUserId("sss");
        try {
            TransactionPo result = transactionService.updateTransaction(transactionPo1.getId(), transactionPo1);
        } catch (BusinessException ex) {
            Assert.assertEquals(1006, ex.getCode());
        }

    }


    @Test
    public void testUpdateTransaction() {
        TransactionPo transactionPo = new TransactionPo();
        String id = "123";
        transactions.put(id, transactionPo);
        TransactionPo updatedTransactionPo = new TransactionPo();
        updatedTransactionPo.setAmount(new BigDecimal(100));
        updatedTransactionPo.setType("newType");
        TransactionPo result = transactionService.updateTransaction(id, updatedTransactionPo);
        Assert.assertEquals(new BigDecimal(100), result.getAmount());
        Assert.assertEquals("newType", result.getType());
    }

    @Test
    public void testDeleteTransaction() {
        TransactionPo transactionPo = new TransactionPo();
        String id = "123";
        transactions.put(id, transactionPo);
        TransactionPo delTransactionPo = new TransactionPo();
        delTransactionPo.setAmount(new BigDecimal(100));
        delTransactionPo.setType("newType");
        transactionService.deleteTransaction(id);
        Assert.assertTrue(!transactions.containsKey(id));
    }

    @Test
    public void testGetTransactions() {
        for (int i = 0; i < 10; i++) {
            TransactionPo transactionPo = new TransactionPo();
            transactionPo.setTransactionTime(LocalDateTime.now());
            transactions.put(String.valueOf(i), transactionPo);
        }
        List<TransactionPo> result = transactionService.getTransactions(0, 5);
        Assert.assertEquals(5, result.size());
        List<TransactionPo> sorted = transactions.values().stream()
                .sorted(Comparator.comparing(TransactionPo::getTransactionTime).reversed())
                .collect(Collectors.toList());
        Assert.assertEquals(sorted.subList(0, 5), result);
    }
}
