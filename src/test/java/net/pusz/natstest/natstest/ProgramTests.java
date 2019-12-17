package net.pusz.natstest.natstest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import net.pusz.natstest.natstest.nats.TestNatsConfig;

@SpringBootTest(classes = { TestNatsConfig.class })
class ProgramTests {

	@Test
	void contextLoads() {
	}

}
