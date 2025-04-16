ConditionTimeout {
	var <>time, <condition;

	*new { | time = 1, test = false |
		^this.newCopyArgs(time).init(test);
	}

	init { | test = false |
		condition = Condition(test);
	}

	hang { | argTime |
		argTime !? { time = argTime };
		thisThread.clock.sched(time, { this.unhang });
		condition.hang;
	}

	unhang {
		condition.unhang
	}
}