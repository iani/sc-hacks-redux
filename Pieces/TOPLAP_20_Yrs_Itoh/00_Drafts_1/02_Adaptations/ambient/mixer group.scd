s.plotTree;

(
~modsources = Group.new(s,\addToHead);
~carsources = Group.new(~modsources,\addAfter);
~effects = Group.new(~carsources,\addAfter);
~themixer = Group.new(s,\addToTail);



SynthDef("theMixer", { arg gain1 = 1,gain2 = 1,gain3 = 1,gain4 =1,gain=0.2;
    ReplaceOut.ar(
        0,
		Mix.ar([In.ar(7, 7)*gain, In.ar(35, 7)*gain2,In.ar(42,2)*gain3,In.ar(44,2)*gain4])*gain
    )
}).add;



e=Synth("theMixer", [\gain,　0.3],~themixer);
)

e.set(\gain,0.1);
e.set(\gain2,0.8);
e.set(\gain3,4.2);
e.set(\gain4,4.5);