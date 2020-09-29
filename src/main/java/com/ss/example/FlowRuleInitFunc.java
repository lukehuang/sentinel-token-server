package com.ss.example;

import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterFlowRuleManager;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

/**
 * FlowRuleInitFunc
 *
 * @author shisong
 * @date 2020/9/29
 */
public class FlowRuleInitFunc implements InitFunc {

    /**
     * nacos地址 standalone
     */
    private final String serverAddr = "192.168.137.1:8848";
    private final String groupId = "SENTINEL_GROUP";
    private final String dataId = "-flow-rule";

    @Override
    public void init() throws Exception {
        ClusterFlowRuleManager.setPropertySupplier(namespace ->{
            ReadableDataSource<String,List<FlowRule>> dataSource = new NacosDataSource<List<FlowRule>>(serverAddr, groupId, namespace + dataId, new Converter<String, List<FlowRule>>() {

                @Override
                public List<FlowRule> convert(String source) {
                    return JSON.parseObject(source,new TypeReference<List<FlowRule>>(){});
                }
            });
            return dataSource.getProperty();
        });
    }
}
