package com.zl.ai.tmall.service;

import com.alibaba.da.coin.ide.spi.meta.ExecuteCode;
import com.alibaba.da.coin.ide.spi.meta.ResultType;
import com.alibaba.da.coin.ide.spi.standard.TaskQuery;
import com.alibaba.da.coin.ide.spi.standard.TaskResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author:lei.zhu
 * @date:2020/11/11 17:26
 */
@Service
@Slf4j
public class OriginServiceImpl implements OriginService{
    @Override
    public TaskResult boot(TaskQuery taskQuery) {

        //从请求中获取意图参数以及参数值
        Map<String, String> paramMap = taskQuery
                .getSlotEntities()
                .stream()
                .collect(
                        Collectors.toMap(slotItem -> slotItem.getIntentParameterName(),
                                slotItem -> slotItem.getStandardValue()));
        log.info("paramMap ：" + paramMap.toString());
        if (taskQuery.getIntentName().equals("起飞")) {
            return executeBoot();
        } else if(taskQuery.getIntentName().equals("降落")){
            return executeShutDown();
        }else{
            return null;
        }
    }
    private TaskResult executeBoot() {
        TaskResult result = new TaskResult();
        //请求服务并填充回复语句
        log.info("============接收到指令，正在给飞机加油============");
        try {
            Process ipconfig = Runtime.getRuntime().exec("ipconfig");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ipconfig.getInputStream(),"GBK"));
            while(bufferedReader.ready()){
                log.info(bufferedReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("===========飞机加油完毕，准备起飞=================");
        try {
            result.setReply("呜呼~起飞，boot~~~~");
        } catch (Exception e) {
            e.printStackTrace();
            result.setReply("呀~飞机没油了，起飞失败");
        }

        result.setExecuteCode(ExecuteCode.SUCCESS);
        result.setResultType(ResultType.RESULT);
        return result;
    }
    private TaskResult executeShutDown(){
        TaskResult result = new TaskResult();
        //请求服务并填充回复语句
        log.info("阿拉~降落，shutDown~~~~");
        result.setExecuteCode(ExecuteCode.SUCCESS);
        result.setResultType(ResultType.RESULT);
        return result;
    }
}
