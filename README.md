swagger  http://localhost:8080/swagger-ui.html

1、新增接口 http://localhost:8080/api/transactions/create   content-type: application/json
  入参  ： {
"amount":"728.3",
"type":"XF",
"userId":"test"
}

2、根据id查询交易  http://localhost:8080/api/transactions/selectById    content-type:form-data
id : 222

3、查询所有交易 GET   http://localhost:8080/api/transactions/getTransList 

4、删除交易 http://localhost:8080/api/transactions/deleteById content-type:form-data
id : 222

5、更新交易 http://localhost:8080/api/transactions/updateById content-type: application/json
{
"amount":"728.3",
"type":"XF",
"userId":"test",
"id":"2345"
}


