package com.ss.example;

import com.alibaba.csp.sentinel.cluster.server.ClusterTokenServer;
import com.alibaba.csp.sentinel.cluster.server.SentinelDefaultTokenServer;
import com.alibaba.csp.sentinel.cluster.server.config.ClusterServerConfigManager;
import com.alibaba.csp.sentinel.cluster.server.config.ServerTransportConfig;

import java.util.Collections;

/**
 * ClusterServer
 *
 * @author shisong
 * @date 2020/9/29
 */
public class ClusterServer {

    public static void main(String[] args) throws Exception{
        ClusterTokenServer clusterTokenServer = new SentinelDefaultTokenServer();

        //手动载入namespace和serverTransportConfig的配置到ClusterServerConfigManager
        //集群限流服务端通信相关配置
        ClusterServerConfigManager.loadGlobalTransportConfig(new ServerTransportConfig(9999,600));
        //加载namespace集合列表() ， namespace也可以放在配置中心
        ClusterServerConfigManager.loadServerNamespaceSet(Collections.singleton("App-Test"));
        clusterTokenServer.start();
        //Token-client会上报自己的project.name到token-server。Token-server会根据namespace来统计连接数
    }

}
