package org.iot.trucker.rules;

import org.iot.trucker.entity.Reading;
import org.iot.trucker.entity.Vehicle;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Class to fire all the rules
 * @author Harish Krishnamurthi
 *
 */
@Component
@Scope("prototype")
public class CheckRule {
	
	@Autowired
	private Rules rules;
	@Autowired
	private RulesEngine rulesEngine;
	@Autowired
	@Qualifier("rpmRule")
	private TruckerRule rpmRule;
	@Autowired
	@Qualifier("fuelRule")
	private TruckerRule fuelRule;
	@Autowired
	@Qualifier("indicatorRule")
	private TruckerRule indicatorRule;
	@Autowired
	@Qualifier("tirePressureRule")
	private TruckerRule tirePressureRule;
	
	/**
	 * fire all the rules
	 * @param reading
	 * @param vehicle
	 */
	
	public void checkComponents(Reading reading,Vehicle vehicle){
		Facts facts = new Facts();
		facts.put("reading", reading);
		facts.put("vehicle", vehicle);
		rules.register(rpmRule);
		rules.register(fuelRule);
		rules.register(tirePressureRule);
		rules.register(indicatorRule);
		rulesEngine.fire(rules, facts);
	}
}
