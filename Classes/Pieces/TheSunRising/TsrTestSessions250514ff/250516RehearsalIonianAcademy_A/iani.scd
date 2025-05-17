//Document for iani created at 250516_194900
//:[80234.099584375] iani iani 250516_194900
// "\n\n\nTHIS IS A POST FROM THE LOCAL STARTUP OF USER IANI\n\n\n".postln;
var incipits, verses, loadWav;
incipits = [
		\busy,
		\whyDost,
		\through,
		\must,
		\saucy,
		\late,
		\go,
		\call,
		\love,
		\nor,
		\thy,
		\whyShouldst,
		\i,
		\but,
		\if,
		\look,
		\whether,
		\be,
		\ask,
		\and,
		\shes,
		\nothing,
		\princes,
		\all,
		\thou,
		\in,
		\thine,
		\to,
		\shine,
		\this,
	];

verses = [
"Busy old fool, unruly Sun,",
"Why dost thou thus,",
"Through windows, and through curtains, call on us?",
"Must to thy motions lovers' seasons run?",
"Saucy pedantic wretch, go chide",
"Late school-boys and sour prentices,",
"Go tell court-huntsmen that the king will ride,",
"Call country ants to harvest offices;",
"Love, all alike, no season knows nor clime,",
"Nor hours, days, months, which are the rags of time.",
// ------------ ,
"Thy beams so reverend, and strong",
"Why shouldst thou think?",
"I could eclipse and cloud them with a wink,",
"But that I would not lose her sight so long.",
"If her eyes have not blinded thine,",
"Look, and to-morrow late tell me,",
"Whether both th' Indias of spice and mine",
"Be where thou left'st them, or lie here with me.",
"Ask for those kings whom thou saw'st yesterday,",
"And thou shalt hear, \"all here in one bed lay.\"",
// ------------
"She's all states, and all princes I;",
"Nothing else is;",
"Princes do but play us; compared to this,",
"All honour's mimic, all wealth alchemy.",
"Thou, Sun, art half as happy as we,",
"In that the world's contracted thus;",
"Thine age asks ease, and since thy duties be",
"To warm the world, that's done in warming us.",
"Shine here to us, and thou art everywhere;",
"This bed thy center is, these walls thy sphere.",
];
currentEnvironment.parent[\verses] = verses;
currentEnvironment.parent[\incipits] = incipits;
//:
loadWav = { | p, num |
	var folderNum, audioFiles, buffers;
	folderNum = p.folderName.last.asString;
	postln("Loading voice buffers from:" + p.folderName);
	buffers = Array.newClear(verses.size);
	p.fullPath.entriesMatchingWav do: { | f |
		buffers[f.fileNameWithoutExtension.split($-)[2].interpret]
		= Buffer.read(Server.default, f)
	};
	currentEnvironment.parent[("verses"++(num+1)).asSymbol] = buffers;
	incipits do: { | i, j |
		currentEnvironment.parent[(i++(num+1)).asSymbol] = buffers[j];
	};
	// ("verses"++(num+1)).asSymbol.vlayout(
	// 	ListView().items_(verses).action_({ | me |
	// 		buffers[me.value].play;
	// 	})
	// );
	buffers;
};
Server.default waitForBoot: {
	var buflist, window, listViews, selectionActions;
	buflist = PathName(thisProcess.nowExecutingPath).pathOnly.folders.detect({ | p |
		p.folderName == "audiofiles"
	}).folders collect: { | p, num |
		loadWav.(p, num);
	};
	/* // IMPORTANT: Need to do something like this here,
		// in order ro run the code locally and share it to the Usrs in OSC
		User.interpretSend("1000.rand;");
		// what code should we interpret there? We want to set a global
		variable to the list of buffers selected.
		Perhaps use a new class. For example, class TheSunRising
		For example something like:
		User.interpretSend(
		format(
		"TheSunRising.addVerses(%)",
		me.selection
		);
		One should first test this by posting the string to be interpreted
		like this:
		format(
		"TheSunRising.addVerses(%)",
		me.selection
		).postln;
		Try building this up from this simple example:
		User.interpretSend(
		format("%.size;", Array.rand(2, 10, 100))
		);
	*/
	// sounds funny for Enter, Space and backspace
	SynthDef(\pinch, { |freq=330, ffreq=110, pos=0.0, amp=1.0|
		var signal, env, conv, f1;
		f1 = LFSaw.kr(ffreq * 0.001).range(0,1);
		env = EnvGen.ar(Env.perc(0.01,0.1), doneAction: 2);
		signal = SinOsc.ar(freq+SinOsc.ar(ffreq,0,freq)).fold(f1.neg,f1) * env;
		conv = Limiter.ar(Convolution.ar(signal, Decay2.ar(Dust.ar(ffreq),0.01,f1/3)), 0.95);
		Out.ar(0, Pan2.ar(signal, pos, amp))
	}).add;

	selectionActions = (1..4) collect: { | voiceIndex |
		{ | me |
			// postln("SELECTION:" me.selection);
			postln("(Voice" + voiceIndex + ":");
			me.items[me.selection] do: { | v |
				postln("   " + v)
			};
			")".postln;
		}
	};
	if (User.localId === \iani2) {
		window = 'The Sun Rising'.hlayout(
			*(listViews = buflist.collect({ | b, voiceIndex |
				ListView().items_(verses)
				.selectionMode_(\multi)
				.enterKeyAction_({ | me |
					me.selection.postln;
				});
				// .selectionAction_(nil);
			}))
		);
		window.bounds = Rect(0, 0, 1225, 475);
		{
			listViews do: { | me | me.selection = [] };
			listViews do: { | v, i | // activate action after clearing
				v.selectionAction = selectionActions[i];
			}
		} defer: 0.1;
	};
};

//:[80235.854468833] iani iani 250516_194902
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[80246.481560083] iani iani 250516_194912
"Egine to reboot"
//:[80258.89456175] iani iani 250516_194925
"lets test the clicks, giorgo"
//:[80293.749374792] iani iani 250516_195000
"OCHI"
//:[80301.165882125] iani iani 250516_195007
"den akouo klick"
//:[80315.298736333] iani iani 250516_195021
"OK"
//:[80408.377694042] iani iani 250516_195154
"AKOUO"
//:[80424.814288208] iani iani 250516_195211
"THelo flute + granulation"
//:[80450.380053417] iani iani 250516_195236
"NAI!!!!!!!"
//:[80451.079264292] iani iani 250516_195237
"NAI!!!!!!!"
//:[80465.465246375] iani iani 250516_195251
"PERFECT. Clicks + Flute"
//:[80480.802475167] iani iani 250516_195307
"All we are mising, are the granulation from Vasili - if it works"
//:[80517.56058025] iani iani 250516_195344
"Archizoume opos archisame tora"
//:[80528.865965917] iani iani 250516_195355
"Penny???? Tha paixeis?"
//:[80570.913373583] iani iani 250516_195437
"THa paixei?"
//:[80586.334458833] iani iani 250516_195452
"OK> CHairomai"
//:[80596.683888083] iani iani 250516_195503
"Pame elevthera ;ops archisame"
//:[80613.096475417] iani iani 250516_195519
"Tha sas grapso edo gia tin enarxi - se ligo"
//:[80662.810718125] iani iani 250516_195609
"Tha prologiso to kommati stous ligous synadelfous poy echoyn erthei"
//:[80711.66821075] iani iani 250516_195658
"Naipeimenete"
//:[80739.011747] iani iani 250516_195725
"Tha sas grapso edo tin enarxji seakomi  ligo - 4- 5 lepta"
//:[80752.7414125] iani iani 250516_195739
"apo mena den lamvaneis penny?"
//:[80772.561585625] iani iani 250516_195759
"AN thelete kanete test"
//:[80781.559338917] iani iani 250516_195808
"ecxoume icho"
//:[80789.619436083] iani iani 250516_195816
"echoume icho"
//:[80822.2861305] iani iani 250516_195848
"OK se 2 lepta tora"
//:[81325.221876583] iani iani 250516_200711
"PAME"
//:[81424.042275042] iani iani 250516_200850
~i1
//:[81435.709579667] iani iani 250516_200902
~i1.play;
//:[81448.313257667] iani iani 250516_200914
~ir.play;
//:[81453.753754917] iani iani 250516_200920
~i4.play;
//:[81480.466166917] iani iani 250516_200946
~must3.play;
//:[81485.919264542] iani iani 250516_200952
~must2.play;
//:[81490.684898583] iani iani 250516_200957
~must1.play;
//:[81515.908572] iani iani 250516_201022
~saucy4.play;
//:[81527.044659625] iani iani 250516_201033
~saucy3.play;
//:[81536.966386875] iani iani 250516_201043
~saucy2.play;
//:[81624.356899542] iani iani 250516_201210
~i3.play;
//:[81672.167773167] iani iani 250516_201258
~call1.play;
//:[81683.191951208] iani iani 250516_201309
~call2.play;
//:[81688.640119333] iani iani 250516_201315
~call3.play;
//:[81694.074147917] iani iani 250516_201320
~call4.play;
//:[81967.766873667] iani iani 250516_201754
"pame pros telos"
//:[82171.072554042] iani iani 250516_202117
~she1
//:[82175.367277792] iani iani 250516_202121
~she1.play
//:[82180.956167083] iani iani 250516_202127
~shes1.play
//:[82199.082695917] iani iani 250516_202145
~call4.play;
//:[82234.597879] iani iani 250516_202221
~this1
//:[82238.110759292] iani iani 250516_202224
~this1.play;
//:[82272.126476792] iani iani 250516_202258
~this4.play;
