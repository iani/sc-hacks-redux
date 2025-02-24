/* 11 Mar 2021 23:02
Execute a series of unix cmds in order.
Wait until all commands are finished before continuing.
*/
//:Code to rewrite using UnixCmdQueue:

(
fork {
	var remaining = 0;
	var cond = Condition({ remaining <= 0 });
	
	5.do { |i|
		"some command, maybe sox...".unixCmd({ |exit|
			remaining = remaining - 1;
			cond.signal;
		});
		remaining = remaining + 1;
	};
	cond.wait;
	
	//	... now continue...
};
)
//:Testing UnixCmdQueue outside of a routine
(
u = UnixCmdQueue();
u.onEnd = { "================ DONE ================".postln; };
u.add("ls audi*");
u.add("ls bti*");
u.add("ls pwu*");
)
//:testing inside a routine.
(
r = {
	var u;
	u = UnixCmdQueue();
	u.onEnd = { r.next };
	[
		"ls audi*", "ls bti*", "ls pwu*"
	] do: { | c | u.add(c) };
	
	\waitHere.yield;
	"================ ALL UNIX CMDS DONE".postln;
	"I CONTINUED NOW".postln;
}.fork;
)
//:
//:Put the actions to be done when finished inside onEnd
//No need for a routine.
(
var u;
u = UnixCmdQueue();
u.onEnd = {
	"================ ALL UNIX CMDS DONE".postln;
	"I CONTINUED NOW".postln;
};
[
	"ls audi*", "ls bti*", "ls pwu*"
] do: { | c | u.add(c) };

)