// posted in sc forum on Feb 7 2024
(
var rampToTrig = { |phase|
	var history = Delay1.ar(phase);
	var delta = (phase - history);
	var sum = (phase + history);
	var trig = (delta / sum).abs > 0.5;
	Trig1.ar(trig, SampleDur.ir);
};

{
	var rate = 100;
	var measurePhase = (Phasor.ar(DC.ar(0), rate * SampleDur.ir) - SampleDur.ir).wrap(0, 1);
	var stepsPerMeasure = 8;

	var stepPhase = (measurePhase * stepsPerMeasure).wrap(0, 1);
	var stepTrigger = rampToTrig.(stepPhase);

	var numSize = \numSize.ar(8);
	var numHits = \numHits.ar(9);
	var offset = \offset.ar(0);

	var ratio = Latch.ar(numHits.floor / numSize.floor, stepTrigger);
	var ratioAbs = ratio.abs;
	var rampDirection = ratio.sign;
	var ratchetLevel = ratioAbs.ceil;
	var ratchets = 2 ** (ratchetLevel - 1);

	var stepPhaseRotated = ((measurePhase * stepsPerMeasure) - Latch.ar(offset.floor, stepTrigger) * rampDirection).wrap(0, numSize);
	var stepIndex = stepPhaseRotated.floor;

	var lastEvent = (stepIndex * ratioAbs).floor;
	var currentEvent = (lastEvent / ratioAbs).ceil;
	var nextEvent = ((lastEvent + ratchetLevel) / ratio.abs).ceil;

	var stepDuration = nextEvent - currentEvent;

	var eventPhase = (stepPhaseRotated - currentEvent / stepDuration * ratchets).wrap(0, 1);

	[measurePhase, eventPhase];
}.plot(0.011);
)
