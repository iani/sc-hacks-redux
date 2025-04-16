/* 火 15  4 2025 19:59

Get the OSC data recordings from this repository:

https://github.com/iani/osc_data_240510ff.git

Create a Rokoko instance using this date as timestamp in the name.
Start the instance listening to incoming '/rokoko/' OSC messages.
Create an OscData gui with data obtained from a folder chosen by the user,
via FileDialog.
	============== IMPORTANT: Rokoko data are found in this repository:
	https://github.com/iani/osc_data_240510ff.git
	=============== !!!!!!!!!!!!!!!!! ================
Note: Once the folder is chosen, it is saved in userAppSupportDir,
	under the name of the Rokoko instance.
	Rokoko instance will read the data from the saved path if available.
*/
//========== creating Rokoko instances ==========
//: ========== create an instance with a custom name  ==========
Rokoko.named(\x); // create Rokoko with custom name \x
//: ========== create an instance with today's date stamp YYMMDDrokoko as name  ==========
Rokoko.atDate;
//: Custom postfix to date:
// ========== create an instance with today's date stamp YYMMDDtest as name  ==========
Rokoko.atDate(\test);
//:------------------------------------------------------------
//: Playing rokoko with recorded data
//: enabling osc reception on an instance of Rokoko:
Rokoko.atDate.enable;
Rokoko.atDate.name;
Rokoko.atDate(\iani).name;
Rokoko.named(\x).name;
Rokoko.named(\x).oscDataGui;
Rokoko.named(\z).oscDataGui;
Rokoko.named(\z).oscDataGui;
//:
Rokoko.atDate;
Rokoko.atDate.name;
Rokoko.atDate.name.push;
Rokoko.atDate.envir.keys;
Rokoko.atDate.envir['Baubo'];
Rokoko.atDate.envir['Baubo'].envir;
Rokoko.atDate.envir['Baubo'].envir.push;

Rokoko.atDate.envir['Baubo'].envir.keys.asArray;

~rightUpLegz
~leftFoot;
~outbus;
~inbus;
currentEnvironment.keys.asArray;
currentEnvironment.keys.asArray;
Rokoko.atDate.
//:
//: opening an osc data gui for playback
Rokoko.atDate.oscDataGui;
//: enabling and opening gui in one statement
Rokoko.atDate.enable.oscDataGui;
// Once the OscData gui is open, click the play Checkbox to send some
// data to the Rokoko instance.
// Then: Inspect the environment of the Rokoko, containing its actors:
Rokoko.atDate.envir;
// Obtain the array of created actors:
Rokoko.atDate.actors;
// Inspect the first actor:
Rokoko.atDate.actors.first.inspect;
// Obtain the array of joints in the first actor:
Rokoko.atDate.actors.first.joints;
//:========== Creating Rokoko instances with different names:
Rokoko.atDate; // create name from today's timestamp
Rokoko.atDate.name; // test the above
Rokoko.named(\x); // create Rokoko with custom name
Rokoko.named(\x).name; // test the above
//:============================================================
// Actor tests
a = Actor();
a.envir;
a.at(\actor); // same as a[\actor];
a[\actor];
a.at(\head); // access a joint
a.at(\headx); // access the bus of a joint variable
a[\headz]; // same as a.at(\headz)
a.at(\heady);
a.at(\hipx);
a[\hipy];    // input bus for hip y joint
a[\hipyout]; // output bus for hip y joint
a[\hip];
//:============================================================
//:Getting the OscData instance created by Rokoko, to process the data in sclang
//:1. using Rokoko directly, with a ref.
d = Ref(); // Ref is needed because oscDataGui is asynchronous.
Rokoko.atDate.oscDataGui(d);
//: Ref is needed because oscDataGui is asynchronous.
d.value;
//:2. Using RokokoData
d = RokokoData.atDate;
// Avoid chaining oscdata to atDate or named, because instance creation is asynchronous
o = d.oscdata;
d.gui;
//: Exploring oscdata from Rokoko offline
~rd = RokokoData.atDate;
~rd.name;
~od = RokokoData.atDate.oscdata;
~od.gui;
~od.playSegment; // play back entire data
~od.playSegment(0, 10, 5);
~od.playSegment(30, 0, 5);
// Presupposes having read the data previously with RokokoData.atDate or RokokoData.named(...)
~data = RokokoData.atDate.oscdata.parsedEntries;
~times = ~data.flop.first;
~entries = ~data.flop[1];
~entries.first.class;
~entries.first.interpret;
//:
~rdata = RokokoData.atDate;
~rdata.times;
~rdata.entries;
~rdata.controlValues;
~rdata.controlValues.first;
~rdata.controlValues[1];
~rdata.controlValues.first - ~rdata.controlValues[1];
(~rdata.controlValues.first - ~rdata.controlValues[1]).abs.maxItem;
(~rdata.controlValues.first - ~rdata.controlValues[1]).abs.minItem;
~rdata.controlValues.first.plot;
~rdata.controlValues[0].plot;
~rdata.controlValues[1].plot;

~rdata.controlValues[..10].plot;
(0..9).collect({ Array.rand(100, -1.0, 1.0) }).plot;
~rdata.play; // play back.
~rdata.splay; // play back and open control scope to watch.
//:
Actor.numControls;
//: Debugging
b = Bus(\control, 0, Server.default, Actor.numControls);
b.set(1);
c = Bus.control;
c.set(1);
d = Bus(\control, Server.default, 0, 1);