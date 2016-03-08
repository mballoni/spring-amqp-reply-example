package com.cit.poc;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/do")
public class RestService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Get method for convenience
     */
    @GET
    @Path("/{arg}")
    public String service(@PathParam("arg") String arg) {
        return (String) rabbitTemplate.convertSendAndReceive(arg);
    }
}
