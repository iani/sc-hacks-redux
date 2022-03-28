/* 27 Mar 2022 15:03
Code trials for refactoring OSCData.

*/
//:

OSCData.selectSession;
OSCData.loadedSession;
OSCData.sessionPaths.first.folderName;
OSCData.sessionNames;
OSCData.window;

t = OSCData.loadedSession.first.data.flop[1];
u = t.differentiate;
u[0] = 0;

u.size;
d = OSCData.loadedSession.first.data;
d.size;
d = d ++ d;
//:
{
	u do: { | t, i|
		t.wait;
		d[i].postln;
	};
	"Finished data playback".postln;
}.fork;
//:

o = OSCData.record;
o.dataFilePath;
o.save;
o.disable;

//:
OSC.untrace;
//
{
	10 do: { | i |
		m = Set.newFrom(['/test', '/status', '/n_go', '/sense/data', i.asSymbol]);
		d = { [['/status', '/sense/data'].choose] ++ Array.rand(10, 0.0, 1.0) } ! 10000;
		(
			listening2: m,
			data: d,
			time: Date.localtime
		).writeArchive("~/sc-projects/oscdata/test.scd".absPath);
		0.01.wait;
		i.postln;
	};
	"DONE".postln;
}.fork;
//:
r = Object.readArchive("~/sc-projects/oscdata/test.scd".absPath);
//:
r[\listening2];
//:
r[\data].first;
//:
r[\data].flop[1];
//:
//:
//:
a = Array(14);
{ | i |  a add: i } ! 10;
//:
"OSCData" ++ Date.localtime.stamp ++ "_" ++ (UniqueID.next % 1000);
//:
Date