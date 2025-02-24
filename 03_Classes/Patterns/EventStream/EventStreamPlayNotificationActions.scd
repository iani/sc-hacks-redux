/* 27 Mar 2022 00:28
Add notification actions to each EventStream play event.
*/

(dur: Prand([1, 2], inf)) ++> \test;
//:
currentEnvironment[\test].trace;
currentEnvironment[\test].untrace;
//:
\x.addNotifier(currentEnvironment[\test], \played, { | n, event, stream |
	postln("Event" + event);
	postln("Stream" + stream.stream);
});
//: Syntax drafts for operator shortcut
\player <!.envir { action }; /* add action to notifier \player for message \played
and listener \player */
\player >!.envir NetAddress /* Send \player's played event keys values to NetAddress.
	Only send keys contained in \player's stream. Default key: \dur */
//: ================================================================
// tests with new methods
(dur: Prand([1, 1, 2] / 4, inf), degree: Prand([0, 2], inf)) ++> \test.sendEvents;
// \test.sendEvents;
OSC.trace1(\eventPlayed);
OSC.untrace1(\eventPlayed);