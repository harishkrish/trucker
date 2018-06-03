package org.iot.trucker;

import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Harish Krishamurthi
 *
 */
@Configuration
public class AppConfig {
	
	@Bean
	public Rules getRules(){
		return new Rules();
	}
	@Bean
	public RulesEngine getRulesEngine(){
		return new DefaultRulesEngine();
	}
}
