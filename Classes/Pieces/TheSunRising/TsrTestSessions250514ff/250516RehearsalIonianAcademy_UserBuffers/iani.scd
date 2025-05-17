//Document for iani created at 250515_102734
//:[7.5755665] iani iani 250515_102735
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[1897.497528167] iani iani 250515_105905
//: 木 15  5 2025 10:57
var old, new;
old = ~verses[0];
new = ~verses[0];
old ediDistance: new;

//:[1909.9794815] iani iani 250515_105918
//: 木 15  5 2025 10:57
var old, new;
old = ~verses[0];
new = ~verses[0];
old editDistance: new;

//:[2049.963000792] iani iani 250515_110138
//:
var old, new;
old = ~verses[0];
fork {
	old do: { | c, i |
		new = old[..i];
		(new.size > 0).if {
			old.editDistance(new).postln;
		};
	}
	0.1.wait;
};

//:[2076.570487167] iani iani 250515_110204
//:
var old, new;
old = ~verses[0];
fork {
	old do: { | c, i |
		new = old[..i];
		(new.size > 0).if {
			old.editDistance(new).postln;
		};
		0.1.wait;
	}
};

//:[2100.895856542] iani iani 250515_110229
//:
var old, new;
old = ~verses[0];
fork {
	old do: { | c, i |
		new = old[..i];
		(new.size > 0).if {
			old.editDistance(new).postln;
		};
		0.1.wait;
	}
};

//:[3479.508762333] iani iani 250515_112527
b = TsrVerse(\iani).actions;
//:[3530.716885917] iani iani 250515_112618
b = TsrType(\iani).actions;
//:[3533.285640708] iani iani 250515_112621
b.typeVerse;
//:[3543.144125833] iani iani 250515_112631
//:
Tsr.doOnType({ | char |
	postln("Tsr received char" + char);

}, \iani1);

//:[3560.393190625] iani iani 250515_112648
a = TsrType(\geodia).actions;
//:[3563.965478708] iani iani 250515_112652
a.typeVerse;
//:[3567.098509958] iani iani 250515_112655
b = TsrType(\iani).actions;
//:[3568.664164958] iani iani 250515_112656
b.typeVerse;
//:[3676.549997875] iani iani 250515_112844
//:
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	postln("Tsr received char" + char);

}, \iani1);

//:[3769.646313917] iani iani 250515_113017
//:
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions(); // test gd verse matching

//:[3831.660997375] iani iani 250515_113119
//:
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions(); // test gd verse matching

//:[3919.833055042] iani iani 250515_113248
//:
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions2(); // test gd verse matching

//:[3969.238435292] iani iani 250515_113337
TypingActions2()
//:[4032.003249667] iani iani 250515_113440
TypingActions2()
//:[4134.119877417] iani iani 250515_113622
//:
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions2(); // test gd verse matching

//:[4151.544972417] iani iani 250515_113639
//:
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions2(); // test gd verse matching

//:[4413.465797125] iani iani 250515_114101
//:
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions2(); // test gd verse matching

//:[4515.999247] iani iani 250515_114244
//:
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions2(); // test gd verse matching

//:[4573.344274458] iani iani 250515_114341
//:
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	postln("Tsr received char - for verse matching" + char);
}, \iani1);

//:[4592.967967958] iani iani 250515_114401
//:
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	postln("Tsr received char - for verse matching" + char);
}, \iani1);

//:[4616.283612083] iani iani 250515_114424
//:
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions2(); // test gd verse matching

//:[4665.244343625] iani iani 250515_114513
//:
Tsr.undoOnType(\gdmusic);
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	postln("Tsr received char - for verse matching" + char);
}, \iani1);

//:[4670.574661958] iani iani 250515_114518
//:
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions2(); // test gd verse matching

//:[5059.864534792] iani iani 250515_115148
//:
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions2(); // test gd verse matching

//:[5280.243327958] iani iani 250515_115528
//:
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions2(); // test gd verse matching

//:[5309.267111292] iani iani 250515_115557
//:
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions2(); // test gd verse matching

//:[5561.756777625] iani iani 250515_120009
//: ------- testing GD typeVerse
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions2(); // test gd verse matching

//:[5595.177968542] iani iani 250515_120043
//: ------- testing GD typeVerse
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions2(); // test gd verse matching

//:[5829.044263417] iani iani 250515_120437
//: ------- testing GD typeVerse
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions2(); // test gd verse matching

//:[5843.266254208] iani iani 250515_120451
//: ------- testing GD typeVerse
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions2(); // test gd verse matching

//:[5912.19035675] iani iani 250515_120600
//: ------- testing GD typeVerse
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions2(); // test gd verse matching

//:[5958.140522375] iani iani 250515_120646
//: ------- testing GD typeVerse
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions2(); // test gd verse matching

//:[6047.923539583] iani iani 250515_120816
13.asAscii;
//:[6067.726475375] iani iani 250515_120835
13.asAscii.ascii;
//:[6078.591329083] iani iani 250515_120846
\nl.asAscii;
//:[6085.085363208] iani iani 250515_120853
\nl.ascii;
//:[6097.151455667] iani iani 250515_120905
Char.nl.ascii;
//:[6102.813590375] iani iani 250515_120910
Char.ret.ascii;
//:[6110.93794025] iani iani 250515_120919
Char.ret;
//:[6133.079332542] iani iani 250515_120941
Char.ret.ascii;
//:[6387.326316917] iani iani 250515_121355
//: ------- testing GD typeVerse with all verses!
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerses;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions2(); // test gd verse matching

//:[6530.517384542] iani iani 250515_121618
//: ------- testing GD typeVerse with all verses!
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerses;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions2(); // test gd verse matching

//:[6911.736691333] iani iani 250515_122239
//: ------- testing GD typeVerse with all verses!
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerses;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions2(); // test gd verse matching

//:[7177.979729083] iani iani 250515_122706
//: ------- testing GD typeVerse
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions2(); // test gd verse matching

//:[7204.748143583] iani iani 250515_122732
//: ------- testing GD typeVerse with all verses!
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerses;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions2(); // test gd verse matching

//:[17659.434170292] iani iani 250515_152147
//: ------- testing GD typeVerse with all verses!
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerses;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingActions2(); // test gd verse matching

//:[17814.95204225] iani iani 250515_152423
//: ------- testing GD typeVerse with all verses!
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerses;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
Tsr.doOnVerse({ | index, verse, incipit |
	"New verse received".postln;
	postln("index" + index);
	postln("verse" + verse);
	postln("incipit" + incipit);
}, \iani1);
TypingActions2(); // test gd verse matching

//:[17988.350613375] iani iani 250515_152716
//: ------- testing GD typeVerse with all verses!
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerses;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
Tsr.doOnVerse({ | index, verse, incipit |
	"New verse received".postln;
	postln("index" + index);
	postln("verse" + verse);
	postln("incipit" + incipit);
}, \iani1);
OSC.add(\cret, { "\n\n===carriage return===\n\n".postln; })
TypingActions2(); // test gd verse matching

//:[17996.078076625] iani iani 250515_152724
//: ------- testing GD typeVerse with all verses!
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerses;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
Tsr.doOnVerse({ | index, verse, incipit |
	"New verse received".postln;
	postln("index" + index);
	postln("verse" + verse);
	postln("incipit" + incipit);
}, \iani1);
OSC.add(\cret, { "\n\n===carriage return===\n\n".postln; });
TypingActions2(); // test gd verse matching

//:[18230.08424175] iani iani 250515_153118
//: ------- testing GD typeVerse with all verses!
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerses;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
Tsr.doOnVerse({ | index, verse, incipit |
	"New verse received".postln;
	postln("index" + index);
	postln("verse" + verse);
	postln("incipit" + incipit);
}, \iani1);
OSC.add(\cret, { "\n\n===carriage return===\n\n".postln; });
TypingActions2(); // test gd verse matching

//:[18235.738129458] iani iani 250515_153123
//:
Tsr.undoOnType(\gdmusic);
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	postln("Tsr received char - for verse matching" + char);
}, \iani1);

//:[18250.219583042] iani iani 250515_153138
//: ------- testing GD typeVerse with all verses!
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerses;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
Tsr.doOnVerse({ | index, verse, incipit |
	"New verse received".postln;
	postln("index" + index);
	postln("verse" + verse);
	postln("incipit" + incipit);
}, \iani1);
OSC.add(\cret, { "\n\n===carriage return===\n\n".postln; });
TypingActions2(); // test gd verse matching

//:[18363.919062667] iani iani 250515_153332
//: ------- testing GD typeVerse with all verses!
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerses;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
Tsr.doOnVerse({ | index, verse, incipit |
	"New verse received".postln;
	postln("index" + index);
	postln("verse" + verse);
	postln("incipit" + incipit);
}, \iani1);
OSC.add(\cret, { "\n\n===carriage return===\n\n".postln; });
TypingActions2(); // test gd verse matching

//:[18590.437478125] iani iani 250515_153718
//: ------- testing GD typeVerse with all verses!
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerses;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
Tsr.doOnVerse({ | index, verse, incipit |
	"New verse received".postln;
	postln("index" + index);
	postln("verse" + verse);
	postln("incipit" + incipit);
}, \iani1);
OSC.add(\cret, { "\n\n===carriage return===\n\n".postln; });
TypingActions2(); // test gd verse matching

//:[18644.993032333] iani iani 250515_153813
//: ------- testing GD typeVerse with all verses!
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerses;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
Tsr.doOnVerse({ | index, verse, incipit |
	"New verse received".postln;
	postln("====== index" + index);
	postln("====== verse ======" + verse);
	postln("====== incipit" + incipit);
}, \iani1);
OSC.add(\cret, { "\n\n===carriage return===\n\n".postln; });
TypingActions2(); // test gd verse matching

//:[18798.441849] iani iani 250515_154046
//: ------- testing GD typeVerse with all verses!
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerses;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
Tsr.doOnVerse({ | index, verse, incipit |
	"New verse received".postln;
	postln("====== index" + index);
	postln("====== verse ======" + verse);
	postln("====== incipit" + incipit);
}, \iani1);
OSC.add(\cret, { "\n\n===carriage return===\n\n".postln; });
TypingActions2(); // test gd verse matching

//:[19128.997815333] iani iani 250515_154617
//: ------- testing GD typeVerse with all verses!
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerses;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
Tsr.doOnVerse({ | index, verse, incipit |
	"New verse received".postln;
	postln("index" + index);
	postln("verse" + verse);
	postln("incipit" + incipit);
}, \iani1);
OSC.add(\cret, { "\n\n===carriage return===\n\n".postln; });
TypingListener(); // test gd verse matching

//:[19222.264673125] iani iani 250515_154750
//: ------- testing GD typeVerse with all verses!
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerses;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
Tsr.doOnVerse({ | index, verse, incipit |
	"New verse received".postln;
	postln("index" + index);
	postln("verse" + verse);
	postln("incipit" + incipit);
}, \iani1);
OSC.add(\cret, { "\n\n===carriage return===\n\n".postln; });
TypingListener(); // test gd verse matching

//:[19703.961371417] iani iani 250515_155552
thisProcess.elapsedTime;
//:[19709.7113655] iani iani 250515_155557
Main.elapsedTime;
//:[19755.895056583] iani iani 250515_155644
//: ------- testing GD typeVerse
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerse;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
TypingListener(); // test gd verse matching

//:[19775.883026458] iani iani 250515_155704
//: ------- testing GD typeVerse with all verses!
Tsr.undoOnType(\iani1);
b = TsrType(\iani).actions;
b.typeVerses;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
Tsr.doOnVerse({ | index, verse, incipit |
	"New verse received".postln;
	postln("index" + index);
	postln("verse" + verse);
	postln("incipit" + incipit);
}, \iani1);
OSC.add(\cret, { "\n\n===carriage return===\n\n".postln; });
TypingListener(); // test gd verse matching

//:[20643.49168225] iani iani 250515_161131
//: ------- testing recording of typing !
Tsr.undoOnType(\iani1);
TypingListener(); // test gd verse matching
c = RecordTyping().activate;
b = TypeTsr(\iani).actions;
b.typeVerses;

//:[20750.950488958] iani iani 250515_161319
c;
//:[20757.036884167] iani iani 250515_161325
c.lines;
//:[20771.024102917] iani iani 250515_161339
c.lines.size;
//:[225.481983291] iani iani 250515_174931
c = c add: a;
//:[233.636058125] iani iani 250515_174939
a = [[1, 2] [3, 4]];
//:[234.593569875] iani iani 250515_174940
b = [[1, 2] [3, 4]];
//:[252.100899958] iani iani 250515_174957
c = nil;
//:[253.325545791] iani iani 250515_174959
c = c add: a;
//:[255.644193583] iani iani 250515_175001
a = [[1, 2] [3, 4]];
//:[257.051817083] iani iani 250515_175002
b = [[1, 2] [3, 4]];
//:[258.335759] iani iani 250515_175004
c = nil;
//:[259.685806541] iani iani 250515_175005
c = c add: a;
//:[264.776566416] iani iani 250515_175010
a = [[1, 2] [3, 4]];
//:[275.619703541] iani iani 250515_175021
a = [[1, 2], [3, 4]];
//:[277.996610708] iani iani 250515_175023
b = [[1, 2], [3, 4]];
//:[279.559942333] iani iani 250515_175025
c = nil;
//:[281.063152083] iani iani 250515_175026
c = c add: a;
//:[298.383146] iani iani 250515_175044
c.flatten2;
//:[310.683504083] iani iani 250515_175056
a = [[1, 2], [3, 4]];
//:[311.93638825] iani iani 250515_175057
b = [[1, 2], [3, 4]];
//:[314.524639708] iani iani 250515_175100
c = c add: a;
//:[324.621589625] iani iani 250515_175110
b = [[1, 2], [3, 4]] + 4;
//:[328.174717833] iani iani 250515_175113
c = nil;
//:[329.135387041] iani iani 250515_175114
c = c add: a;
//:[332.616494] iani iani 250515_175118
c = c add: b;
//:[339.316689458] iani iani 250515_175124
c.flatten2;
//:[535.892390625] iani iani 250515_175441
//:
// Record and replay of typed data.
Tsr.undoOnType(\iani1);
b = TypeTsr(\iani).actions;
b.typeVerses;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
Tsr.doOnVerse({ | index, verse, incipit |
	"New verse received".postln;
	postln("====== index" + index);
	postln("====== verse ======" + verse);
	postln("====== incipit" + incipit);
}, \iani1);
OSC.add(\cret, { "\n\n===carriage return===\n\n".postln; });
TypingListener(); // test gd verse matching

//:[651.651230291] iani iani 250515_175637
//:
// Record and replay of typed data.
Tsr.undoOnType(\iani1);
b = TypeTsr(\iani).actions;
b.typeVerses;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
Tsr.doOnVerse({ | index, verse, incipit |
	"New verse received".postln;
	postln("====== index" + index);
	postln("====== verse ======" + verse);
	postln("====== incipit" + incipit);
}, \iani1);
OSC.add(\cret, { "\n\n===carriage return===\n\n".postln; });
TypingListener(); // test gd verse matching

//:[61.29432825] iani iani 250515_175835
b = TypeTsr(\iani).actions;
//:[64.625028834] iani iani 250515_175838
b.typeVerses;
//:[132.383886834] iani iani 250515_175946
//:
// Record and replay of typed data.
b = TypeTsr(\iani).actions;
b.typeVerses;

//:[134.477560209] iani iani 250515_175948
//:
Tsr.doOnVerse({ | index, verse, incipit |
	"New verse received".postln;
	postln("====== index" + index);
	postln("====== verse ======" + verse);
	postln("====== incipit" + incipit);
}, \iani1);

//:[174.376038459] iani iani 250515_180028
Tsr.doOnType({ | char | char.postln; }, \iani1);
//:[346.924729375] iani iani 250515_180320
//: Record and replay of typed data.
b = TypeTsr(\iani).actions;
b.verbose = true;
b.typeVerses;

//:[381.191997959] iani iani 250515_180355
b = TypeTsr(\iani).actions;
//:[385.801240584] iani iani 250515_180359
b.verbose_(true);
//:[410.218440375] iani iani 250515_180424
b.action;
//:[468.219303709] iani iani 250515_180522
//: Record and replay of typed data.
b = TypeTsr(\iani).actions;
TypingListener.verbose = true;
b.typeVerses;

//:[473.801577875] iani iani 250515_180527
b.typeVerses;
//:[488.359196] iani iani 250515_180542
//: Record and replay of typed data.
b = TypeTsr(\iani).actions;
TypingListener.verbose = true;
TypingListener();
b.typeVerses;

//:[282.178862] iani iani 250515_181149
TypingListener.verbose = true;
//:[285.212830583] iani iani 250515_181152
TypingListener()
//:[315.906560291] iani iani 250515_181223
Tsr.type($a);
//:[320.595553875] iani iani 250515_181227
Tsr.type($a);
//:[333.203343458] iani iani 250515_181240
Tsr.doOnType({ | char | char.postln; }, \iani1);
//:[335.244565041] iani iani 250515_181242
Tsr.type($a);
//:[336.477892916] iani iani 250515_181243
Tsr.type($a);
//:[337.92578425] iani iani 250515_181245
Tsr.type($a);
//:[339.278569916] iani iani 250515_181246
Tsr.type($a);
//:[353.150533583] iani iani 250515_181300
TypingListener.verbose = false;
//:[358.288092833] iani iani 250515_181305
Tsr.type($a);
//:[359.320127958] iani iani 250515_181306
Tsr.type($a);
//:[360.391126666] iani iani 250515_181307
Tsr.type($a);
//:[621.978982708] iani iani 250515_181729
i2
//:[625.513776625] iani iani 250515_181732
~i2
//:[630.42249275] iani iani 250515_181737
~i2.play;
//:[664.407505291] iani iani 250515_181811
TypeTsr.typeVerse;
//:[718.04051925] iani iani 250515_181905
TypeTsr.typeVerse;
//:[780.35850525] iani iani 250515_182007
TypeTsr.verbose;
//:[785.845892875] iani iani 250515_182013
TypeTsr.verbose = true;
//:[787.840000791] iani iani 250515_182015
TypeTsr.typeVerse;
//:[893.642794875] iani iani 250515_182200
TypeTsr.verbose = true;
//:[894.981547416] iani iani 250515_182202
TypeTsr.typeVerse;
//:[915.114038333] iani iani 250515_182222
TypeTsr.typeVerse;
//:[932.378310208] iani iani 250515_182239
TypingListener();
//:[964.254706583] iani iani 250515_182311
TypeTsr.typeVerse;
//:[11.210977375] iani iani 250515_182408
TypingListener();
//:[13.451645916] iani iani 250515_182411
TypeTsr.verbose = true;
//:[14.984384291] iani iani 250515_182412
TypeTsr.typeVerse;
//:[104.938633125] iani iani 250515_182609
b = TypeTsr(\iani).actions;
//:[108.239461916] iani iani 250515_182612
TypingListener.verbose = true;
//:[111.53486175] iani iani 250515_182616
TypingListener();
//:[115.413777208] iani iani 250515_182619
TypeTsr.typeVerse;
//:[188.683861791] iani iani 250515_182733
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerse;

//:[191.12643125] iani iani 250515_182735
TypingListener();
//:[287.326067916] iani iani 250515_182911
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerse;
// Test whether this makes the Segmentation Fault:
TypingListener();

//:[322.49199725] iani iani 250515_182947
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerse;
// Test whether this makes the Segmentation Fault:
TypingListener();

//:[19.824417667] iani iani 250515_183046
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerse;
// Test whether this makes the Segmentation Fault:
TypingListener();

//:[13.559378875] iani iani 250515_183218
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerse;
// Test whether this makes the Segmentation Fault:
TypingListener();

//:[75.5398535] iani iani 250515_183320
				Char.nl.asString.post;
//:[77.432345792] iani iani 250515_183322
				Char.nl.asString.post;
//:[78.345991] iani iani 250515_183323
				Char.nl.asString.post;
//:[79.032051084] iani iani 250515_183323
				Char.nl.asString.post;
//:[162.860696709] iani iani 250515_183447
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerse;
// Test whether this makes the Segmentation Fault:
TypingListener();

//:[224.758560792] iani iani 250515_183549
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerse;
// Test whether this makes the Segmentation Fault:
TypingListener();

//:[284.768333792] iani iani 250515_183649
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerse;
// Test whether this makes the Segmentation Fault:
TypingListener();

//:[312.969052959] iani iani 250515_183717
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
// Test whether this makes the Segmentation Fault:
TypingListener();

//:[453.525843875] iani iani 250515_183938
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
// Test whether this makes the Segmentation Fault:
TypingListener();

//:[568.8008535] iani iani 250515_184133
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
// Test whether this makes the Segmentation Fault:
TypingListener();

//:[631.137534084] iani iani 250515_184235
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
// Test whether this makes the Segmentation Fault:
TypingListener();

//:[738.660344959] iani iani 250515_184423
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
// Test whether this makes the Segmentation Fault:
TypingListener();

//:[16.019674917] iani iani 250515_184506
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
// Test whether this makes the Segmentation Fault:
TypingListener();

//:[30.52219525] iani iani 250515_184552
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
TypingListener(); // Test whether this makes the Segmentation Fault:

//:[17.323642208] iani iani 250515_184646
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
TypingListener(); // Test whether this makes the Segmentation Fault:

//:[149.831345708] iani iani 250515_184859
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
TypingListener(); // Test whether this makes the Segmentation Fault:

//:[233.546989583] iani iani 250515_185023
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
TypingListener(); // Test whether this makes the Segmentation Fault:

//:[261.968208583] iani iani 250515_185051
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
TypingListener(); // Test whether this makes the Segmentation Fault:

//:[658.552607792] iani iani 250515_185728
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
TypingListener();
RecordTyping().activate;

//:[750.412131833] iani iani 250515_185859
RecordTyping(\test).activate;
//:[772.22139125] iani iani 250515_185921
RecordTyping(\test);
//:[777.531277583] iani iani 250515_185927
RecordTyping(\test);
//:[794.36906425] iani iani 250515_185943
a = RecordTyping(\test);
//:[802.94278025] iani iani 250515_185952
RecordTyping(\test).activate;
//:[818.235089875] iani iani 250515_190007
RecordTyping(\test).lines;
//:[934.668736417] iani iani 250515_190204
RecordTyping(\test).lines;
//:[936.743400708] iani iani 250515_190206
RecordTyping(\test).lines;
//:[937.911327875] iani iani 250515_190207
RecordTyping(\test).lines;
//:[938.933235167] iani iani 250515_190208
RecordTyping(\test).lines;
//:[939.841120458] iani iani 250515_190209
RecordTyping(\test).lines;
//:[940.534863625] iani iani 250515_190210
RecordTyping(\test).lines;
//:[941.26887725] iani iani 250515_190210
RecordTyping(\test).lines;
//:[941.884865208] iani iani 250515_190211
RecordTyping(\test).lines;
//:[942.389851875] iani iani 250515_190211
RecordTyping(\test).lines;
//:[942.856011208] iani iani 250515_190212
RecordTyping(\test).lines;
//:[943.320656917] iani iani 250515_190212
RecordTyping(\test).lines;
//:[943.767952083] iani iani 250515_190213
RecordTyping(\test).lines;
//:[944.189453417] iani iani 250515_190213
RecordTyping(\test).lines;
//:[944.618810875] iani iani 250515_190214
RecordTyping(\test).lines;
//:[945.002393542] iani iani 250515_190214
RecordTyping(\test).lines;
//:[945.411503042] iani iani 250515_190214
RecordTyping(\test).lines;
//:[945.759698125] iani iani 250515_190215
RecordTyping(\test).lines;
//:[998.734919208] iani iani 250515_190308
RecordTyping(\test).lines;
//:[999.42649125] iani iani 250515_190308
RecordTyping(\test).lines;
//:[1102.870440208] iani iani 250515_190452
a = List();
//:[1106.942950667] iani iani 250515_190456
a.array;
//:[1135.545413708] iani iani 250515_190525
a = RecordTyping(\test);
//:[1139.803848583] iani iani 250515_190529
RecordTyping(\test).lines;
//:[1142.270514417] iani iani 250515_190531
RecordTyping(\test).lines;
//:[1173.339593708] iani iani 250515_190602
RecordTyping(\test).replay;
//:[1185.208831792] iani iani 250515_190614
RecordTyping(\test).replay1;
//:[1383.075832917] iani iani 250515_190932
pi
//:[1387.20820175] iani iani 250515_190936
RecordTyping(\test).lines;
//:[1392.041046083] iani iani 250515_190941
RecordTyping(\test).replay1;
//:[1483.594080708] iani iani 250515_191113
RecordTyping(\test).replay1;
//:[1506.233871083] iani iani 250515_191135
RecordTyping(\test).replay1;
//:[1633.158518042] iani iani 250515_191342
RecordTyping(\test).replay1;
//:[1685.908537458] iani iani 250515_191435
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
TypingListener();
RecordTyping(\test);


//:[1713.290760917] iani iani 250515_191502
RecordTyping(\test).replay1;
//:[2306.245755167] iani iani 250515_192455
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
TypingListener();
RecordTyping(\test);

//:[2312.829580333] iani iani 250515_192502
RecordTyping(\test).lines;
//:[2338.526181292] iani iani 250515_192528
a = RecordTyping(\test).lines;
//:[2342.363677042] iani iani 250515_192531
a;
//:[2346.212571667] iani iani 250515_192535
a[0]
//:[2352.190157167] iani iani 250515_192541
a[0][0]
//:[2433.020806] iani iani 250515_192702
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
TypingListener();
RecordTyping(\test);

//:[2436.302322458] iani iani 250515_192705
RecordTyping(\test).post;
//:[2443.863600833] iani iani 250515_192713
RecordTyping(\test).unPost;
//:[2536.37459825] iani iani 250515_192845
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
TypingListener();
RecordTyping(\test);

//:[2544.802403917] iani iani 250515_192854
a = RecordTyping(\test).lines;
//:[2548.129120167] iani iani 250515_192857
a = RecordTyping(\test).lines;
//:[2553.490709708] iani iani 250515_192902
RecordTyping(\test).post;
//:[2554.99925225] iani iani 250515_192904
RecordTyping(\test).replay1;
//:[2622.273136583] iani iani 250515_193011
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
TypingListener();
RecordTyping(\test);

//:[2627.3049985] iani iani 250515_193016
RecordTyping(\test).replay1;
//:[2680.195130292] iani iani 250515_193109
RecordTyping(\test).post;
//:[2683.347848125] iani iani 250515_193112
RecordTyping(\test).replay1;
//:[2785.378380958] iani iani 250515_193254
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
TypingListener();
RecordTyping(\test);

//:[2797.90581775] iani iani 250515_193307
RecordTyping(\test).post;
//:[2799.427364875] iani iani 250515_193308
RecordTyping(\test).replay1;
//:[3194.282315083] iani iani 250515_193943
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
TypingListener();
RecordTyping(\test);

//:[3199.7360155] iani iani 250515_193949
//:Test sound
RecordTyping(\test).replay1;
RecordTyping(\test).gdplay;

//:[3235.767910375] iani iani 250515_194025
RecordTyping(\test).gdplay;
//:[3238.964049375] iani iani 250515_194028
RecordTyping(\test).replay1;
//:[3298.803514542] iani iani 250515_194128
RecordTyping(\testxxx).replay1;
//:[3335.339648292] iani iani 250515_194204
RecordTyping(\test).replay1;
//:[3442.67327] iani iani 250515_194352
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
TypingListener();
RecordTyping(\test);

//:[3453.708753292] iani iani 250515_194403
RecordTyping(\test).replay1;
//:[3466.888004375] iani iani 250515_194416
RecordTyping(\test).replay1;
//:[3468.044514333] iani iani 250515_194417
RecordTyping(\test).gdplay;
//:[3548.360794792] iani iani 250515_194537
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
TypingListener();
RecordTyping(\test);

//:[3556.460274375] iani iani 250515_194545
//:Test sound
RecordTyping(\test).replay1;
RecordTyping(\test).gdplay;

//:[3608.1589815] iani iani 250515_194637
RecordTyping(\test).post;
//:[3609.946700583] iani iani 250515_194639
RecordTyping(\test).post;
//:[3611.41120925] iani iani 250515_194640
RecordTyping(\test).post;
//:[3663.840544833] iani iani 250515_194733
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
TypingListener();
RecordTyping(\test);

//:[3669.034088708] iani iani 250515_194738
//:Test sound
RecordTyping(\test).replay1;
RecordTyping(\test).gdplay;

//:[3708.821057958] iani iani 250515_194818
//: Testing just the type verse
// with fres compile!
TypeTsr.verbose = true;
TypeTsr.typeVerses;
TypingListener();
RecordTyping(\test);

//:[3714.458700875] iani iani 250515_194823
//:Test sound
RecordTyping(\test).replay1;
RecordTyping(\test).gdplay;

//:[3789.928632375] iani iani 250515_194939
a = Synth(\pinch);
//:[3816.150097708] iani iani 250515_195005
a = Synth(\pinch, [freq: 90, ffreq: 30]);
//:[3817.428781833] iani iani 250515_195006
a = Synth(\pinch, [freq: 90, ffreq: 30]);
//:[3827.120393917] iani iani 250515_195016
a = Synth(\pinch, [freq: 900, ffreq: 300]);
//:[3841.105892] iani iani 250515_195030
a = Synth(\pinch, [freq: 900, ffreq: 300, dur: 3]);
//:[3903.14960275] iani iani 250515_195132
\test play: (instrument: \pinch, freq: char*3, ffreq: char / 3);
//:[3917.648981917] iani iani 250515_195147
\test play: (instrument: \pinch, freq: 59*3, ffreq: 59 / 3);
//:[3930.687712458] iani iani 250515_195200
\test play: (instrument: \pinch, freq: 59*3, ffreq: 59 / 3, dur: 0.1);
//:[3981.62448175] iani iani 250515_195251
\test2 play: (instrument: \pinch);
//:[4028.672642458] iani iani 250515_195338
\test2 play: (freq: (100..500).pseq(1), dur: 0.1);
//:[4036.762543708] iani iani 250515_195346
\test2 play: (freq: (100..500).pseq(1), dur: 0.01);
//:[4047.976497083] iani iani 250515_195357
\test2 play: (freq: (100..500).pseq(1), dur: 0.03);
//:[4085.471146125] iani iani 250515_195434
\test2 play: (ffreq: (100..500).reverse.pseq(1));
//:[4830.164620458] iani iani 250515_200659
72
//:[4830.169478458] iani iani 250515_200659
72
//:[5019.127019083] iani iani 250515_201008
//:
// 木 15  5 2025 20:06 TGrain on verse voice samples
{
    var trate, dur, rate, b;
	b = ~i1;
    trate = MouseY.kr(2,200,1);
    dur = 4 / trate;
    rate = Dseq([10, 1, 1, 0.5, 0.5, 0.2, 0.1], inf);
    TGrains.ar(2, Impulse.ar(trate), b, rate, MouseX.kr(0,BufDur.kr(b)), dur, Dseq([-1, 1], inf), 0.1, 2);
}.play;
//:[5250.248648875] iani iani 250515_201359
//:
// 木 15  5 2025 20:06 TGrain on verse voice samples
a = { | trate = 4, duratio = 4, rate = 1, pos = 0.5
    var trate, dur, rate, b;
	b = ~i1;
    trate = MouseY.kr(2,200,1);
    dur = duratio / trate;
    TGrains.ar(2, Impulse.ar(trate), b, rate, BufDur.kr(b) * pos, dur, Dseq([-1, 1], inf), 0.1, 2);
}.play;

//:[5270.762866958] iani iani 250515_201420
//:
// 木 15  5 2025 20:06 TGrain on verse voice samples
a = { | trate = 4, duratio = 4, rate = 1, pos = 0.5
    var dur, b;
	b = ~i1;
    trate = MouseY.kr(2,200,1);
    dur = duratio / trate;
    TGrains.ar(2, Impulse.ar(trate), b, rate, BufDur.kr(b) * pos, dur, Dseq([-1, 1], inf), 0.1, 2);
}.play;

//:[5277.725795625] iani iani 250515_201427
//:
// 木 15  5 2025 20:06 TGrain on verse voice samples
a = { | trate = 4, duratio = 4, rate = 1, pos = 0.5 |
    var dur, b;
	b = ~i1;
    trate = MouseY.kr(2,200,1);
    dur = duratio / trate;
    TGrains.ar(2, Impulse.ar(trate), b, rate, BufDur.kr(b) * pos, dur, Dseq([-1, 1], inf), 0.1, 2);
}.play;

//:[5295.544170583] iani iani 250515_201445
//:
// 木 15  5 2025 20:06 TGrain on verse voice samples
a = { | trate = 4, duratio = 4, rate = 1, pos = 0.5 |
    var dur, b;
	b = ~i1;
    dur = duratio / trate;
    TGrains.ar(2, Impulse.ar(trate), b, rate, BufDur.kr(b) * pos, dur, Dseq([-1, 1], inf), 0.1, 2);
}.play;

//:[5321.862334125] iani iani 250515_201511
a.set(trate: 10);
//:[5330.317490833] iani iani 250515_201519
a.set(\trate, 10);
//:[5348.17639] iani iani 250515_201537
a.set(\duratio, 2);
//:[5352.845286458] iani iani 250515_201542
a.set(\duratio, 20);
//:[5357.566320167] iani iani 250515_201547
a.set(\duratio, 10);
//:[5372.967062] iani iani 250515_201602
a.set(\rate, 10);
//:[5377.83724675] iani iani 250515_201607
a.set(\rate, 1);
//:[5381.811917708] iani iani 250515_201611
a.set(\rate, 0.4);
//:[5384.901306417] iani iani 250515_201614
a.set(\rate, 0.4);
//:[5419.173659917] iani iani 250515_201648
a.set(\pos, 0.4);
//:[5423.732674375] iani iani 250515_201653
a.set(\pos, 0.1);
//:[5429.1589975] iani iani 250515_201658
a.set(\pos, 0.01);
//:[5434.769556042] iani iani 250515_201704
a.set(\pos, 0.99);
//:[5654.901614792] iani iani 250515_202044
\b.bus;
//:[5817.185628917] iani iani 250515_202326
currentEnvironment.name;
//:[5841.1424315] iani iani 250515_202350
currentEnvironment.mediator;
//:[5849.01650675] iani iani 250515_202358
currentEnvironment.mediator.class;
//:[5882.092404625] iani iani 250515_202431
\b.bus;
//:[5935.446334875] iani iani 250515_202524
\b.bus;
//:[6549.89126125] iani iani 250515_203539
72
//:[6549.895438458] iani iani 250515_203539
72
//:[7170.347786333] iani iani 250515_204559
{ WhiteNoise.ar(0.1) }.bmap;
//:[7238.825020125] iani iani 250515_204708
72
//:[7238.829254625] iani iani 250515_204708
72
//:[7665.719134542] iani iani 250515_205415
(abld: 1) keysValuesArrayDo: { | key, value | [key, value].postln; }
//:[7669.095983458] iani iani 250515_205418
(abld: 1) keysValuesArrayDo: { | key, value | [key, value].postln; }
//:[7677.805616583] iani iani 250515_205427
(abld: 1) keysValuesArrayDo: { | key, value | [key, value].postln; }
//:[7716.689272625] iani iani 250515_205506
(abld: 1) keysValuesDo: { | key, value | [key, value].postln; }
//:[7818.644559083] iani iani 250515_205648
a = BusMap((freq: 400))
//:[7827.437586042] iani iani 250515_205656
a.asMapArgs;
//:[7831.721117875] iani iani 250515_205701
a = BusMap((freq: 400))
//:[7835.29495425] iani iani 250515_205704
a.asMapArgs;
//:[8010.902630958] iani iani 250515_210000
a = BusMap((freq: 400))
//:[8012.4646635] iani iani 250515_210001
a.asMapArgs;
//:[8020.27109575] iani iani 250515_210009
a.mapArgs;
//:[8054.047632958] iani iani 250515_210043
a = BusMap((freq: 400))
//:[8055.591135667] iani iani 250515_210045
a.mapArgs;
//:[8133.481193208] iani iani 250515_210202
a = BusMap((freq: 4000));
//:[8135.8169885] iani iani 250515_210205
{ | freq: 400 | SinOsc.ar(freq, 0, 0.1) }.bmap(a);
//:[8142.817946708] iani iani 250515_210212
{ | freq = 400 | SinOsc.ar(freq, 0, 0.1) }.bmap(a);
//:[8172.112616208] iani iani 250515_210241
//:
// 木 15  5 2025 20:31
// automatically map a collection of named buses to the controls of a synth
// also handle creation/stopping of control synths that write to these buses
BusMap {
	var <dict;

	*new { | keyVals |
		^this.newCopyArgs(()).init(keyVals);
	}

	init { | keyVals |
		keyVals keysValuesDo: { | k, v | dict[k] = k.bus(v);};
	}

	mapArgs {
		var args;
		dict keysValuesDo: { | k, v | args = args add: k; args = args add: v.index };
		^args;
	}
}
// TODO: should use new paused?
/*
a = BusMap((freq: 400))
a.mapArgs;

*/
// target, outbus: 0, fadeTime: 0.02, addAction: 'addToHead', args, player, envir)
+ Function {
	bmap { | bmap, target, outbus = 0, fadeTime = 0.02, addAction = 'addToHead', args |
		var synth;
		synth = this.play(target, outbus, fadeTime, addAction, args);
		synth.onStart({ | s |
			s.map(*bmap.mapArgs);
		});
	}
}

/*
a = BusMap((freq: 4000));
{ | freq = 400 | SinOsc.ar(freq, 0, 0.1) }.bmap(a);
*/

//:[8179.974356125] iani iani 250515_210249
a = BusMap((freq: 4000));
//:[8181.610255792] iani iani 250515_210251
{ | freq = 400 | SinOsc.ar(freq, 0, 0.1) }.bmap(a);
//:[12.625489292] iani iani 250515_213159
//: ------- testing GD typeVerse with all verses!
Tsr.undoOnType(\iani1);
b = TypeTsr(\iani).actions;
b.typeVerses;
Tsr.doOnType({ | char |
	// postln("Tsr received char - for verse matching" + char);
}, \iani1);
Tsr.doOnVerse({ | index, verse, incipit |
	"New verse received".postln;
	postln("====== index" + index);
	postln("====== verse ======" + verse);
	postln("====== incipit" + incipit);
}, \iani1);
OSC.add(\cret, { "\n\n===carriage return===\n\n".postln; });
TypingListener(); // test gd verse matching

//:[201.655075167] iani iani 250515_213508
//:
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR KOSMA".postln;
	postln("index" + index + "convereted index string" + (100 + index).asString[1..].asCompileString);
}, \kosmaverse1);
//:[245.813281333] iani iani 250515_213552
OSC.remove(\cret);
//:[760.747904958] iani iani 250515_214427
//:
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse);
}, \kosmaverse1);
//:[829.630778333] iani iani 250515_214536
//:
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse);
	LiveText.text(verse);
}, \vasiliverse1);
//:[842.454381542] iani iani 250515_214548
Tsr.undoOnVerse(\kosmavers1);
//:[845.305190167] iani iani 250515_214551
//:
Tsr.doOnVerse({ | index, verse, incipit |
	"FOR VASILI".postln;
	postln("verse" + verse);
	LiveText.text(verse);
}, \vasiliverse1);

//:[1238.335005125] iani iani 250515_215224
10000.rand;
//:[2525.372876333] iani iani 250515_221351
\verse !!! "this is a verse";
//:[2530.727976917] iani iani 250515_221357
~verse;
//:[2576.659472125] iani iani 250515_221443
"\verse !!! verse;"
//:[2581.207093708] iani iani 250515_221447
"\\verse !!! verse;"
//:[7837.422294583] iani iani 250515_234223
a = \i;
//:[7851.251235958] iani iani 250515_234237
(a++1)
//:[7858.339808125] iani iani 250515_234244
(a++1).asSymbol;
//:[7874.703224917] iani iani 250515_234301
currentEnvironment[(a++1).asSymbol];
//:[7893.865434042] iani iani 250515_234320
currentEnvironment[(a++1).asSymbol].play;
//:[9236.3010705] iani iani 250516_000542
//:
\x ndef: {
	arg buf=0;
    var trate, clk, pos;

	trate = MouseY.kr(0.01,120,1);
	// trate = SinOsc.kr(0.04).range(0.1, 150);
    clk = Impulse.kr(trate);

	pos = MouseX.kr(0,BufDur.kr(buf) + TRand.kr(0, 0.01, clk));
	// pos = 10 + TRand.kr(0, 0.2, clk);

    TGrains.ar(
		2,
		clk,
		buf,
		1,
		pos,
		2 / trate, // dur of grain
		WhiteNoise.kr(0.6),
		0.3);
});

//:[9243.248887542] iani iani 250516_000549
//:
\x ndef: {
	arg buf=0;
    var trate, clk, pos;

	trate = MouseY.kr(0.01,120,1);
	// trate = SinOsc.kr(0.04).range(0.1, 150);
    clk = Impulse.kr(trate);

	pos = MouseX.kr(0,BufDur.kr(buf) + TRand.kr(0, 0.01, clk));
	// pos = 10 + TRand.kr(0, 0.2, clk);

    TGrains.ar(
		2,
		clk,
		buf,
		1,
		pos,
		2 / trate, // dur of grain
		WhiteNoise.kr(0.6),
		0.3);
};

//:[9255.51901525] iani iani 250516_000602
\x.play;
//:[10163.058193] iani iani 250516_002109
~voice1
//:[10168.722500167] iani iani 250516_002115
~verses1
//:[10173.799199625] iani iani 250516_002120
~verses1.choose;
//:[10289.315541583] iani iani 250516_002315
~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]);
//:[10295.451319458] iani iani 250516_002321
~vrs.set(\buf, ~verses1.choose);
//:[10300.751445417] iani iani 250516_002327
~vrs.set(\buf, ~verses1.choose);
//:[10315.231048042] iani iani 250516_002341
~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]);
//:[10321.302895792] iani iani 250516_002347
~vrs.set(\buf, ~verses1.choose);
//:[10327.510654333] iani iani 250516_002354
~vrs.set(\buf, ~verses1.choose);
//:[10424.299684083] iani iani 250516_002530
//:
\x.play;

Tsr.doOnVerse({ | index, verse, incipit |
	"Verse changed".postln;
	~verses[index.asInteger].postln;
	b = currentEnvironment[(~incipits[index]++[1,2,3,4].choose).asSymbol];
	if (~vrs.isNil) {
		~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]);
	}{
		~vrs.set(\buf, ~verses1.choose);
	};
}, \penny);


~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]);
~vrs.set(\buf, ~verses1.choose);

~verses1.choose;
"\\verse !!! verse;"

\a.ndef(\p)
//:[10434.064239875] iani iani 250516_002540
//:
Tsr.doOnVerse({ | index, verse, incipit |
	"Verse changed".postln;
	~verses[index.asInteger].postln;
	b = currentEnvironment[(~incipits[index]++[1,2,3,4].choose).asSymbol];
	if (~vrs.isNil) {
		~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]);
	}{
		~vrs.set(\buf, ~verses1.choose);
	};
}, \penny);

//:[10498.305815875] iani iani 250516_002644
~vrs = nil;
//:[10510.314856458] iani iani 250516_002656
//:
Tsr.doOnVerse({ | index, verse, incipit |
	"Verse changed".postln;
	~verses[index.asInteger].postln;
	b = currentEnvironment[(~incipits[index]++[1,2,3,4].choose).asSymbol];
	if (~vrs.isPlaying.not) {
		~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]).register;
	}{
		~vrs.set(\buf, ~verses1.choose);
	};
}, \penny);

//:[10570.674073625] iani iani 250516_002757
//:
Tsr.doOnVerse({ | index, verse, incipit |
	"Verse changed".postln;
	~verses[index.asInteger].postln;
	b = currentEnvironment[(~incipits[index]++[1,2,3,4].choose).asSymbol];
	if (~vrs.isPlaying.not) {
		~vrs = Synth(\p, [\buf, b, \l, 1, \amp, 0.1]).register;
	}{
		~vrs.set(\buf, ~verses1[index]);
	};
}, \penny);

//:[10840.779596958] iani iani 250516_003227
//:
Tsr.doOnVerse({ | index, verse, incipit |
	"Verse changed".postln;
	~verses[index.asInteger].postln;
	b = currentEnvironment[(~incipits[index]++[1,2,3,4].choose).asSymbol];
	if (~vrs.isPlaying) {
		~vrs.set(\buf, ~verses1[index]);
	};
}, \penny);

//:[11390.836171] iani iani 250516_004137
b.play;
//:[12980.909526625] iani iani 250516_010807
//:
// 金 16  5 2025 01:06
// Show typing received from TypingListener
\tsr.hlayout(
	var tv = TextView()
)
//:[13017.322821792] iani iani 250516_010843
//: Show typing received from TypingListener
var tv;
\tsr.hlayout(
	tv = TextView()
)
//:[13155.362258625] iani iani 250516_011101
//: Show typing received from TypingListener
var tv, txt;
\tsr.hlayout(
	tv = TextView();
);
Tsr.doOnType({ | char |
	txt = tv.string;
	char.postln;
	//	tv.string = txt ++ char.
}, \textwindow)
//:[13195.635655167] iani iani 250516_011142
//: Show typing received from TypingListener
var tv, txt;
\tsr.hlayout(
	tv = TextView();
);
Tsr.doOnType({ | char |
	{
	txt = tv.string;
	char.postln;
	}.defer;
	//	tv.string = txt ++ char.
}, \textwindow)
//:[13269.486309167] iani iani 250516_011255
//: Show typing received from TypingListener
var tv, txt;
\tsr.hlayout(
	tv = TextView();
);
Tsr.doOnType({ | char |
	{
	txt = tv.string;
	tv.string = txt ++ (char.asAscii.asString);
	// char.postln;
	}.defer;
	//	tv.string = txt ++ char.
}, \textwindow)
//:[13284.030903958] iani iani 250516_011310
//: Show typing received from TypingListener
var tv, txt;
\tsr.hlayout(
	tv = TextView();
);
Tsr.doOnType({ | char |
	{
	txt = tv.string;
	tv.string = txt ++ (char.asAscii.asString);
	// char.postln;
	}.defer;
	//	tv.string = txt ++ char.
}, \textwindow)
//:[13443.735060208] iani iani 250516_011550
//: Show typing received from TypingListener
var tv, txt;
\tsr.hlayout(
	tv = TextView();
);
OSC.addArgs(\tsrchar, { | char |
	{
	txt = tv.string;
	tv.string = txt ++ (char.asAscii.asString);
	// char.postln;
	}.defer;
})




//:[13451.448586208] iani iani 250516_011557
//: Show typing received from TypingListener
var tv, txt;
\tsr.hlayout(
	tv = TextView();
);
OSC.addArgs(\tsrchar, { | char |
	{
	txt = tv.string;
	tv.string = txt ++ (char.asAscii.asString);
	// char.postln;
	}.defer;
})




//:[13583.10923475] iani iani 250516_011809
//:
Tsr.doOnType({ | char |
	{
		txt = tv.string;
		char = char.asAscii;
		(char.isAlpha or: { char.isPunct } or: { char.isSpace }).if {
			tv.string = txt ++ (char.asString);
		}
	}.defer;
	//	tv.string = txt ++ char.
}, \textwindow)

//:[13600.894896917] iani iani 250516_011827
//: Show typing received from TypingListener
var tv, txt;
\tsr.hlayout(
	tv = TextView();
);
Tsr.doOnType({ | char |
	{
		txt = tv.string;
		char = char.asAscii;
		(char.isAlpha or: { char.isPunct } or: { char.isSpace }).if {
			tv.string = txt ++ (char.asString);
		}
	}.defer;
	//	tv.string = txt ++ char.
}, \textwindow)

//:[14582.836978875] iani iani 250516_013449
TsrPoem.verses;
//:[14613.607103083] iani iani 250516_013520
r
//:[14616.445706375] iani iani 250516_013522
r
//:[14618.745015792] iani iani 250516_013525
TsrPoem.verses;
//:[14630.839951458] iani iani 250516_013537
TsrPoem.incipits;
//:[16130.355976042] iani iani 250516_020036
//:
		SynthDef(\sines, {|outbus = 0, buffoffset = 0, offsetval = 0, gate = 1, freq = 261.63, freq2 = 329.63, freq3 = 415.30, freqdust = 1, amp = 0.4, pan = 0, vol = 0.03, fadeinvl = 0, fadeoutvl = 0, fade = 4, char|

			var env, source;
			env = EnvGen.kr(Env([fadeinvl, 0.8, fadeoutvl], [fade, fade]), gate, doneAction: 2);
			//env = EnvGen.kr(Env.perc, doneAction:2);
			source = SinOsc.ar([freq, freq2, freq3], 0, amp*env)+Saw.ar([freq, freq2, freq3], 0, amp*env)+FSinOsc.ar([freq*2, freq2*2, freq3*2], 0.5, amp*env)+SinOsc.ar(SinOsc.ar(freq*4, freq2*4, freq3*4), 0, amp*env);
			Out.ar(outbus, Pan2.ar(source*env*vol, pan))!2
		}).add;


//:[16223.223242333] iani iani 250516_020209
//:
		SynthDef(\sines, {|outbus = 0, buffoffset = 0, offsetval = 0, gate = 1, freq = 261.63, freq2 = 329.63, freq3 = 415.30, freqdust = 1, amp = 0.4, pan = 0, vol = 0.03, fadeinvl = 0, fadeoutvl = 0, fade = 4, char|

			var env, source;
			env = EnvGen.kr(Env([fadeinvl, 0.8, fadeoutvl], [fade, fade]), gate, doneAction: 2);
			//env = EnvGen.kr(Env.perc, doneAction:2);
			source = SinOsc.ar([freq, freq2, freq3], 0, amp*env)+Saw.ar([freq, freq2, freq3], 0, amp*env)+FSinOsc.ar([freq*2, freq2*2, freq3*2], 0.5, amp*env)+SinOsc.ar(SinOsc.ar(freq*4, freq2*4, freq3*4), 0, amp*env);
			Out.ar(outbus, Pan2.ar(source*env*vol, pan))!2
		}).add;


//:[16600.062695625] iani iani 250516_020826
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[16713.651170375] iani iani 250516_021020
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[16807.149512792] iani iani 250516_021153
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[17036.091798083] iani iani 250516_021542
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[17320.906177917] iani iani 250516_022027
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[17839.4891705] iani iani 250516_022905
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[19954.785489] iani iani 250516_030421
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[19985.959288875] iani iani 250516_030452
GrainEnvs();
//:[19996.671428208] iani iani 250516_030503
GrainEnvs.welch;
//:[20141.294427792] iani iani 250516_030727
https://github.com/iani/TheSunRisingGlobal
//:[20585.326883708] iani iani 250516_031451
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[20595.993576042] iani iani 250516_031502
ShowTyping();
//:[21060.815782417] iani iani 250516_032247
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[21259.011717542] iani iani 250516_032605
currentEnvironment[\verses1]
//:[21265.206637333] iani iani 250516_032611
currentEnvironment[\verses1][4]
//:[21266.801258542] iani iani 250516_032613
currentEnvironment[\verses1][4]
//:[21497.715294917] iani iani 250516_033004
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[21672.136445125] iani iani 250516_033258
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[21957.241879708] iani iani 250516_033743
Synth(\grainAn);
//:[21969.621575042] iani iani 250516_033756
Synth(\grainAn);
//:[21978.100718] iani iani 250516_033804
Synth(\grainAn);
//:[22315.463636625] iani iani 250516_034341
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[22558.612494625] iani iani 250516_034745
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[22709.841658542] iani iani 250516_035016
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[22751.535244083] iani iani 250516_035058
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[48825.792936] iani iani 250516_110532
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[49142.657425167] iani iani 250516_111049
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[49209.533680917] iani iani 250516_111156
ShowTyping();
//:[56267.8451055] iani iani 250516_130934
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[56332.333532083] iani iani 250516_131038
Diffuser(\iani) play: { SinOsc.ar(400) * 0.1 };
//:[56355.191146958] iani iani 250516_131101
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[56360.848365875] iani iani 250516_131107
Diffuser(\iani) play: { SinOsc.ar(400) * 0.1 };
//:[56393.994035625] iani iani 250516_131140
72
//:[56393.997679083] iani iani 250516_131140
72
//:[56503.400913125] iani iani 250516_131329
Node.addActions;
//:[56603.421860042] iani iani 250516_131509
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[56619.543768417] iani iani 250516_131526
z = Diffuser(\iani);
//:[56636.756788583] iani iani 250516_131543
z play: { SinOsc.ar(400) * 0.1 };
//:[56648.092853875] iani iani 250516_131554
z.scope;
//:[56654.403730667] iani iani 250516_131600
z play: { SinOsc.ar(400) * 0.1 };
//:[56723.402868792] iani iani 250516_131709
z = Diffuser(\iani);
//:[56723.658719] iani iani 250516_131710
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[56726.26467825] iani iani 250516_131712
z play: { SinOsc.ar(400) * 0.1 };
//:[56731.648985958] iani iani 250516_131718
z play: { SinOsc.ar(500) * 0.1 };
//:[56747.882253917] iani iani 250516_131734
z play: { SinOsc.ar(500) * 0.1 };
//:[56798.266259542] iani iani 250516_131824
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[56801.9624025] iani iani 250516_131828
a = z play: { SinOsc.ar(500) * 0.1 };
//:[56809.6945945] iani iani 250516_131836
a.isPlaying
//:[56844.696677792] iani iani 250516_131911
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[56848.462290583] iani iani 250516_131914
z = Diffuser(\iani);
//:[56850.459072292] iani iani 250516_131916
a = z play: { SinOsc.ar(500) * 0.1 };
//:[56867.212727208] iani iani 250516_131933
a
//:[56936.737123125] iani iani 250516_132043
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[56965.335226417] iani iani 250516_132111
z = Diffuser(\iani);
//:[56967.305452125] iani iani 250516_132113
a = z play: { SinOsc.ar(500) * 0.1 };
//:[57028.931921542] iani iani 250516_132215
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[57068.940437958] iani iani 250516_132255
{ WhiteNoise.ar(0.1) }.play;
//:[57141.378594458] iani iani 250516_132407
//:
{
	PanAz.ar(8, WhiteNoise.ar(0.1),
		LFSaw.kr(5.reciprocal)
	)
}.play
//:[57213.39111925] iani iani 250516_132519
Server.default.options.numOutputBusChannels = 8;
//:[57221.6969135] iani iani 250516_132528
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[57224.552464167] iani iani 250516_132531
//:
{
	PanAz.ar(8, WhiteNoise.ar(0.1),
		LFSaw.kr(5.reciprocal)
	)
}.play
//:[57320.099473458] iani iani 250516_132706
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[57329.014994458] iani iani 250516_132715
z = Diffuser(\iani);
//:[57332.218969042] iani iani 250516_132718
a = z play: { SinOsc.ar(500) * 0.1 };
//:[57355.754215833] iani iani 250516_132742
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[57363.399537292] iani iani 250516_132749
z = Diffuser(\iani);
//:[57366.0797795] iani iani 250516_132752
a = z play: { SinOsc.ar(500) * 0.1 };
//:[57406.746600083] iani iani 250516_132833
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[57431.118972917] iani iani 250516_132857
z = Diffuser(\iani);
//:[57433.537175375] iani iani 250516_132900
a = z play: { SinOsc.ar(500) * 0.1 };
//:[57436.693325] iani iani 250516_132903
z.scope;
//:[57444.980072917] iani iani 250516_132911
a = z play: { SinOsc.ar(500) * 0.1 };
//:[57450.090603375] iani iani 250516_132916
a = z play: { SinOsc.ar(400) * 0.1 };
//:[57465.336976458] iani iani 250516_132931
a.isPlaying
//:[57473.980789542] iani iani 250516_132940
a.release;
//:[57480.195079917] iani iani 250516_132946
a = z play: { SinOsc.ar(400) * 0.1 };
//:[57485.430779625] iani iani 250516_132951
a.release;
//:[57591.790167667] iani iani 250516_133138
a = { WhiteNoise.ar(0.1) * Env.fader };
//:[57596.344162208] iani iani 250516_133142
a = { WhiteNoise.ar(0.1) * Env.fader }.play;
//:[57602.373570167] iani iani 250516_133148
a.release;
//:[57619.702256917] iani iani 250516_133206
a = { WhiteNoise.ar(0.1) * Env.fader }.play;
//:[57620.917773583] iani iani 250516_133207
a release: 7;
//:[57655.479268792] iani iani 250516_133241
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[57659.333080917] iani iani 250516_133245
z = Diffuser(\iani);
//:[57661.200402958] iani iani 250516_133247
a = z play: { SinOsc.ar(400) * 0.1 };
//:[57667.263784583] iani iani 250516_133253
a.isPlaying
//:[57668.93842925] iani iani 250516_133255
a.release;
//:[57812.886558042] iani iani 250516_133519
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[57839.373635167] iani iani 250516_133545
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[57849.798671458] iani iani 250516_133556
z = Diffuser(\iani);
//:[57851.354795375] iani iani 250516_133557
a = z play: { SinOsc.ar(400) * 0.1 };
//:[57857.138635542] iani iani 250516_133603
a = z play: { SinOsc.ar(460) * 0.1 };
//:[57868.696028125] iani iani 250516_133615
a = z play: { SinOsc.ar(460) * 0.1 };
//:[57872.054537458] iani iani 250516_133618
a.release;
//:[57969.5707925] iani iani 250516_133756
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[58002.176042542] iani iani 250516_133828
a = z play: { SinOsc.ar(460) * 0.1 };
//:[58004.02287825] iani iani 250516_133830
z = Diffuser(\iani);
//:[58005.443795333] iani iani 250516_133831
a = z play: { SinOsc.ar(460) * 0.1 };
//:[58009.999623083] iani iani 250516_133836
a = z play: { SinOsc.ar(860) * 0.1 };
//:[58050.2570455] iani iani 250516_133916
a = z play: { SinOsc.ar(860) * 0.1 * Env.fader };
//:[58060.054441625] iani iani 250516_133926
a = z play: { SinOsc.ar(760) * 0.1 * Env.fader };
//:[58254.017912792] iani iani 250516_134240
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[58259.309263042] iani iani 250516_134245
z = Diffuser(\iani);
//:[58261.154835417] iani iani 250516_134247
a = z play: { SinOsc.ar(760) * 0.1 * Env.fader(3) };
//:[58301.90420975] iani iani 250516_134328
72
//:[58301.908412167] iani iani 250516_134328
72
//:[58354.646394833] iani iani 250516_134421
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[58355.228185792] iani iani 250516_134421
z = Diffuser(\iani);
//:[58356.916246583] iani iani 250516_134423
a = z play: { SinOsc.ar(760) * 0.1 * Env.fader(3) };
//:[58362.95338475] iani iani 250516_134429
a = z play: { SinOsc.ar(860) * 0.1 * Env.fader(3) };
//:[58418.295034917] iani iani 250516_134524
z = Diffuser(\iani);
//:[58419.591165625] iani iani 250516_134526
//:
a = z play: {
	var src;
	src = SinOsc.ar(860) * 0.1 * Env.fader(3)
	Out.ar(0, src);
};

//:[58425.431681792] iani iani 250516_134531
//:
a = z play: {
	var src;
	src = SinOsc.ar(860) * 0.1 * Env.fader(3);
	Out.ar(0, src);
};

//:[58431.325328375] iani iani 250516_134537
//:
a = z play: {
	var src;
	src = SinOsc.ar(560) * 0.1 * Env.fader(3);
	Out.ar(0, src);
};

//:[58471.060357417] iani iani 250516_134617
//:
a = z play: { | gate = 1 |
	var src;
	src = SinOsc.ar(560) * 0.1 * Env.fader(3);
	Out.ar(0, src);
};

//:[58481.963933458] iani iani 250516_134628
a.set(\gate, -1);
//:[58551.570871042] iani iani 250516_134738
//:
z = { | gate = 1 |
	var src;
	src = SinOsc.ar(560) * 0.1 *
	Env.adsr.kr(gate, doneAction: 2);
	Out.ar(0, src);
};

a.set(\gate, -1);

//:[58558.644886708] iani iani 250516_134745
//:
z = { | gate = 1 |
	var src;
	src = SinOsc.ar(560) * 0.1 *
	Env.adsr.kr(gate, doneAction: 2);
	Out.ar(0, src);
}.play;

a.set(\gate, -1);

//:[58567.607333625] iani iani 250516_134754
z.release;
//:[58579.108019833] iani iani 250516_134805
//:
z = { | gate = 1 |
	var src;
	src = SinOsc.ar(560) * 0.1 *
	Env.adsr.kr(gate, doneAction: 2);
	Out.ar(0, src);
}.play;

//:[58581.52362025] iani iani 250516_134808
z.set(\gate, 0);
//:[58585.691452792] iani iani 250516_134812
z.set(\gate, 0);
//:[60475.93916925] iani iani 250516_141942
//:
{
	PanAz.ar(8, WhiteNoise.ar(0.1),
		LFSaw.kr(5.reciprocal)
	)
}.play

a = { WhiteNoise.ar(0.1) * Env.fader }.play;
a release: 7;


//:[60483.570062042] iani iani 250516_141950
//:
{
	PanAz.ar(8, WhiteNoise.ar(0.1),
		LFSaw.kr(5.reciprocal)
	)
}.play

//:[60491.400342667] iani iani 250516_141957
//:
{
	PanAz.ar(8, WhiteNoise.ar(0.1),
		LFSaw.kr(5.reciprocal)
	)
}.play

//:[60495.944983417] iani iani 250516_142002
//:
{
	PanAz.ar(8, WhiteNoise.ar(0.1),
		LFSaw.kr(5.reciprocal)
	)
}.play

//:[60523.006028583] iani iani 250516_142029
Server.default.options.sampleRate = 48000;
//:[60524.275518417] iani iani 250516_142030
Server.default.options.numOutputBusChannels = 8;
//:[60531.01622625] iani iani 250516_142037
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[60533.813664875] iani iani 250516_142040
//:
{
	PanAz.ar(8, WhiteNoise.ar(0.1),
		LFSaw.kr(5.reciprocal)
	)
}.play

//:[61063.397429875] iani iani 250516_142929
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[61068.363635375] iani iani 250516_142934
//:
{
	PanAz.ar(8, WhiteNoise.ar(0.1),
		LFSaw.kr(5.reciprocal)
	)
}.play

//:[61118.183194667] iani iani 250516_143024
//:
{
	PanAz.ar(8, WhiteNoise.ar(0.1),
		LFSaw.kr(15.reciprocal)
	)
}.play

//:[61369.474863125] iani iani 250516_143435
//:
{
	PanAz.ar(8, WhiteNoise.ar(0.1),
		LFSaw.kr(15.reciprocal)
	)
}.play

//:[62977.744972042] iani iani 250516_150124
Node.addActions;
//:[63031.892633917] iani iani 250516_150218
72
//:[63031.895725583] iani iani 250516_150218
72
//:[63217.264101667] iani iani 250516_150523
Server.default.scope(numChannels: 8)
//:[65076.792519667] iani iani 250516_153623
\x ndef: { WhiteNoise.ar(0.1) };
//:[65082.796022375] iani iani 250516_153629
\x.play;
//:[65084.92816475] iani iani 250516_153631
\x.play;
//:[65092.898816208] iani iani 250516_153639
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[65096.595743083] iani iani 250516_153643
\x ndef: { WhiteNoise.ar(0.1) };
//:[65098.178734375] iani iani 250516_153644
\x.play;
//:[65105.420178208] iani iani 250516_153651
\x.stop;
//:[65125.836758333] iani iani 250516_153712
\x ndef: { Out.ar(0, WhiteNoise.ar(0.1)) };
//:[65129.19649375] iani iani 250516_153715
\x.play;
//:[65131.01546] iani iani 250516_153717
\x.stop;
//:[68926.598400875] iani iani 250516_164033
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[68958.965405042] iani iani 250516_164105
72
//:[68958.968751] iani iani 250516_164105
72
//:[69022.991096167] iani iani 250516_164209
Server.default.plotTree
//:[69053.112484208] iani iani 250516_164239
z = Diffuser(\iani);
//:[69087.680087625] iani iani 250516_164314
z.start;
//:[69129.259901375] iani iani 250516_164355
\a play: { SinOsc.ar(400, 0, 0.1) };
//:[69154.282439708] iani iani 250516_164420
z.scope;
//:[69213.688820333] iani iani 250516_164520
z.pos = 0.1;
//:[69229.297469792] iani iani 250516_164535
z.pos = 0.5;
//:[69253.82992375] iani iani 250516_164600
z.mainsynth;
//:[69265.007197458] iani iani 250516_164611
z.mainsynth.set(\level, 0);
//:[69342.648165667] iani iani 250516_164729
Server.default.plotTree
//:[69470.784020625] iani iani 250516_164937
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[69485.797757458] iani iani 250516_164952
z = Diffuser(\iani);
//:[69518.790867125] iani iani 250516_165025
z play: { ReplaceOut.ar(0, WhiteNoise.ar(0.01)) };
//:[69524.849706083] iani iani 250516_165031
z play: { ReplaceOut.ar(0, WhiteNoise.ar(0.1)) };
//:[69538.462231708] iani iani 250516_165044
z play: { Out.ar(0, WhiteNoise.ar(0.1)) };
//:[69546.510629042] iani iani 250516_165053
z play: { Out.ar(0, WhiteNoise.ar(0.1)) };
//:[69549.628342833] iani iani 250516_165056
z play: { Out.ar(0, WhiteNoise.ar(0.1)) };
//:[69568.835557958] iani iani 250516_165115
{ Out.ar(0, WhiteNoise.ar(0.1)) }.play;
//:[69583.713715083] iani iani 250516_165130
z play: { Out.ar(0, WhiteNoise.ar(0.01)) };
//:[69591.060575792] iani iani 250516_165137
z play: { ReplaceOut.ar(0, WhiteNoise.ar(0.01)) };
//:[69596.669432875] iani iani 250516_165143
\a play: { SinOsc.ar(400, 0, 0.1) };
//:[69598.427714458] iani iani 250516_165144
\a play: { SinOsc.ar(400, 0, 0.1) };
//:[69600.769192708] iani iani 250516_165147
\a play: { SinOsc.ar(400, 0, 0.1) };
//:[69641.39070775] iani iani 250516_165227
//:
z play: {
	ReplaceOut.ar(0, In.ar(0))
};

//:[69648.264328542] iani iani 250516_165234
//:
z play: {
	ReplaceOut.ar(1, In.ar(0))
};

//:[69678.636070208] iani iani 250516_165305
\a play: { PinkNoise.ar(0.1) };
//:[69684.597167333] iani iani 250516_165311
\a play: { PinkNoise.ar(0.01) };
//:[69688.502697833] iani iani 250516_165315
	ReplaceOut.ar(1, In.ar(0))
//:[69695.181594875] iani iani 250516_165321
z.scope;
//:[69762.930758833] iani iani 250516_165429
//:
z play: {
	ReplaceOut.ar(1, PanAz.ar(8, In.ar(0)))
};

//:[69776.47498925] iani iani 250516_165442
\a play: { PinkNoise.ar(0.1) };
//:[69822.466361875] iani iani 250516_165528
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(1, PanAz.ar(8, In.ar(0), pos, level, width))
};

//:[69825.464266333] iani iani 250516_165531
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(1, PanAz.ar(8, In.ar(0), pos, level, width))
};

//:[69834.411101875] iani iani 250516_165540
\a play: { PinkNoise.ar(0.1) };
//:[69836.769490542] iani iani 250516_165543
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(1, PanAz.ar(8, In.ar(0), pos, level, width))
};

//:[69847.009897] iani iani 250516_165553
\a play: { PinkNoise.ar(0.1) };
//:[69850.490594833] iani iani 250516_165556
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(1, PanAz.ar(8, In.ar(0), pos, level, width))
};

//:[69863.907177792] iani iani 250516_165610
z.mainsynth.set(width, 1);
//:[69869.667289667] iani iani 250516_165616
z.mainsynth.set(\width, 1);
//:[69884.470084625] iani iani 250516_165630
z.mainsynth.set(\pos, 1);
//:[69892.51949575] iani iani 250516_165639
z.mainsynth.set(\pos, 0.1);
//:[69900.199946292] iani iani 250516_165646
z.mainsynth.set(\pos, 0.2);
//:[69903.759028167] iani iani 250516_165650
z.mainsynth.set(\pos, 0.2);
//:[69909.563811792] iani iani 250516_165656
z.mainsynth.set(\pos, 0.3);
//:[69939.937609833] iani iani 250516_165726
Server.default.plotTree
//:[69977.889595583] iani iani 250516_165804
z.mainsynth.set(\level, 0.1);
//:[69985.02235775] iani iani 250516_165811
z.mainsynth.set(\level, 0.5);
//:[70002.10938675] iani iani 250516_165828
72
//:[70002.11250775] iani iani 250516_165828
72
//:[70104.561328958] iani iani 250516_170011
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(1, PanAz.ar(8, In.ar(0), pos, level, width))
};
z.mainsynth.set(\width, 1);
z.mainsynth.set(\pos, 0.3);
z.mainsynth.set(\level, 0.5);

//:[70114.356262792] iani iani 250516_170020
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(1, PanAz.ar(8, In.ar(0), pos, level, width))
};

//:[70123.79377775] iani iani 250516_170030
z = Diffuser(\iani);
//:[70125.955647875] iani iani 250516_170032
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(1, PanAz.ar(8, In.ar(0), pos, level, width))
};

//:[70135.383303625] iani iani 250516_170041
\a play: { PinkNoise.ar(0.1) };
//:[70154.087767208] iani iani 250516_170100
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(0, PanAz.ar(8, In.ar(0), pos, level, width))
};

//:[70174.156475917] iani iani 250516_170120
z.scope;
//:[70181.016991875] iani iani 250516_170127
z.mainsynth.set(\pos, 0.3);
//:[70196.142419167] iani iani 250516_170142
z.mainsynth.set(\pos, 0.5);
//:[70242.13536] iani iani 250516_170228
z.pos = 0;
//:[70248.334523958] iani iani 250516_170234
z.pos = 0.1;
//:[70364.887367375] iani iani 250516_170431
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[70537.696066792] iani iani 250516_170724
\a play: { PinkNoise.ar(0.1) };
//:[70539.515626458] iani iani 250516_170726
z = Diffuser(\iani);
//:[70552.077411167] iani iani 250516_170738
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(0, PanAz.ar(8, In.ar(0), pos, level, width))
};

//:[70554.752895042] iani iani 250516_170741
z.mainsynth.set(\width, 1);
//:[70572.011639375] iani iani 250516_170758
z.scope;
//:[70582.256392042] iani iani 250516_170808
z.mainsynth.set(\width, 1);
//:[70583.729624458] iani iani 250516_170810
z.mainsynth.set(\pos, 0.3);
//:[70586.333925167] iani iani 250516_170812
z.mainsynth.set(\pos, 0.5);
//:[70587.961023458] iani iani 250516_170814
z.mainsynth.set(\level, 0.5);
//:[70591.452175792] iani iani 250516_170817
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(0, PanAz.ar(8, In.ar(0), pos, level, width))
};

//:[70594.536060417] iani iani 250516_170821
z.mainsynth.set(\pos, 0.3);
//:[70630.388500583] iani iani 250516_170856
z.mainsynth.set(\pos, 0.3);
//:[70635.257121542] iani iani 250516_170901
z.pos = 0;
//:[70637.294012375] iani iani 250516_170903
z.pos = 0.1;
//:[70780.90727775] iani iani 250516_171127
\a play: { PinkNoise.ar(0.1) };
//:[70783.479390833] iani iani 250516_171129
z = Diffuser(\iani);
//:[70813.519645667] iani iani 250516_171200
\a play: { PinkNoise.ar(0.1) };
//:[70815.783145833] iani iani 250516_171202
z = Diffuser(\iani);
//:[70819.553500208] iani iani 250516_171206
z.start;
//:[70856.751979667] iani iani 250516_171243
z.stop;
//:[71026.837685917] iani iani 250516_171533
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[71030.554867083] iani iani 250516_171537
\a play: { PinkNoise.ar(0.1) };
//:[71033.034364792] iani iani 250516_171539
z = Diffuser(\iani);
//:[71034.825139] iani iani 250516_171541
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(0, PanAz.ar(8, In.ar(0), pos, level, width))
};

//:[71051.165855375] iani iani 250516_171557
\a play: { PinkNoise.ar(0.1) };
//:[71053.288489167] iani iani 250516_171559
z = Diffuser(\iani);
//:[71055.702430292] iani iani 250516_171602
z.start;
//:[71092.519239042] iani iani 250516_171639
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[71109.49075225] iani iani 250516_171655
z = Diffuser(\iani);
//:[71112.070128333] iani iani 250516_171658
\a play: { PinkNoise.ar(0.1) };
//:[71114.081151] iani iani 250516_171700
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(0, PanAz.ar(8, In.ar(0), pos, level, width))
};
//;
z.start;
z.stop;

//:[71134.110371292] iani iani 250516_171720
\a play: { PinkNoise.ar(0.1) };
//:[71137.142428375] iani iani 250516_171723
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(0, PanAz.ar(8, In.ar(0), pos, level, width))
};
//;
z.start;
z.stop;

//:[71143.459062208] iani iani 250516_171729
z.stop;
//:[71176.317339375] iani iani 250516_171802
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[71178.061792458] iani iani 250516_171804
\a play: { PinkNoise.ar(0.1) };
//:[71180.544839708] iani iani 250516_171807
z = Diffuser(\iani);
//:[71183.12026925] iani iani 250516_171809
z.start;
//:[71203.234135667] iani iani 250516_171829
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(0, PanAz.ar(8, In.ar(0), pos, level, width))
};
//;
z.start;
z.stop;

//:[71204.975634042] iani iani 250516_171831
\a play: { PinkNoise.ar(0.1) };
//:[71220.686669125] iani iani 250516_171847
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[71222.910083333] iani iani 250516_171849
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(0, PanAz.ar(8, In.ar(0), pos, level, width))
};
//;
z.start;
z.stop;

//:[71231.691517083] iani iani 250516_171858
z = Diffuser(\iani);
//:[71235.950915875] iani iani 250516_171902
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(0, PanAz.ar(8, In.ar(0), pos, level, width))
};
//;
z.start;
z.stop;

//:[71239.851643417] iani iani 250516_171906
\a play: { PinkNoise.ar(0.1) };
//:[71260.371518583] iani iani 250516_171926
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[71262.2842285] iani iani 250516_171928
z = Diffuser(\iani);
//:[71268.452962917] iani iani 250516_171934
\a play: { PinkNoise.ar(0.1) };
//:[71271.437335] iani iani 250516_171937
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(0, PanAz.ar(8, In.ar(0), pos, level, width))
};
//;
z.start;
z.stop;

//:[71281.5806145] iani iani 250516_171948
z.scope;
//:[71291.871831083] iani iani 250516_171958
\a play: { PinkNoise.ar(0.1) };
//:[71296.1331895] iani iani 250516_172002
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(0, PanAz.ar(8, In.ar(0), pos, level, width))
};
//;
z.start;
z.stop;

//:[71354.169878708] iani iani 250516_172100
z.tree;
//:[71359.402246667] iani iani 250516_172105
z = Diffuser(\iani);
//:[71361.726737542] iani iani 250516_172108
z.tree;
//:[71415.553417] iani iani 250516_172202
\a play: { PinkNoise.ar(0.1) };
//:[71420.319994208] iani iani 250516_172206
//:
{ | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(0, PanAz.ar(8, In.ar(0), pos, level, width))
}.play(addAction: \addAfter)


//:[71424.790999833] iani iani 250516_172211
\a play: { PinkNoise.ar(0.1) };
//:[71426.775024833] iani iani 250516_172213
//:
{ | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(0, PanAz.ar(8, In.ar(0), pos, level, width))
}.play(addAction: \addAfter)


//:[71446.328178917] iani iani 250516_172232
\a play: { PinkNoise.ar(0.1) };
//:[71450.892360958] iani iani 250516_172237
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(0, PanAz.ar(8, In.ar(0), pos, level, width))
};

//:[71498.720438333] iani iani 250516_172325
\a play: { PinkNoise.ar(0.1) };
//:[71506.299494125] iani iani 250516_172332
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(0, PanAz.ar(8, In.ar(0), pos, level, width))
};

//:[71814.748743708] iani iani 250516_172841
8.reciprocal;
//:[71946.656408917] iani iani 250516_173053
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[72008.993962958] iani iani 250516_173155
\a play: { SinOsc.ar(400 * [1, 1.2], 0, 0.1) };
//:[72053.832147917] iani iani 250516_173240
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[72056.268416208] iani iani 250516_173242
z = Diffuser(\iani);
//:[72066.041590375] iani iani 250516_173252
\a play: { SinOsc.ar(400 * [1, 1.2], 0, 0.1) };
//:[72068.036199958] iani iani 250516_173254
z.start;
//:[72078.703677833] iani iani 250516_173305
z.scope;
//:[72101.795204083] iani iani 250516_173328
z.pos = 0.2;
//:[74901.880432] iani iani 250516_182008
ShowTyping();
//:[78153.141879917] iani iani 250516_191419
z = Diffuser(\iani);
//:[78161.943870875] iani iani 250516_191428
z.start;
//:[78173.489106458] iani iani 250516_191439
z.scope;
//:[78229.739793] iani iani 250516_191536
\a play: { PinkNoise.ar(0.1) };
//:[78236.161753208] iani iani 250516_191542
z.start;
//:[78259.259912208] iani iani 250516_191605
z.p = { | out = 1 | Out.kr(out, LFSaw.kr(5.reciprocal)) };
//:[78609.308655917] iani iani 250516_192155
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[79753.086248875] iani iani 250516_194059
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[79886.9617635] iani iani 250516_194313
z = Diffuser(\iani);
//:[79892.8773815] iani iani 250516_194319
z = Diffuser(\iani);
//:[79900.266600667] iani iani 250516_194326
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(0, PanAz.ar(8, In.ar(0), pos, level, width))
};
//;
z.start;
z.stop;
z.p =

//:[79908.334659125] iani iani 250516_194334
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(0, PanAz.ar(8, In.ar(0), pos, level, width))
};
//;
z.start;
z.stop;
z.p =

//:[79914.813943042] iani iani 250516_194341
//:
z play: { | pos = 0, level = 1, width = 2 |
	ReplaceOut.ar(0, PanAz.ar(8, In.ar(0), pos, level, width))
};
//;
z.start;
z.stop;
z.p =

//:[79922.704161875] iani iani 250516_194349
z.start;
//:[79926.304182792] iani iani 250516_194352
z.scope;
//:[80023.362369083] iani iani 250516_194529
z.stop;
//:[80162.591657583] iani iani 250516_194749
"Kanoyme reboot kai restart to kommati apo tin archi tora"
//:[80169.94974675] iani iani 250516_194756
"Den akoume klik giorgo"
//:[80171.153784708] iani iani 250516_194757
"Den akoume klik giorgo"
//:[80171.754345917] iani iani 250516_194758
"Den akoume klik giorgo"
//:[80189.035611667] iani iani 250516_194815
"Proteino reboot kai restart oloi apo tin archi"
//:[80200.639380292] iani iani 250516_194827
"OK???????""
//:[80204.543243708] iani iani 250516_194831
"OK???????"
//:[80226.4900305] iani iani 250516_194852
"Perimenete emena kano reboot tora"
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
//:[82307.331800583] iani iani 250516_202333
(1..17) collect: { | i | ("mbus"++i).asSymbol };
//:[87094.905399292] iani iani 250516_214321
(1..17) collect: { | i | ("mbus"++i).asSymbol };
