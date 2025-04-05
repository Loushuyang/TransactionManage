package com.example.demo.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.example.demo.constant.ErrorCode;
import com.example.demo.domain.BaseResponse;
import com.example.demo.domain.TransactionPo;
import com.example.demo.domain.TransactionRequest;
import com.example.demo.handler.BusinessException;
import com.example.demo.service.TransactionService;
import com.example.demo.utils.ResultUtils;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.demo.constant.ErrorCode.BLOCK_ERROR;

@Api(tags = "交易系统api")
@RestController
@RequestMapping("/api/transactions")
@Validated
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Create new transaction
     * @param request Transaction request data
     * @return Created transaction
     */
    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    @SentinelResource(
            value = "createTransaction",
            blockHandler = "blockExceptionHandler",
            blockHandlerClass = BlockHandler.class)
    public BaseResponse<TransactionPo> create(@RequestBody @Valid TransactionRequest request) {
        TransactionPo transactionPo = new TransactionPo();
        transactionPo.setAmount(request.getAmount());
        transactionPo.setType(request.getType());
        transactionPo.setUserId(request.getUserId());
        transactionPo = transactionService.createTransaction(transactionPo);
        return ResultUtils.success(transactionPo);
    }

    /**
     * Get transaction by ID
     * @param id Transaction ID
     * @return Transaction details
     */
    @PostMapping("/selectById")
    public BaseResponse<TransactionPo> selectTransById(@RequestParam String id) {
        TransactionPo transactionPo = transactionService.getTransaction(id);
        if (transactionPo == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtils.success(transactionPo);
    }

    /**
     * Get paginated transaction list
     * @param page Page number (default: 0)
     * @param size Page size (default: 10)
     *  分頁模式 从0开始每页10条
     * @return List of transactions
     */
    @GetMapping(value = "getTransList")
    @SentinelResource(
            value = "getTransaction",
            blockHandler = "blockExceptionHandler",
            blockHandlerClass = BlockHandler.class)
    public BaseResponse<List<TransactionPo>> getTranslist(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<TransactionPo> transactionPos = transactionService.getTransactions(page, size);
        return ResultUtils.success(transactionPos);
    }


    /**
     * Update existing transaction
     * @param request Updated transaction data
     * @return Updated transaction
     */
    @PostMapping("/updateById")
    public BaseResponse<TransactionPo> updateById(@RequestBody @Valid TransactionRequest request) {
        TransactionPo transactionPo = new TransactionPo();
        transactionPo.setAmount(request.getAmount());
        transactionPo.setType(request.getType());
        transactionPo = transactionService.updateTransaction(request.getId(), transactionPo);
        return ResultUtils.success(transactionPo);
    }

    /**
     * Delete transaction by ID
     * @param id Transaction ID
     */
    @PostMapping("/deleteById")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public BaseResponse deleteById(@RequestParam String id) {
        transactionService.deleteTransaction(id);
        return ResultUtils.success();
    }

    public static class BlockHandler {
        public static BaseResponse blockExceptionHandler(BlockException exception) {
            return ResultUtils.error(BLOCK_ERROR.getCode(), BLOCK_ERROR.getMessage());
        }
    }

}
