// Exporting 45 snippets on 230917_154836
//Source: [ 230915_200350, 230915_200352, 230915_200404, 230915_200416, 230915_200428, 230915_200440, 230915_200452, 230915_200504, 230915_200516, 230915_200528, 230915_200540, 230915_200552, 230915_200604, 230915_200616, 230915_200630, 230915_200643, 230915_200656, 230915_200708, 230915_200720, 230915_200732, 230915_200744, 230915_200756, 230915_200808, 230915_200821 ]
//:--[83289.122303917]
[ '/code', "//:GrainBuf setup + start
//buffer for demand ugens for frequency;
[12, 10, 9, 8, 4, 6, 8, 7, 10 * 16 / 15 / 2,  10 * 14 / 15 / 2] @> \\freqs;
// trigger every 2.9 seconds
{ Impulse.kr(2.9, pi) } @>.jun \\trig;
// use crickets buffer
\\crickets +>.jun \\buf;
// volume 0.1 (rather low)
0.1 @>.jun \\vol;
{
	var src, out, freqs, amps, ringtimes;
	src = GrainBuf0_.ar;
	freqs = (5..8) / 2 * 100 @@>.1 \\f;
	amps = 0.01.dup(4) @@>.1 \\a;
	ringtimes = 1.dup(4) @@>.1 \\r;
	// ringtimes
	out = DynKlank.ar(
		`[freqs, amps, ringtimes],
		src
	).pan;
	// out.postln;
} +> \\jun;
// LFNoise2 -> pos
{ LFNoise2.kr(1/10).range(0, 1) } @>.jun \\pos;
// LFNoise2 -> rate
{ LFNoise2.kr(1/10).range(0.8, 1.2) } @>.jun \\rate;
" ]
//:--[83295.335407]
[ '/code', "1200 @>.jun \\f1;  /// keep this" ]
//:--[83298.136213833]
[ '/code', "1000 @>.jun \\f2; /// keep this" ]
//:--[83300.800707833]
[ '/code', "1400 @>.jun \\f1; /// keep this" ]
//:--[83304.521982042]
[ '/code', "900 @>.jun \\f3;" ]
//:--[83307.560279875]
[ '/code', "800 @>.jun \\f4;" ]
//:--[83311.532838333]
[ '/code', "400 / 2 @>.jun \\f3; // keep this" ]
//:--[83314.686081083]
[ '/code', "600 / 2 @>.jun \\f4; // keep this" ]
//:--[83317.79329775]
[ '/code', "1000 @>.jun \\f2; /// keep this" ]
//:--[83318.998966625]
[ '/code', "1400 @>.jun \\f1; /// keep this" ]
//:--[83320.598514708]
[ '/code', "1200 @>.jun \\f1;  /// keep this" ]
//:--[83322.816847625]
[ '/code', "900 @>.jun \\f3;" ]
//:--[83326.336866167]
[ '/code', "1200 @>.jun \\f1;  /// keep this" ]
//:--[83327.638099458]
[ '/code', "1400 @>.jun \\f1; /// keep this" ]
//:--[83329.62389925]
[ '/code', "1000 @>.jun \\f2; /// keep this" ]
//:--[83331.000426958]
[ '/code', "900 @>.jun \\f3;" ]
//:--[83333.5411365]
[ '/code', "800 @>.jun \\f4;" ]
//:--[83335.769602583]
[ '/code', "400 / 2 @>.jun \\f3; // keep this" ]
//:--[83338.881944167]
[ '/code', "600 / 2 @>.jun \\f4; // keep this" ]
//:--[83341.42403]
[ '/code', "1200 @>.jun \\f1;  /// keep this" ]
//:--[83343.790149333]
[ '/code', "1000 @>.jun \\f2; /// keep this" ]
//:--[83346.247396167]
[ '/code', "400 / 2 @>.jun \\f3; // keep this" ]
//:--[83348.991232208]
[ '/code', "600 / 2 @>.jun \\f4; // keep this" ]
//:--[83363.924613708]
[ '/code', "1400 @>.jun \\f1; /// keep this" ]
//:--[83365.572607458]
[ '/code', "1000 @>.jun \\f2; /// keep this" ]
//:--[83367.016207292]
[ '/code', "900 @>.jun \\f3;" ]
//:--[83369.587058542]
[ '/code', "1200 @>.jun \\f1;  /// keep this" ]
//:--[83371.086202833]
[ '/code', "1400 @>.jun \\f1; /// keep this" ]
//:--[83372.757052083]
[ '/code', "1000 @>.jun \\f2; /// keep this" ]
//:--[83373.767479833]
[ '/code', "900 @>.jun \\f3;" ]
//:--[83375.361832292]
[ '/code', "800 @>.jun \\f4;" ]
//:--[83380.605739667]
[ '/code', "400 / 2 @>.jun \\f3; // keep this" ]
//:--[83382.23961475]
[ '/code', "600 / 2 @>.jun \\f4; // keep this" ]
//:--[83386.430076875]
[ '/code', "1200 @>.jun \\f1;  /// keep this" ]
//:--[83388.581485042]
[ '/code', "1000 @>.jun \\f2; /// keep this" ]
//:--[83393.219795708]
[ '/code', "//:------------- now introduce individual hand control
1200 @>.jun \\f1;  /// keep this
1000 @>.jun \\f2; /// keep this
400 / 2 @>.jun \\f3; // keep this
600 / 2 @>.jun \\f4; // keep this
// xyz control jun f3 amp LH ---- DO USE THIS YES YES YES
{ Xyz(7, 0.15, 0.05, 1.1, 0.005) * 0.5 } @>.jun \\a3;
// xyz control jun f3 amp LH DO USE THIS YES YES YES
{ Xyz(6, 0.15, 0.05, 1.1, 0.005) * 0.5 } @>.jun \\a4;
// xyz control ASAYO f1 amp LH ----- DO USE THIS YES YES YES
{ Xyz(5, 0.15, 0.05, 1.1, 0.005) * 1.5 } @>.jun \\a1;
// xyz control ASAYO f2 amp LH ------ DO USE THIS YES YES YES
{ Xyz(8, 0.15, 0.05, 1.1, 0.005) * 1.5 } @>.jun \\a2;
" ]
//:--[83469.387834833]
[ '/code', "1200 @>.jun \\f1;  /// keep this" ]
//:--[83470.641793667]
[ '/code', "1400 @>.jun \\f1; /// keep this" ]
//:--[83472.30771175]
[ '/code', "1000 @>.jun \\f2; /// keep this" ]
//:--[83475.811078708]
[ '/code', "1200 @>.jun \\f1;  /// keep this" ]
//:--[83487.221821667]
[ '/code', "1100 @>.jun \\f1;  /// keep this" ]
//:--[83499.7218965]
[ '/code', "1000 @>.jun \\f1;  /// keep this" ]
//:--[83504.583732625]
[ '/code', "1200 @>.jun \\f1;  /// keep this" ]
//:--[83508.37431175]
[ '/code', "1100 @>.jun \\f1;  /// keep this" ]
//:--[83514.671809708]
[ '/code', "1400 @>.jun \\f1; /// keep this" ]

//the end

