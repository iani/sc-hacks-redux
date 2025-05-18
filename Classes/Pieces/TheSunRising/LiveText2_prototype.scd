// 日 18  5 2025 11:33

//============================================================
// Basic principle: Access an array with char.ascii as index.
// This is faster, and simpler to construct.
//
// Extra idea: Make a method to construct pseq's directly

//Upper case chars
(65..(65+25)) collect: _.asAscii;
//:all ascii chars
(0..127) collect: _.asAscii;

//: ----------- 2. Constructing the array
//:Trying to work with array directly
//:Initialize all ascii chars with 0
a = (0..127) collect: 0;
//:put integer values for a-z
(($a.ascii)..($z.ascii)) do: { | n | a[n] = n - 96 };
(($A.ascii)..($Z.ascii)) do: { | n | a[n] = n - 64 };
//: Look at the finished array:
a;
//: Test some characters in the array:
a[$a.ascii];
a[$l.ascii];
a[$z.ascii];
a[$,.ascii];
a[$ .ascii];
a[$A.ascii];
a[$Z.ascii];

//:----------- 3. Testing the array with string input
"abc".ascii collect: { | c | a[c] };
"Busy old fool".ascii  collect: { | c | a[c] };

//:----------- 4. Constructing a pseq
x = "Busy old fool".ascii.collect({ | c | a[c] }).pseq(1);

//:----------- 5. Adapting the pseq for ue in sines1

b = ("Busy old fool".ascii.collect({ | c | a[c] }).pseq(1) + 60);

//:too low:
\x play: (dur: 0.1, freq: x);
//:low, but audible
\x play: (dur: 0.1, freq: b);

//============================================================
//: The float array is a little more complicated:
//: We construct it by concatenation in 4 parts.
w = (0.1, 0.2..0.9);
x = (0.1, 0.21..0.98);
y = [0.19];
z = (0.220, 0.321..0.827);

w.size;
x.size;
y.size;
z.size;
[w, x, y, z].flat.size;
//: Reconstruct and enter into the int and float arrays:
//: part 1: ~int
~int = (0..127) collect: 0;
//:put integer values for a-z
(($a.ascii)..($z.ascii)) do: { | n | ~int[n] = n - 96 };
(($A.ascii)..($Z.ascii)) do: { | n | ~int[n] = n - 64 };
//: part 2: ~float
~w = (0.1, 0.2..0.9);
~x = (0.1, 0.21..0.98);
~y = [0.19];
~z = (0.220, 0.321..0.827);
~asciiFloats = [~w, ~x, ~y, ~z].flat;
~asciiFloats.size;
~float = 0.0 ! 127;
(($a.ascii)..($z.ascii)) do: { | n, i | ~float[n] = ~asciiFloats[i] };
(($A.ascii)..($Z.ascii)) do: { | n, i | ~float[n] = ~asciiFloats[i] };
//:Tests:
(($a.ascii)..($z.ascii)) do: { | char | [char.asAscii, char, ~float[char]].postln }
(($A.ascii)..($Z.ascii)) do: { | char | [char.asAscii, char, ~float[char]].postln }
//:
//: -------------------- CANCELLED:--------------------
//: Creating it iteratively in one go is too complicated:
//: The basis is simple
(($a.ascii)..($z.ascii)) collect: { | n | (n - 96) % 10 / 10 };
//: but we need to increment by 1/100 - irregularly?
x = (($a.ascii)..($z.ascii)) - 96; // generation base
y = x collect:q { | n | n % 10 / 10 };
z = y collect: { | y, n |
	y + (n - 10 max: 0 / 100).clip(0.0, 0.9)
};
//:One more go with clump
x = ((($a.ascii)..($z.ascii)) - 96 / 10 mod: 1).clump(10);
y = x[1][..7];
z = y + ((0..9) / 100); // here we need to correct last float
z[9] = 0.19;
q = y + (0.019,0.020..0.027)
q[0] = 0.19;
//:Redo: One more go with clump
x = ((($a.ascii)..($z.ascii)) - 96 / 10 mod: 1).clump(9);
x[0];
x[1]
(0.1, 0.2..0.9)
(0.1, 0.21..0.98);
(0.220, 0.321..0.827)

y = x[1][..7];
z = y + ((0..9) / 100); // here we need to correct last float
z[9] = 0.19;
q = y + (0.019,0.020..0.027)
q[0] = 0.19;
//:





x[2];
q = x[2] - 0.1 / 100;

q / 100 + (q / 10)

q + ((120..126) / 1000)

y.size;
z.size

((0..9) / 100)
z = x[2];
z + ((0..(z.size-1)) / 1000)

z.size;
(0..9) / 100;

y
//
//:
x collect: { | n | n - 10 max: 0 / 100 }
//:
z = y collect: { | n, i | n + (i / 1000) }
//:
(n - 96) % / 20 + (n - 97 / 10).round(0.01)
};
//:
x =
(($a.ascii)..($z.ascii)) collect: { | n |
n - 96 / 10 +
};