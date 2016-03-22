package com.cit.poc;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/transaction")
public class SkuTransactionRestService {

    Logger LOGGER = LoggerFactory.getLogger(SkuTransactionRestService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Get method for convenience
     */
    @GET
    @Path("/async/{sku}/{qtdy}")
    public void serviceAsync(@Suspended AsyncResponse asyncResponse, @PathParam("sku") String sku,
            @PathParam("qtdy") Integer qtdy) {
        LOGGER.info("Receiving sku {} for stock operation {}", sku, qtdy);
        Object info = String.format("{\"sku\": %s, \"qtdy\": %d}", sku, qtdy);
        String result = (String) rabbitTemplate.convertSendAndReceive("test", info);

        asyncResponse.resume(result);
    }

    @GET
    @Path("/sync/{sku}/{qtdy}")
    public String serviceSync(@PathParam("sku") String sku, @PathParam("qtdy") Integer qtdy) {
        LOGGER.info("Receiving sku {} for stock operation {}", sku, qtdy);
        Object info = String.format("{\"sku\": %s, \"qtdy\": %d}", sku, qtdy);
        String result = (String) rabbitTemplate.convertSendAndReceive("test", info, new CorrelationData(UUID
                .randomUUID().toString()));

        return result;
    }
}
