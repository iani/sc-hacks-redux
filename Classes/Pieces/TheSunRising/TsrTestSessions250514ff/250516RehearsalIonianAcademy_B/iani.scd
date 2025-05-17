//Document for iani created at 250516_202332
//:[82305.588910042] iani iani 250516_202332
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

//:[82307.331800583] iani iani 250516_202333
(1..17) collect: { | i | ("mbus"++i).asSymbol };
