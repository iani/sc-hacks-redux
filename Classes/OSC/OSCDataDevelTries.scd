/* 27 Mar 2022 15:03
Code trials for refactoring OSCData.

*/
//: vpn debug 28 Mar 2022 16:52
// IANI WITH VPN TETHERING FROM IPHONE
// user info received for 'vas', private: 192.168.2.3:22246 public: 5.54.207.85:22246
// user info received for 'iani', private: 172.20.10.6:22246 public: 62.74.17.136:1545

//: IANI WITHOUT VPN DIRECTLY FROM MATIZ WIFI
// user info received for 'iani', private: 192.168.1.69:22246 public: 78.87.215.17:22246
// ================================================================
// iani + vas without vpn, directly from wifi
// user info received for 'vas', private: 192.168.2.3:22246 public: 5.54.207.85:22246
// user info received for 'iani', private: 192.168.1.69:22246 public: 78.87.215.17:22246
//:

Project.gui;

"Hello";

10000.rand;

OSCData.selectSession;
OSCData.loadedSession;
OSCData.loadedSession.name;
d = OSCData.loadedSession.data;

d.size;
OSCData.loadedSession.duration;

d[0][0];
d[0][1];
d.flop[1];
d.flop[1].differentiate;
//:
{
	d.flop[1].differentiate.put(0, 0) do: { | dt, count |
		dt.wait;
		postln("playing element at index:" + count);
	};
}.fork;
//:
var messages;
messages = d.flop[0];
{
	d.flop[1].differentiate.put(0, 0) do: { | dt, count |
		dt.wait;
		postln("playing element at index:" + count);
		postln("the message is:");
		messages[count].postln;
	};
}.fork;
//:
{
	var messages, dt;
	messages = d.flop[0];
	dt = d.flop[1].differentiate; // .put(0, 0);
	(5..12) do: { | index, count |
		if (count > 0) { dt[index].wait; };
		postln("playing element at index:" + index + "COUNT IS" + count);
		postln("the message is:");
		messages[index].postln;
	};
}.fork;
//:
{
	var messages, dt;
	messages = d.flop[0];
	dt = d.flop[1].differentiate; // .put(0, 0);
	(5..12) do: { | index, count |
		if (count > 0) { (dt[index] * 0.1).wait; };
		postln("playing element at index:" + index + "COUNT IS" + count);
		postln("the message is:");
		messages[index].postln;
	};
}.fork;
//:
{
	var messages, dt;
	messages = d.flop[0];
	dt = d.flop[1].differentiate; // .put(0, 0);
	(5..12) do: { | index, count |
		if (count > 0) { (dt[index] * 2).wait; };
		postln("playing element at index:" + index + "COUNT IS" + count);
		postln("the message is:");
		messages[index].postln;
	};
}.fork;
//:
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