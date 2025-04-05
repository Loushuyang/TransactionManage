package com.example.demo.configuration;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SentinelConfig {

    @PostConstruct
    public void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();

        // 配置创建交易接口的限流规则（QPS 5）
        FlowRule createRule = new FlowRule("createTransaction")
                .setGrade(RuleConstant.FLOW_GRADE_QPS)
                .setCount(1);

        // 配置查询接口的限流规则（QPS 10）
        FlowRule queryRule = new FlowRule("getTransaction")
                .setGrade(RuleConstant.FLOW_GRADE_QPS)
                .setCount(1);
        queryRule.setResource("getTransaction");


        rules.add(createRule);
        rules.add(queryRule);
        FlowRuleManager.loadRules(rules);
    }


//    @Bean
//    public BlockExceptionHandler blockExceptionHandler() {
//        return new BlockExceptionHandler() {
//            @Override
//            public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
//                httpServletResponse.setContentType("application/json;charset=utf-8");
//                httpServletResponse.getWriter().write("{\"code\": 429, \"message\": \"请求过于频繁，请稍后再试\"}");
//            }
//
//        };
//    }
}
