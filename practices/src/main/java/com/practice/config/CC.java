package com.practice.config;

import com.practice.cache.RedisNode;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.impl.ConfigBeanImpl;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lxl
 * @Date 2018/4/28 16:05
 */
public interface CC {
    Config cfg = load();

    static Config load(){
        Config config = ConfigFactory.load();
        String custom_conf = "mp.conf";
        if(config.hasPath(custom_conf)){
            File file = new File(config.getString(custom_conf));
            if(file.exists()){
                Config custom = ConfigFactory.parseFile(file);
                config = custom.withFallback(config);
            }
        }

        return config;
    }


    interface redis{
        Config cfg = CC.cfg.getObject("redis").toConfig();
        String password = cfg.getString("password");
        String clusterModel = cfg.getString("cluster-model");
        String sentinelMaster = cfg.getString("sentinel-master");

        List<RedisNode> nodes = cfg.getList("nodes")
                .stream()
                .map(v -> RedisNode.from(v.unwrapped().toString()))
                .collect(Collectors.toList());

        static boolean isCluster(){

            return "cluster".equalsIgnoreCase(clusterModel);
        }

        static boolean isSentinel(){

            return "sentinel".equalsIgnoreCase(sentinelMaster);
        }

        static <T> T getPoolConfig(Class<T> clazz){
            return ConfigBeanImpl.createInternal(cfg.getObject("config").toConfig(),clazz);
        }
    }

    interface baidu{
        Config cfg = CC.cfg.getObject("baidu").toConfig();
        interface app{
            Config cfg = baidu.cfg.getObject("app").toConfig();
            String id = cfg.getString("id");
            String key = cfg.getString("key");
            String secret = cfg.getString("secret");

            static String getId(){
                return id;
            }

            static String getKey(){
                return key;
            }
            static String getSecret(){
                return secret;
            }
        }
    }
}
