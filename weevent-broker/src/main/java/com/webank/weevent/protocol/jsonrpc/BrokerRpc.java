package com.webank.weevent.protocol.jsonrpc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.webank.weevent.broker.plugin.IProducer;
import com.webank.weevent.sdk.BrokerException;
import com.webank.weevent.sdk.SendResult;
import com.webank.weevent.sdk.TopicInfo;
import com.webank.weevent.sdk.TopicPage;
import com.webank.weevent.sdk.WeEvent;
import com.webank.weevent.sdk.jsonrpc.IBrokerRpc;

import com.alibaba.fastjson.JSON;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implement of JsonRpc service.
 *
 * @author matthewliu
 * @since 2018/11/21
 */
@Slf4j
@AutoJsonRpcServiceImpl
@Component
public class BrokerRpc implements IBrokerRpc {
    private IProducer producer;

    @Autowired
    public void setProducer(IProducer producer) {
        this.producer = producer;
    }

    @Override
    public SendResult publish(@JsonRpcParam(value = "topic") String topic,
                              @JsonRpcParam(value = "groupId") String groupId,
                              @JsonRpcParam(value = "content") byte[] content,
                              @JsonRpcParam(value = "extensions") Map<String, String> extensions) throws BrokerException {
        log.info("topic:{} groupId:{} content.length:{} extensions:{}", topic, groupId, content.length, JSON.toJSONString(extensions));

        return this.producer.publish(new WeEvent(topic, content, extensions), groupId);
    }

    @Override
    public SendResult publish(@JsonRpcParam(value = "topic") String topic,
                              @JsonRpcParam(value = "content") byte[] content,
                              @JsonRpcParam(value = "extensions") Map<String, String> extensions) throws BrokerException {
        log.info("topic:{} contentLength:{} extensions:{}", topic, content.length, JSON.toJSONString(extensions));

        return this.producer.publish(new WeEvent(topic, content, extensions), "");
    }

    @Override
    public SendResult publish(@JsonRpcParam(value = "topic") String topic,
                              @JsonRpcParam(value = "content") byte[] content) throws BrokerException {
        log.info("topic:{} content.length:{}", topic, content.length);

        return this.producer.publish(new WeEvent(topic, content, new HashMap<>()), "");
    }

    @Override
    public SendResult publish(@JsonRpcParam(value = "topic") String topic,
                              @JsonRpcParam(value = "groupId") String groupId,
                              @JsonRpcParam(value = "content") byte[] content) throws BrokerException {
        log.info("topic:{} groupId:{} content.length:{}", topic, groupId, content.length);

        return this.producer.publish(new WeEvent(topic, content, new HashMap<>()), groupId);
    }

    @Override
    public WeEvent getEvent(@JsonRpcParam(value = "eventId") String eventId,
                            @JsonRpcParam(value = "groupId") String groupId) throws BrokerException {
        log.info("eventId:{} groupId:{}", eventId, groupId);

        return this.producer.getEvent(eventId, groupId);
    }

    @Override
    public WeEvent getEvent(@JsonRpcParam(value = "eventId") String eventId) throws BrokerException {
        log.info("eventId:{}", eventId);

        return this.producer.getEvent(eventId, "");
    }

    @Override
    public boolean open(@JsonRpcParam(value = "topic") String topic,
                        @JsonRpcParam(value = "groupId") String groupId) throws BrokerException {
        log.info("topic:{} groupId:{}", topic, groupId);

        return this.producer.open(topic, groupId);
    }

    @Override
    public boolean open(@JsonRpcParam(value = "topic") String topic) throws BrokerException {
        log.info("topic:{}", topic);

        return this.producer.open(topic, "");
    }

    @Override
    public boolean close(@JsonRpcParam(value = "topic") String topic,
                         @JsonRpcParam(value = "groupId") String groupId) throws BrokerException {
        log.info("topic:{} groupId:{}", topic, groupId);

        return this.producer.close(topic, groupId);
    }

    @Override
    public boolean close(@JsonRpcParam(value = "topic") String topic) throws BrokerException {
        log.info("topic:{}", topic);

        return this.producer.close(topic, "");
    }

    @Override
    public boolean exist(@JsonRpcParam(value = "topic") String topic,
                         @JsonRpcParam(value = "groupId") String groupId) throws BrokerException {
        log.info("topic:{} groupId:{}", topic, groupId);

        return this.producer.exist(topic, groupId);
    }

    @Override
    public boolean exist(@JsonRpcParam(value = "topic") String topic) throws BrokerException {
        log.info("topic:{} groupId", topic);

        return this.producer.exist(topic, "");
    }

    @Override
    public TopicPage list(@JsonRpcParam(value = "pageIndex") Integer pageIndex,
                          @JsonRpcParam(value = "pageSize") Integer pageSize,
                          @JsonRpcParam(value = "groupId") String groupId) throws BrokerException {
        log.info("pageIndex:{} pageSize:{} groupId:{}", pageIndex, pageSize, groupId);

        return this.producer.list(pageIndex, pageSize, groupId);
    }

    @Override
    public TopicPage list(@JsonRpcParam(value = "pageIndex") Integer pageIndex,
                          @JsonRpcParam(value = "pageSize") Integer pageSize) throws BrokerException {
        log.info("pageIndex:{} pageSize:{}", pageIndex, pageSize);

        return this.producer.list(pageIndex, pageSize, "");
    }

    @Override
    public TopicInfo state(@JsonRpcParam(value = "topic") String topic,
                           @JsonRpcParam(value = "groupId") String groupId) throws BrokerException {
        log.info("topic:{} groupId:{}", topic, groupId);

        return this.producer.state(topic, groupId);
    }

    @Override
    public TopicInfo state(@JsonRpcParam(value = "topic") String topic) throws BrokerException {
        log.info("topic:{}", topic);

        return this.producer.state(topic, "");
    }

    @Override
    public List<String> listGroup() throws BrokerException {
        return this.producer.listGroupId();
    }

}
