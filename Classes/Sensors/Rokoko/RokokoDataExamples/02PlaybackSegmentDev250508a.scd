//:Load, playback, test different reactions
~rokoko = \session1.rdata; // load some data in advance
//: First test: post the data as played
~rokoko.addPostData;
~rokoko.playSegment(0, 10);
~rokoko.removePostData;
//:post just the index as played
~rokoko.addPostIndex;
~rokoko.playSegment(0, 10);
~rokoko.removePostIndex;

~rokoko;

//:cloning (for multiple playbacks at once!)
~r2 = ~rokoko.copy;
~r2 === ~rokoko;
~r2.oscdata === ~r2.oscdata;
//: -------- Early stuff --------
a = \session1.rdata.oscdata;
a.parsedEntries.size;
d = a.parsedEntries[0..4];
t = d.flop[0];
u = t.differentiate[0] = 0;
//:
fork {
	u do: { | dt, i |
		dt.wait;
		// d[i][1].class.postln;
		d[i][1].interpret.class.postln;
	}
};

x = Stack();
x.top;
x.push(1000);
x.top;
//: composing data filters
a = FunctionList.new;
fork { loop { 0.7.wait; a.value.postln } };
a.addFunc({ 800.rand });
a.addFunc({ "another".scramble });

//: Passing filtered data down the function list
a = FunctionList.new;
fork { loop { 0.7.wait; a.value(100).postln } };
a.addFunc({ | i | i + 10.rand });
a.addFunc({ | i | i + 2 });
//: building one's own function pipeline:
p = { | ... funcs |

}
