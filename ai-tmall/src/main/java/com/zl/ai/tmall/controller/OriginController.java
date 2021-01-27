package com.zl.ai.tmall.controller;

import com.alibaba.da.coin.ide.spi.standard.ResultModel;
import com.alibaba.da.coin.ide.spi.standard.SecurityWrapperTaskQuery;
import com.alibaba.da.coin.ide.spi.standard.TaskQuery;
import com.alibaba.da.coin.ide.spi.standard.TaskResult;
import com.alibaba.da.coin.ide.spi.trans.MetaFormat;
import com.zl.ai.tmall.service.OriginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:lei.zhu
 * @date:2020/11/11 17:14
 */
@RestController
@Slf4j
public class OriginController {
    @Autowired
    private OriginService originService;

    /**
     *
     * @param taskQuery
     * @return
     */
    @PostMapping(value = "/tmall/api/boot")
    public ResultModel<TaskResult> getResponse(@RequestBody String taskQuery) {
        /**
         * 将开发者平台识别到的语义理解的结果（json字符串格式）转换成TaskQuery
         */
        log.info("TaskQuery:{}", taskQuery.toString());
        TaskQuery query = MetaFormat.parseToQuery(taskQuery);

        /**
         * 构建服务返回结果
         */
        ResultModel<TaskResult> resultModel = new ResultModel<TaskResult>();
        try {
            /**
             * 调用天气服务执行并构建回复内容
             */
            TaskResult result = originService.boot(query);
            resultModel.setReturnCode("0");
            resultModel.setReturnValue(result);
        } catch (Exception e) {
            resultModel.setReturnCode("-1");
            resultModel.setReturnErrorSolution(e.getMessage());
        }
        /**
         * 直接返回ResultModel<TaskResult>对象就ok
         */
        return resultModel;
    }
}
