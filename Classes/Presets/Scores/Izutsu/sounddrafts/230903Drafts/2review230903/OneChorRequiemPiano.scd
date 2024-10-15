//:See realization in:
// https://www.youtube.com/watch?v=aUGN7PIJmts
// has error because of midi - but study code for chords!
//:has error because of midi
(
s.waitForBoot({
	var midiout;
	var no_of_bins;
	var bins = Set[];
	var table;
	var lut;
	var period = 127;
	var phase = 0;
	var rangemin = 20;
	var rangemax = 80;
	var strum = 0.0025.neg;
	var delay_between_chords = 0.25;
	var direction = 1;
	var stopit = false;
	var idx = 0;
	var ampmod = 1.0;

	CmdPeriod.doOnce{
		127.do({
			|note|
			midiout.noteOff(1, note);
		});
	};
	// if (MIDIClient.initialized.not) { MIDIClient.init; };
	// midiout = MIDIOut.new(0);
	no_of_bins = 24;
	table = {
		| period, phase, rangemin, rangemax |
		127.collect({
			|time|
			sin(2pi*time/period + phase).linlin(-1, 1, rangemin, rangemax);
		});
	};

	bins = Set[];
	while ({bins.size < no_of_bins}) {
		bins = bins.add(20.rrand(100));
	};
	bins = bins.asList.sort;
	idx = 0;

	while({stopit.not}) {
		var wrappedidx = idx.mod(127);
		idx.postln;
		if (idx.mod(127) == 0) {
			period = period / 2;
			lut = table.(period, phase, rangemin, rangemax);
			direction = direction.neg;
			//lut.debug("new lut");
			direction.debug("new direction");
		};
		if (idx.mod(127*2*2) == 0) {
			strum = strum + 0.0025;
			delay_between_chords = delay_between_chords * 0.9;
			strum.debug("new strum");
			bins = bins.scramble;
			bins.debug("new order");
		};
		if ((idx > 0) && (idx.mod(890) == 0)) {
			strum = 0.01;
			bins = bins.scramble;
		};
		if (idx > 890) {
			direction = direction * [1,-1].choose; // one last chaotic period
			period = period * 2.6329.rrand(1.0/2.6329);
			ampmod = ampmod * 0.97;
			strum = strum * 1.2;
			delay_between_chords = delay_between_chords * 0.9;
		};
		if (idx == 915) {
			stopit = true;
			"preparing to stop....".postln;
		};
		bins.do({
			|bin|
			var finalidx = (wrappedidx + (bin*direction)).wrap(0, 126);
			midiout.noteOn(1, bin, ampmod * lut[finalidx]);
			strum.wait;
		});
		delay_between_chords.wait;
		bins.do({
			|bin|
			midiout.noteOff(1, bin);
		});

		idx = idx+1;
	};
	midiout.control(1, 64, 127);
	127.do({
		|note|
		midiout.noteOff(1, note);
	});

	10.wait;
	midiout.control(1, 64, 0);
	"The End.".postln;
});
)