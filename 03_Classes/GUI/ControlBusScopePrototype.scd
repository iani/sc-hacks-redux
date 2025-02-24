//: execute the following two blocks in succession:

f = Buffer.alloc(s,1024,2);
b = Bus.audio(s,1);
​
w = Window.new.front;
//:
w.onClose = { // free everything when finished
    c.stop; a.free; d.free; f.free; b.free;
    "SynthDefs, busses and buffers have all been freed.".postln;
};
//:
c = ScopeView(w.view,w.view.bounds.insetAll(10,10,10,10));
c.bufnum = f.bufnum;
c.server = s; // Important: one must assign the ScopeView to a server

​//:

// listening to the bus, using ScopeOut2 to write it to the buffer
a = SynthDef("monoscope", { arg bus, bufnum;
    var z;
    z = In.ar(bus, 2);
​
    ScopeOut2.ar(z, bufnum);
    Out.ar(0, z);
}).play(
    target: RootNode(s),
    args: [\bus, b.index, \bufnum, f.bufnum],
    addAction: \addToTail // make sure it goes after what you are scoping
);
//:
​
//:
// making noise onto the buffer
d = SynthDef("noise", { arg bus;
    var z;
    z = LFSaw.ar(SinOsc.kr(0.1).range(300,1000),[0,1]*pi) * 0.1;
    Out.ar(bus, z);
}).play(
    s,
    [\bus,b.index]
);
//:
c.start; // Tell the ScopeView to start
CmdPeriod.doOnce({w.close});
