//Fri 12 Jan 2024 15:40
//make Server:waitForBoot use Mediator's default environment
//even when called from startup file.

+ Server {
	waitForBootEnvir { |onComplete, limit = 100, onFailure, dt = 0.1 |
		// onFailure.true: why is this necessary?
		// this.boot also calls doWhenBooted.
		// doWhenBooted prints the normal boot failure message.
		// if the server fails to boot, the failure error gets posted TWICE.
		// So, we suppress one of them.
		{
			this.waitForBoot(onComplete, limit, onFailure);
		}.defer(dt)
	}
}