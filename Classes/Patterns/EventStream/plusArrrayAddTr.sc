/* 28 Feb 2022 09:55

*/

/*
+>> { | player, key | this.addTr(player, key) }

	addTr { | player, key |
		key ?? { key = this };
		this.addAction({
			currentEnvironment[player].getNextEvent.play;
		}, key);
	}
	Symbol : makeTr :
	makeTr { | trigger, values |
		var message;
		message = this.asOscMessage;
		trigger ?? { trigger = { Impulse.kr(2) } };
		values ?? { values = 0 };
		^{
			//			Env.adsr.kr(gate: \gate.kr(1), doneAction: 2);
			SendReply.kr(trigger.value, message, values.value);
			Env.adsr.kr(gate: \gate.kr(1), doneAction: 2);
		};
	}
*/

+ Array {
	+>> { | message, player |
		/*
			message: the osc message with which the trigger data are sent
			player: the player where the function will play (defaults to message)
			this[0] : trigger: The function generating the triggering UGen
			this[1] : values: The function generating the values to be sent
		*/
		var trigger, values;
		player ?? {  player = message };
		message = message.asOscMessage;
		trigger = this[0] ?? { { Impulse.kr(2) } };
		values = this[1] ?? { 0 };
		postln("message:" + message + "player" + player +
			"trigger" + trigger + "values" + values);
		message.makeTr(trigger, values) +> player;
	}

}