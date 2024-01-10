/* 27 Feb 2022 16:13

*/

{ Impulse.kr(1) }.makeTrig.play;
{ Impulse.kr(1) }.makeReply.play;
{ Impulse.kr(5) }.makeTrig({SinOsc.ar(0.1)}).play;
//:
OSC.verbose;
OSC.silent;
//:
\trigger.trace;
\trigger.untrace;
//:
\trigger.makeTrig({ Impulse.kr(5) });
//:
EventStream((degree: (-10..10).pseq, dur: 0.1)).addTr(\trigger);
//:
EventStream((degree: (-10..10).pseq), dur: 0.1)
//:
\trigger.makeTr({ Impulse.kr(5) }) +> \mytrigger;
\trigger.makeTr({ Impulse.kr(15) }) +> \mytrigger;

\trigger.makeTr({ Impulse.kr(2.5) }) +> \mytrigger;
//:
(degree: (-10..10).pseq, dur: 0.1) +>! \test;
~test.addTr(\mytrigger);

(degree: (-10..10).pseq, dur: 1) ++> \test;

~test.event.put(\degree, 1);


//: debug getNextEvent when modifying the stream with ++>
(degree: (-10..10).pseq, dur: 0.1) +>! \test;
~test;
~test.getNextEvent;
(degree: 0) ++> \test;
//:



\test.start;
(degree: (10..-10).pseq, dur: 0.1) +>! \test;
//:
(degree: (10..-10).pseq, dur: 0.5) +> \test2;
(degree: (-10..10).pseq, dur: 0.3) ++> \test2;
(degree: ((0..5)*2).pseq, dur: 0.2) ++> \test2;


// ======== OLDER STUFF =========
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