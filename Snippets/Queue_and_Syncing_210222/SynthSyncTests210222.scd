/* 22 Feb 2021 14:02
	Testing sync requirements of Synth 
*/
//:Must wait for SynthDef to load in server before starting synth with it.
SynthDef("synctest", { | freq = 440 |
	var src;
	src = SinOsc.ar(freq, 0, 0.1).dup;
	Out.ar(0, src * Env.adsr.kr(2, \gate.kr(1)));
}).add;
a = Synth("synctest"); /* getting error:
	*** ERROR: SynthDef synctest not found
	FAILURE IN SERVER /s_new SynthDef not found
*/
//:
a.release;
//:Using sync
{
	SynthDef("synctest", { | freq = 440 |
		var src;
		src = SinOsc.ar(freq, 0, 0.1).dup;
		Out.ar(0, src * Env.adsr.kr(2, \gate.kr(1)));
	}).add;
	Server.default.sync;
	a = Synth("synctest")
}.fork;
//:
a.release;
//:================================================================
//:set after starting a synth - no sync needed after Synth.new:
a = Synth("synctest");
a.set(\freq, 1200); // no sync needed
//:mapping. Testing Bus sync + map sync.
b = Bus.control;
b.set(60.rrand(70).midicps);
a = Synth("synctest");
a.map(\freq, b.index);
//: ================================================================
//:Sync needed if creating the synth with Function:play
//: sync needed for set:
a = { | freq = 440 |
	SinOsc.ar(freq, 0, 0.1).dup * Env.adsr.kr(2, \gate.kr(1));
}.play;
a.set(\freq, 1200); // sync needed
//:Above example, synced - glitch is audible because of delay in receiving sync
{
	a = { | freq = 440 |
		SinOsc.ar(freq, 0, 0.1).dup * Env.adsr.kr(2, \gate.kr(1));
	}.play;
	Server.default.sync;
	a.set(\freq, 1200); // sync needed	
	
}.fork;
//: ================ sync also needed for map
b = Bus.control;
b.set(60.rrand(70).midicps);
a = { | freq = 440 |
	SinOsc.ar(freq, 0, 0.1).dup * Env.adsr.kr(2, \gate.kr(1));
}.play;
a.map(\freq, b.index); // sync needed
//:above mapping example, synced
{
	var b;
	b = Bus.control;
	b.set(80.midicps);
	a = { | freq = 440 |
		SinOsc.ar(freq, 0, 0.1).dup * Env.adsr.kr(2, \gate.kr(1));
	}.play;
	Server.default.sync;
	a.map(\freq, b.index); // glitch is audible because of delay in receiving sync
	
}.fork;