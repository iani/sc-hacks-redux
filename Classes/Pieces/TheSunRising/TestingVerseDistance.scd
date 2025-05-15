//: 木 15  5 2025 10:57
var old, new;
old = ~verses[0];
new = ~verses[0];
old editDistance: new;
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
