/* 17 Feb 2021 14:33
Comment to https://scsynth.org/t/cybernetic-music-with-supercollider/3184/2
by PitchTrebler.
    ...
    sine1 = SinOsc.ar(freq: \sin_freq.kr(440.0) * (1 + (\fm_amount.kr(0) * sine2)) * in);
    ...

    I don’t really know why I’m using the “*” operation with the feedback loop, and not the “+” operation (which was my first guess).

I tried both, and they create wildly different sounds! In fact, any or all of the “*” in that line can be changed to “+” to get very different sounds. As you have it, the frequency will always modulate between positive and negative values (since in is bipolar), which appears to create a more chaotic sound. Using addition instead of multiplication will result in more of a classic FM sound, as this is the standard technique for FM synthesis to add the modulator to the frequency.

Even in the simplest FM synth with 1 modulator and 1 carrier, using multiplication instead of addition creates a much different sound:
*/
(
Ndef(\freqadd, {// frequency + modulator (classic FM sound)
    var freq = MouseY.kr(20, 4000, 1);
    var mratio = MouseX.kr(1/8, 8, 1);
    var mod = SinOsc.ar(freq * mratio) * freq * mratio * \index.kr(5);
    var car = SinOsc.ar(freq + mod);
    car = LeakDC.ar(car);
    car = Pan2.ar(car, \pan.kr(0), \amp.kr(0.3));
}).play;
)

(
Ndef(\freqmul, {// frequency * modulator (more chaotic sounding)
    var freq = MouseY.kr(20, 4000, 1);
    var mratio = MouseX.kr(1/8, 8, 1);
    var mod = SinOsc.ar(freq * mratio) * freq * mratio * \index.kr(5);
    var car = SinOsc.ar(freq * mod);
    car = LeakDC.ar(car);
    car = Pan2.ar(car, \pan.kr(0), \amp.kr(0.3));
}).play;
)

PitchTrebler
7d

…or use both addition and multiplication, and cross-feedback them into each other (thanks, weego, for the cross-feedback idea :slight_smile:):

(
Ndef(\crossfeedback, {
    var freq = MouseY.kr(20, 4000, 1);
    var mratio = MouseX.kr(1/8, 8, 1);
    var in = LocalIn.ar(2);
    var mod = SinOsc.ar(freq * mratio) * freq * mratio * \index.kr(5);
    var car = SinOsc.ar([freq * mod * in[1], freq + mod + in[0]]);
    LocalOut.ar(car * \fb.kr([10, 5]));
    car = LeakDC.ar(Mix(car));
    car = Pan2.ar(car, \pan.kr(0), \amp.kr(0.3));
}).play;
)
