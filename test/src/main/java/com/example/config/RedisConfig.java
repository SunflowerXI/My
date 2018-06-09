package com.example.config;

import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

 
import com.fasterxml.jackson.annotation.JsonAutoDetect;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer; 

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

  
 
 


@Configuration  
@ConditionalOnClass({JedisCluster.class})  
public class RedisConfig {  
	
    @Value("${spring.redis.cluster.nodes}")  
    private String clusterNodes;  
    
    @Value("${spring.redis.timeout}")  
    private int timeout;  
    
    @Value("${spring.redis.pool.max-idle}")  
    private int maxIdle;  
    
    @Value("${spring.redis.pool.max-wait}")  
    private long maxWaitMillis;  
    
    @Value("${spring.redis.commandTimeout}")  
    private int commandTimeout;  
 
   /* @Bean 
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override 
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder(); 
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				Cacheable cacheable = method.getAnnotation(Cacheable.class); 
				if (cacheable != null) {
					value = cacheable.value(); 
				} 
				CachePut cachePut = method.getAnnotation(CachePut.class); 
				if (cachePut != null) {
					value = cachePut.value(); 
				} 
				CacheEvict cacheEvict = method.getAnnotation(CacheEvict.class); 
				if (cacheEvict != null) {
					value = cacheEvict.value(); 
				}
				sb.append(value[0]); 
				for (Object obj : params) {
					sb.append(":" + obj.toString()); 
				} return sb.toString(); 
			};
		}; 
	};*/
    
    @Bean
    public KeyGenerator wiselyKeyGenerator(){
    	return new KeyGenerator() {
			@Override
			public Object generate(Object target,Method method,Object... params) {
				StringBuilder sb=new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for(Object object:params){
					sb.append(object.toString());
				}
				return sb.toString();
			}
		};
    }
    
    @Bean  
    public JedisCluster getJedisCluster() {  
        String[] cNodes = clusterNodes.split(",");  
        Set<HostAndPort> nodes =new HashSet<>();  
        //分割出集群节点  
        for(String node : cNodes) {  
            String[] hp = node.split(":");  
            nodes.add(new HostAndPort(hp[0],Integer.parseInt(hp[1])));  
        }  
        JedisPoolConfig jedisPoolConfig =new JedisPoolConfig();  
        jedisPoolConfig.setMaxIdle(maxIdle);  
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);  
        //创建集群对象  
        return new JedisCluster(nodes,commandTimeout,jedisPoolConfig);  
    }  
  
    /** 
     * 设置数据存入redis 的序列化方式 
     *</br>redisTemplate序列化默认使用的jdkSerializeable,存储二进制字节码,导致key会出现乱码，所以自定义 
     *序列化类 
     * 
     * @paramredisConnectionFactory 
     */  
    @Bean  
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)throws UnknownHostException {  
        RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>();  
        redisTemplate.setConnectionFactory(redisConnectionFactory); 
        setSerializer(redisTemplate);
        
        redisTemplate.setKeySerializer(new StringRedisSerializer());  
        redisTemplate.afterPropertiesSet();  
        return redisTemplate;  
    }  
    
    private void setSerializer(RedisTemplate<Object, Object> redisTemplate) {
    	 Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =new Jackson2JsonRedisSerializer(Object.class);  
         ObjectMapper objectMapper =new ObjectMapper();  
         objectMapper.setVisibility(PropertyAccessor.ALL,JsonAutoDetect.Visibility.ANY);  
         objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);  
         jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
         redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
	}
  
    @Bean
    public CacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate){
    	RedisCacheManager cacheManager=new RedisCacheManager(redisTemplate);
    	cacheManager.setDefaultExpiration(10000);
		return cacheManager;
    }
}  