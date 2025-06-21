f = "~/audiofiles/*.wav".standardizePath.pathMatch;
b = Buffer.read(Server.default, f[10]);
b
//:playbuf
a = {
	PlayBuf.ar(1,
		b.bufnum,
		1,
		1,
		500000
	).dup * 0.1
}.play;
//:bufrd
{
	BufRd.ar(1, b, Phasor.ar(0, BufRateScale.kr(b), 0, BufFrames.kr(b)))
	.dup * 0.1
}.play;
//:bufrd - start anywhere, stop anywhere. No looping
{
	var thresh, phasor;
	thresh = BufFrames.kr(b) / 3;
	phasor = Phasor.ar(0, BufRateScale.kr(b), 30100, BufFrames.kr(b));
	FreeSelf.kr(phasor >= thresh);
	//	FreeSelf.kr();
	BufRd.ar(1, b, phasor)
	.dup * 0.1
}.play;
//:bufrd - start anywhere, stop anywhere - again. No looping.
{
	var thresh, phasor, from, to;
	from = 30100;
	to = from * 1.5;
	phasor = Phasor.ar(0, BufRateScale.kr(b), from, BufFrames.kr(b));
	FreeSelf.kr(phasor >= to);
	//	FreeSelf.kr();
	BufRd.ar(1, b, phasor)
	.dup * 0.1
}.play;
//:bufrd LOOPING: start anywhere,  loop to anywhere.
{
	var thresh, phasor, from, to;
	from = 30100;
	to = from * 1.5;
	phasor = Phasor.ar(0, BufRateScale.kr(b), from, to);
	BufRd.ar(1, b, phasor)
	.dup * 0.1
}.play;
//:bufrd LOOPING - SLOW RATE: start anywhere,  loop to anywhere.
{
	var thresh, phasor, from, to;
	from = 30100;
	to = from * 1.5;
	phasor = Phasor.ar(0, BufRateScale.kr(b) * 0.25, from, to);
	BufRd.ar(1, b, phasor)
	.dup * 0.1
}.play;
//:bufrd
{
	var thresh, phasor;
	thresh = BufFrames.kr(b) / 3;
	phasor = Phasor.ar(0, BufRateScale.kr(b), 0, BufFrames.kr(b));
	FreeSelf.kr(phasor >= thresh);
	//	FreeSelf.kr();
	BufRd.ar(1, b, phasor)
	.dup * 0.1
}.play;
//:

//:how to end the phasor???
b = Buffer.read(s, Platform.resourceDir +/+ "sounds/a11wlk01.wav");
//:
{ BufRd.ar(1, b.bufnum, Phasor.ar(0, BufRateScale.kr(b.bufnum), 0, BufFrames.kr(b.bufnum))) }.play;
//:
a = {
	PlayBuf.ar(1,
		b.bufnum,
		1,
		Impulse.kr(5),
		LFNoise0.kr(5).range(0, 500000)
	).dup * 0.1
}.play;
//:
a = {
	PlayBuf.ar(1,
		b.bufnum,
		1,
		1,
		690000,
		0
	).dup * 0.1
}.play;
//:
}.play;
//:
{ WhiteNoise.ar(0.001) }.play;