package com.puercha.algo.challenge.service;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.puercha.algo.challenge.service.codelauncher.MemoryMonitor;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class MemoryMonitorTest {
	private final static Logger logger = LoggerFactory.getLogger(MemoryMonitor.class);
	@Inject
	MemoryMonitor memoryMontitor;
	
	
	@Test
	public void testMemory() {
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info(""+memoryMontitor.getKeySet());
		
	}
	
}
