// Exporting 18 snippets on 230917_154816
//Source: [ 230915_195422, 230915_195434, 230915_195446, 230915_195458, 230915_195510, 230915_195522, 230915_195534, 230915_195546, 230915_195558, 230915_195610, 230915_195622, 230915_195634, 230915_195647, 230915_195659, 230915_195711, 230915_195723, 230915_195735, 230915_195747, 230915_195759, 230915_195812, 230915_195824, 230915_195836 ]
//:--[82718.197709708]
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
//:--[82729.118588792]
[ '/code', "1200 @>.jun \\f1;  /// keep this" ]
//:--[82731.963128458]
[ '/code', "1000 @>.jun \\f2; /// keep this" ]
//:--[82734.918251875]
[ '/code', "1400 @>.jun \\f1; /// keep this" ]
//:--[82737.571051208]
[ '/code', "900 @>.jun \\f3;" ]
//:--[82740.46955125]
[ '/code', "800 @>.jun \\f4;" ]
//:--[82743.362101875]
[ '/code', "400 / 2 @>.jun \\f3; // keep this" ]
//:--[82745.501598292]
[ '/code', "600 / 2 @>.jun \\f4; // keep this" ]
//:--[82753.423049792]
[ '/code', "1200 @>.jun \\f1;  /// keep this" ]
//:--[82756.052509958]
[ '/code', "1000 @>.jun \\f2; /// keep this" ]
//:--[82758.548270542]
[ '/code', "400 / 2 @>.jun \\f3; // keep this" ]
//:--[82763.844825]
[ '/code', "//:------------- now introduce individual hand control
1200 @>.jun \\f1;  /// keep this
1000 @>.jun \\f2; /// keep this
400 / 2 @>.jun \\f3; // keep this
400 / 2 @>.jun \\f3; // keep this
// xyz control jun f3 amp LH ---- DO USE THIS YES YES YES
{ Xyz(7, 0.15, 0.05, 1.1, 0.005) * 0.1 } @>.jun \\a3;
// xyz control jun f3 amp LH DO USE THIS YES YES YES
{ Xyz(6, 0.15, 0.05, 1.1, 0.005) * 0.1 } @>.jun \\a4;
// xyz control ASAYO f1 amp LH ----- DO USE THIS YES YES YES
{ Xyz(5, 0.15, 0.05, 1.1, 0.005) * 0.1 } @>.jun \\a1;
// xyz control ASAYO f2 amp LH ------ DO USE THIS YES YES YES
{ Xyz(8, 0.15, 0.05, 1.1, 0.005) * 0.1 } @>.jun \\a2;
" ]
//:--[82793.727752125]
[ '/code', "{ Xyz(7, 0.15, 0.05, 1.1, 0.005) * 0.2 } @>.jun \\a3;" ]
//:--[82859.736463667]
[ '/code', "{ Xyz(5, 0.15, 0.05, 1.1, 0.005) * 1.3 } @>.jun \\a1;" ]
//:--[82862.325109583]
[ '/code', "{ Xyz(8, 0.15, 0.05, 1.1, 0.005) * 0.5 } @>.jun \\a2;" ]
//:--[82864.8514775]
[ '/code', "{ Xyz(6, 0.15, 0.05, 1.1, 0.005) * 0.5 } @>.jun \\a4;" ]
//:--[82866.220560875]
[ '/code', "{ Xyz(7, 0.15, 0.05, 1.1, 0.005) * 0.5 } @>.jun \\a3;" ]
//:--[82885.782347875]
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
{ Xyz(5, 0.15, 0.05, 1.1, 0.005) * 1.3 } @>.jun \\a1;
// xyz control ASAYO f2 amp LH ------ DO USE THIS YES YES YES
{ Xyz(8, 0.15, 0.05, 1.1, 0.005) * 0.5 } @>.jun \\a2;
" ]

//the end

