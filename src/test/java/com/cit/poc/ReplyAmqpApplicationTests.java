package com.cit.poc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cit.poc.configuration.ReplyAmqpApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ReplyAmqpApplication.class)
public class ReplyAmqpApplicationTests {

	@Test
	public void contextLoads() {
	}

}
