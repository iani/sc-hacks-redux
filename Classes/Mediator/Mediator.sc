/* 25 Jan 2021 20:30
EnvironmentRedirect that handles state of objects before replacing them
in its keys.

See README.
*/

Mediator : EnvironmentRedirect {

	*new { | envir |
		^super.new(envir).dispatch = MediatorHandler();
	}
	
	put { | key, obj | dispatch.value(key, obj); }

	prPut { | key, obj | envir.put (key, obj) }
}