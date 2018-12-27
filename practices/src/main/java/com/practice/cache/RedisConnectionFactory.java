package com.practice.cache;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.util.Pool;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Connection factory creating <a href="http://github.com/xetorthio/jedis">Jedis</a> based connections.
 * @author lxl
 * @Date 2018/4/28 11:32
 */
public class RedisConnectionFactory {
    private final static Logger log = LoggerFactory.getLogger(RedisConnectionFactory.class);

    private String hostName = "localhost";
    private int port = Protocol.DEFAULT_PORT;
    private int timeout = Protocol.DEFAULT_TIMEOUT;
    private String password;
    private String sentineMaster;
    private List<RedisNode> redisServers;
    private boolean isCluster = false;
    private int dbIndex = 0;

    private JedisShardInfo shardInfo;
    private Pool<Jedis> pool;
    private JedisCluster cluster;
    private JedisPoolConfig poolConfig = new JedisPoolConfig();


    public RedisConnectionFactory() {
    }

    protected Jedis fetchJedisConnector(){
        try {
            if(pool != null){
                return pool.getResource();
            }

            Jedis jedis = new Jedis(getShardInfo());
            jedis.connect();

            return jedis;
        } catch (Exception e) {
            throw new RuntimeException("Cannot get Jedis connection {}",e);
        }
    }


    public void init(){
        if(shardInfo == null){
            shardInfo = new JedisShardInfo(hostName,port);
            if(StringUtils.isNotEmpty(password)){
                shardInfo.setPassword(password);
            }
            if(timeout > 0){
                shardInfo.setConnectionTimeout(timeout);
            }
        }

        if(isCluster){
            this.cluster = createCluster();
        }else {
            this.pool = createPool();
        }
    }

    public JedisShardInfo getShardInfo() {

        return shardInfo;
    }

    private Pool<Jedis> createPool(){
        if(StringUtils.isNotBlank(sentineMaster)){
            return createRedisSentinelPool();
        }

        return createRedisPool();
    }


    protected Pool<Jedis> createRedisPool(){
        return new JedisPool(getPoolConfig(),shardInfo.getHost(),shardInfo.getPort(),
                shardInfo.getSoTimeout(),shardInfo.getPassword());
    }

    public JedisPoolConfig getPoolConfig() {
        return poolConfig;
    }

    protected Pool<Jedis> createRedisSentinelPool(){
        Set<String> hostAndPorts = redisServers
                .stream()
                .map(redisNode -> new HostAndPort(redisNode.getHost(),redisNode.getPort()).toString())
                .collect(Collectors.toSet());

        return new JedisSentinelPool(sentineMaster,hostAndPorts,poolConfig,getShardInfo().getSoTimeout(),getShardInfo().getPassword());
    }

    protected JedisCluster createCluster(){
        Set<HostAndPort> hostAndPorts = redisServers
                .stream()
                .map(redisNode -> new HostAndPort(redisNode.getHost(),redisNode.getPort()))
                .collect(Collectors.toSet());

        if(StringUtils.isNotEmpty(getPassword())){
            throw  new IllegalArgumentException("Jedis does not support password protected Redis Cluster configurations!");
        }

        int redirects = 5;

        return new JedisCluster(hostAndPorts,timeout,redirects,poolConfig);
    }


    public String getPassword() {
        return password;
    }

    public void destroy(){
        if(pool != null){
            try{
                pool.destroy();
            }catch (Exception e){
                log.warn("Cannot properly close Jedis pool {}",e);
            }
            pool = null;
        }

        if(cluster != null){
            try{
                cluster.close();
            }catch (Exception e){
                log.warn("Cannot properly close Jedis pool {}",e);
            }
            cluster = null;
        }
    }

    public JedisCluster getClusterConnection(){

        return cluster;
    }

    public boolean isCluster(){

        return isCluster;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getSentineMaster() {
        return sentineMaster;
    }

    public void setSentineMaster(String sentineMaster) {
        this.sentineMaster = sentineMaster;
    }

    public List<RedisNode> getRedisServers() {
        return redisServers;
    }

    public void setRedisServers(List<RedisNode> redisServers) {
        if(redisServers == null || redisServers.isEmpty()){
            throw new IllegalArgumentException("redis server node can not be empty, please check your conf.");
        }

        this.redisServers = redisServers;
        this.hostName = redisServers.get(0).getHost();
        this.port = redisServers.get(0).getPort();
    }

    public Jedis getJedisConnection() {
        Jedis jedis = fetchJedisConnector();
        if (dbIndex > 0 && jedis != null) {
            jedis.select(dbIndex);
        }
        return jedis;
    }

    public void setCluster(boolean cluster) {
        isCluster = cluster;
    }

    public int getDbIndex() {
        return dbIndex;
    }

    public void setDbIndex(int dbIndex) {
        this.dbIndex = dbIndex;
    }

    public void setShardInfo(JedisShardInfo shardInfo) {
        this.shardInfo = shardInfo;
    }

    public Pool<Jedis> getPool() {
        return pool;
    }

    public void setPool(Pool<Jedis> pool) {
        this.pool = pool;
    }

    public JedisCluster getCluster() {
        return cluster;
    }

    public void setCluster(JedisCluster cluster) {
        this.cluster = cluster;
    }

    public void setPoolConfig(JedisPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }
}

