// Fri  6 Dec 2024 00:49
// Create numbered symbols and evaluate function with them as arguments
// freq -> \freq1, \freq2, \freq3 ... \freqn
// This useful for creating named control inputs in a play function that creates
// output proxies by iterating over a collection.
//: Example:
/*
//:
a = {
	((15..18) * 4).midicps.symcollect({ | f, i, freq |
		SinOsc.ar(freq.kr(f).lag(1), 0, 0.1)
	}).mix;
}.play;
//:
a.set(\freq1, 4000);
*/

+ Collection {
	symcollect { | function /* ... ids  */ |
		var ids;
		ids = (function.def.argNames ? [])[2..];
		^this collect: { | element, i |
			var symbols; // TODO: rewrite the next 4 lines ...
			symbols = ids collect: { | id |
				(id ++ (i + 1)).asSymbol
			};
			function.(element, i, *symbols)
		}
	}
}