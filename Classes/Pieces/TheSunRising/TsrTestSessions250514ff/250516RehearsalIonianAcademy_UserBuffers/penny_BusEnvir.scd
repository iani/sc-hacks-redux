//:[10211.732622292] penny penny 250516_002158
SynthDef(\p, {
	arg buf=0;
	var sig, env;
	env = Env.asr(\atk.kr(0.15), \sus.kr(4), 0).kr(2, \gate.kr(1));
	sig = PlayBuf.ar(1, buf, BufRateScale.kr(buf) *\rate.kr(1), startPos:\spos.kr(1), loop: \l.kr(0));
	sig = Pan2.ar(sig, \pan.kr(0), \amp.ar(0.5));
	Out.ar(\out.ar(~actual0), sig *env);
}).add;
//:
~p = {
	arg buf=0;
	var sig, env;
	env = Env.asr(\atk.kr(0.15), \sus.kr(4), 0).kr(2, \gate.kr(1));
	sig = PlayBuf.ar(1, buf, BufRateScale.kr(buf) *\rate.kr(1), startPos:\spos.kr(1), loop: \l.kr(0));
	sig = Pan2.ar(sig, \pan.kr(0), \amp.ar(0.5));
	Out.ar(\out.ar(~actual0), sig *env);
};

//:
~vrs = BusEnvir(~p, args: [\buf, ~i1.bufnum, \l, 1, \amp, 0.1]);
~vrs.addctl(\pan, { SinOsc.kr(0.2) });
//:

//:

~vrs = Synth(\p, [\buf, ~i1, \l, 1, \amp, 0.1]);

~i1

~verses1

//:

BusEnvir({
	arg buf=0;
	var sig, env;
	env = Env.asr(\atk.kr(0.15), \sus.kr(4), 0).kr(2, \gate.kr(1));
	sig = PlayBuf.ar(1, buf, BufRateScale.kr(buf) *\rate.kr(1), startPos:\spos.kr(1), loop: \l.kr(0));
	sig = Pan2.ar(sig, \pan.kr(0), \amp.ar(0.5));
	Out.ar(\out.ar(~actual0), sig *env);
}, args: [\buf, ~i1.bufnum, \l, 1, \amp, 0.1]);
//:
//
~vrs.defName;

SynthDescLib.at(~vrs.defName);

SynthDescLib.global.inspect;
