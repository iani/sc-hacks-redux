/* 27 Feb 2022 16:13

*/

{ Impulse.kr(1) }.makeTrig.play;
{ Impulse.kr(1) }.makeReply.play;
{ Impulse.kr(5) }.makeTrig({SinOsc.ar(0.1)}).play;
//:
OSC.verbose;
OSC.silent;
//:
\tr.trace;
\tr.untrace;
//:
\tr.makeTrig({ Impulse.kr(5) });
//:
{
	//			Env.adsr.kr(gate: \gate.kr(1), doneAction: 2);
	SendReply.kr(Impulse.kr(1), \test, 0);
i	Env.adsr.kr(gate: \gate.kr(1), doneAction: 2);
}.play;
//:
{ | message = \trigger, trigger |
	message = message.asOscMessage;
	trigger ?? { trigger = Impulse.kr(1) };
	{
		//			Env.adsr.kr(gate: \gate.kr(1), doneAction: 2);
		SendReply.kr(Impulse.kr(1), message, 10);
		Env.adsr.kr(gate: \gate.kr(1), doneAction: 2);
	}.play;
}.value
//:
{ | message = \trigger, trigger |
	message = message.asOscMessage;
	trigger ?? { trigger = { Impulse.kr(2) } };
	{
		//			Env.adsr.kr(gate: \gate.kr(1), doneAction: 2);
		SendReply.kr(trigger.value, message, 10);
		Env.adsr.kr(gate: \gate.kr(1), doneAction: 2);
	}.play;
}.value
//:
{ | message = \trigger, trigger, value |
	message = message.asOscMessage;
	trigger ?? { trigger = { Impulse.kr(2) } };
	value ?? { value = 0 };
	{
		//			Env.adsr.kr(gate: \gate.kr(1), doneAction: 2);
		SendReply.kr(trigger.value, message, value.value);
		Env.adsr.kr(gate: \gate.kr(1), doneAction: 2);
	}.play;
}.value(\trigger, { Dust.kr(5) }, { SinOsc.kr(0.5) * 10  })
//:
\trigger.trace;
\trigger.untrace;