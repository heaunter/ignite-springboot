package com.iss.users.config;

import com.iss.users.model.Person;
import com.iss.users.model.Role;
import com.iss.users.service.PersonService;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.ClientConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @program: users
 * @author: 李泰郎
 * @create: 2018-02-27 19:08
 **/

@Configuration
public class IgniteCfg {

    /**
     * 初始化ignite节点信息
     *
     * @return Ignite
     */
//    @Bean
    public Ignite igniteInstance() {
        // 配置一个节点的Configuration

        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setClientMode(false);
        cfg.setMetricsLogFrequency(60000);

        TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
        ipFinder.setAddresses(Collections.singletonList("127.0.0.1:47500..47509"));

        TcpDiscoverySpi tcpDiscoverySpi = new TcpDiscoverySpi();
        tcpDiscoverySpi.setIpFinder(ipFinder);
        cfg.setDiscoverySpi(tcpDiscoverySpi);


        // 设置该节点名称
        cfg.setIgniteInstanceName("springDataNode");

        // 启用Peer类加载器
        cfg.setPeerClassLoadingEnabled(false);

        // 创建一个Cache的配置，名称为PersonCache
        CacheConfiguration ccfg = new CacheConfiguration("PersonCache");

        // 设置这个Cache的键值对模型
        ccfg.setIndexedTypes(Long.class, Person.class);

        // 把这个Cache放入springDataNode这个Node中
        cfg.setCacheConfiguration(ccfg);

        // 启动这个节点
        Ignite start = Ignition.start(cfg);
        return start;
    }

//    @Bean
    public IgniteClient igniteClient() {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setAddresses("127.0.0.1:10800");
        IgniteClient igniteClient = Ignition.startClient(clientConfiguration);
        return igniteClient;
    }

}
