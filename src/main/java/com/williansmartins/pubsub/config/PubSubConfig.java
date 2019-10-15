package com.williansmartins.pubsub.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.AckMode;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.cloud.gcp.pubsub.support.converter.JacksonPubSubMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.williansmartins.pubsub.domain.User;

@Configuration
public class PubSubConfig {

    private static final Log LOGGER = LogFactory.getLog(PubSubConfig.class);

    //PUBLICADOR
    @Bean
    @ServiceActivator(inputChannel = "pubSubOutputChannel")
    public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
        PubSubMessageHandler adapter = new PubSubMessageHandler(pubsubTemplate, "NOME_QUALQUER");
        adapter.setPublishCallback(new ListenableFutureCallback<String>() {
            @Override
            public void onFailure(Throwable ex) {
                LOGGER.error("There was an error sending the message.");
            }

            @Override
            public void onSuccess(String result) {
                LOGGER.info("Message was sent successfully.");
            }
        });

        return adapter;
    }

    @MessagingGateway(defaultRequestChannel = "pubSubOutputChannel")
    public interface PubSubOutboundGateway {
        void sendToPubSub(@Header(GcpPubSubHeaders.TOPIC) String topic, User message);
    }

    @Bean
    public JacksonPubSubMessageConverter jacksonPubSubMessageConverter(ObjectMapper objectMapper) {
        return new JacksonPubSubMessageConverter(objectMapper);
    }
    
    
    // CONSUMIDOR
    
    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(@Qualifier("channelConsumer") MessageChannel inputChannel, PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, "com.williansmartins.assinatura1");
        adapter.setOutputChannel(inputChannel);
        adapter.setAckMode(AckMode.MANUAL);
        adapter.setPayloadType(User.class);
        return adapter;
    }

    @ServiceActivator(inputChannel = "channelConsumer")
    public void messageReceiver(final String payload, @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
    	System.out.println(payload);
    	System.out.println(message);
        message.ack();
    }

}
