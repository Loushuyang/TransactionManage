package com.example.demo.controller;

import com.example.demo.domain.BaseResponse;
import com.example.demo.domain.TransactionPo;
import com.example.demo.domain.TransactionRequest;
import com.example.demo.handler.BusinessException;
import com.example.demo.service.TransactionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateTransaction() {

        TransactionPo transactionPo = new TransactionPo();
        transactionPo.setUserId("test");
        transactionPo.setId("w12222");
        transactionPo.setAmount(new BigDecimal(8.01));
        transactionPo.setType("XF");
        when(transactionService.createTransaction(any())).thenReturn(transactionPo);
        TransactionRequest request = new TransactionRequest();
        request.setAmount(new BigDecimal(8.01));
        request.setType("XF");
        request.setUserId("test");

        BaseResponse<TransactionPo> response = transactionController.create(request);
        Assert.assertEquals(1000, response.getCode());
    }


    @Test
    public void testSelectById() {

        TransactionPo transactionPo = new TransactionPo();
        transactionPo.setUserId("test");
        transactionPo.setId("12222");
        transactionPo.setAmount(new BigDecimal(8.01));
        transactionPo.setType("XF");
        when(transactionService.getTransaction(anyString())).thenReturn(transactionPo);
        try {
            BaseResponse<TransactionPo> response = transactionController.selectTransById("12222");
        } catch (BusinessException e) {
            Assert.assertEquals(1005, e.getCode());
        }
    }

    @Test
    public void testGetTransList() {

        TransactionPo transactionPo = new TransactionPo();
        transactionPo.setUserId("test");
        transactionPo.setId("12222");
        transactionPo.setAmount(new BigDecimal(8.01));
        transactionPo.setType("XF");

        List<TransactionPo> transactionPoList = new ArrayList<>();
        transactionPoList.add(transactionPo);

        when(transactionService.getTransactions(1, 3)).thenReturn(transactionPoList);
        BaseResponse<List<TransactionPo>> listBaseResponse = transactionController.getTranslist(1, 2);
        Assert.assertEquals(listBaseResponse.getCode(), 1000);
    }


    @Test
    public void testUpdateById() {

        TransactionRequest request = new TransactionRequest();
        request.setAmount(new BigDecimal(3));
        request.setType("XF");
        request.setUserId("test");

        TransactionPo transactionPo = new TransactionPo();
        transactionPo.setUserId("test");
        transactionPo.setId("12222");
        transactionPo.setAmount(new BigDecimal(8.01));
        transactionPo.setType("XF");

        when(transactionService.updateTransaction("12222", transactionPo)).thenReturn(transactionPo);
        BaseResponse<TransactionPo> transactionres = transactionController.updateById(request);
        Assert.assertEquals(1000, transactionres.getCode());
    }

    @Test
    public void testDeleteById() {

        Mockito.doNothing().when(transactionService).deleteTransaction(anyString());
        BaseResponse baseResponse = transactionController.deleteById("s11");
        Assert.assertEquals(1000, baseResponse.getCode());
    }

}
