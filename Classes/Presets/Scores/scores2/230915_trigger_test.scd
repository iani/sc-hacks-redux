//code
// Exporting 2 snippets on 230917_154857
//Source: [ 230915_204002, 230915_204008, 230915_204020, 230915_204032, 230915_204044, 230915_204056, 230915_204108, 230915_204120, 230915_204132, 230915_204145, 230915_204157, 230915_204209, 230915_204221, 230915_204233, 230915_204245, 230915_204257, 230915_204309, 230915_204321, 230915_204333, 230915_204345, 230915_204357, 230915_204409, 230915_204421, 230915_204433 ]
//:--[1] trigger with Xyz. Asayo RH --- TESTED
// no sound!
{
	// WhiteNoise.ar (Decay.kr
		(Xyz(8, 0.15, 0.05, 0.1, 0.01) > 0.02).sendReply(\asayolon);
	// * 0.001
} +>.asayo \asayoron;
//:--[1]  trigger LH - sound removed. just trigger.
{
	(Xyz(5, 0.15, 0.05, 0.1, 0.01) > 0.02).sendReply(\asayoron);
	// SinOsc.ar(400, 0, Decay.kr(Xyz(5, 0.15, 0.05, 0.1, 0.01) > 0.02)) * 0.001
	//.sendReply(\\asayolon)
} +>.asayo \asayolon;
//:--[1] basic monitoring of triggers
\asayolon >>> {
	var prevt, thist;
	prevt =  (\left @ \asayo) ? 0;
	thist = Main.elapsedTime;
	if (thist - prevt > 1) {
		postln("dt asayo levt" + (thist - prevt) + "PLAYING");
		thist +>.asayo \left;
	}{
		postln("skipping thist" + thist + "prevt" + prevt + "diff" + (thist - prevt));
	}
	// postln("asayol: " + (\left @ \asayo));

	// Main.elapsedTime +>.asayo \left;
};
\asayoron >>> {
	postln("asayor: " + (\right @ \asayo));
	Main.elapsedTime +>.asayo \right;
	"asayo RIGHT on".postln; };
//the end

