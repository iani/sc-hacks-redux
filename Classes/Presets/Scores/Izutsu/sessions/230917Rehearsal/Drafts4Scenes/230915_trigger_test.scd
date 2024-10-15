//code
// Exporting 2 snippets on 230917_154857
//Source: [ 230915_204002, 230915_204008, 230915_204020, 230915_204032, 230915_204044, 230915_204056, 230915_204108, 230915_204120, 230915_204132, 230915_204145, 230915_204157, 230915_204209, 230915_204221, 230915_204233, 230915_204245, 230915_204257, 230915_204309, 230915_204321, 230915_204333, 230915_204345, 230915_204357, 230915_204409, 230915_204421, 230915_204433 ]
//:--[1] trigger with Xyz. Asayo R, L --- TESTED // silent version!
{
	(Xyz(8, 0.15, 0.05, 0.1, 0.01) > 0.02).sendReply(\asayolon);
} +>.asayo \asayoron;
{
	(Xyz(5, 0.15, 0.05, 0.1, 0.01) > 0.02).sendReply(\asayoron);
} +>.asayo \asayolon;
//:--[1] trigger with Xyz. Jun R, L // silent version!
{
	(Xyz(6, 0.15, 0.05, 0.1, 0.01) > 0.02).sendReply(\junlon);
} +>.jun \junron;
{
	(Xyz(7, 0.15, 0.05, 0.1, 0.01) > 0.02).sendReply(\junron);
} +>.jun \junlon;
//:--[1] basic monitoring of triggers - asayo
\asayolon >>> {
	var prevt, thist;
	prevt =  (\left @ \asayo) ? 0;
	thist = Main.elapsedTime;
	if (thist - prevt > 1) {
		postln("PLAYING dt asayo left" + (thist - prevt) + "PLAYING");
		thist +>.asayo \left;
	}{
		postln("SKIPPING thist" + "diff" + (thist - prevt) + thist + "prevt" + prevt );
	}
};

\asayoron >>> {
	var prevt, thist;
	prevt =  (\right @ \asayo) ? 0;
	thist = Main.elapsedTime;
	if (thist - prevt > 1) {
		postln("PLAYING dt asayo right" + (thist - prevt) + "PLAYING");
		thist +>.asayo \right;
	}{
		postln("SKIPPING thist" + "diff" + (thist - prevt) + thist + "prevt" + prevt );
	}
} //:--[1] monitoring of triggers - jun
\junlon >>> {
	var prevt, thist;
	prevt =  (\left @ \jun) ? 0;
	thist = Main.elapsedTime;
	if (thist - prevt > 1) {
		postln("PLAYING dt jun left" + (thist - prevt) + "PLAYING");
		thist +>.jun \left;
	}{
		postln("SKIPPING thist" + "diff" + (thist - prevt) + thist + "prevt" + prevt );
	}
};

\junron >>> {
	var prevt, thist;
	prevt =  (\right @ \jun) ? 0;
	thist = Main.elapsedTime;
	if (thist - prevt > 1) {
		postln("PLAYING dt jun right" + (thist - prevt) + "PLAYING");
		thist +>.jun \right;
	}{
		postln("SKIPPING thist" + "diff" + (thist - prevt) + thist + "prevt" + prevt );
	}
}

//:--[1] ====== end ...
//the end

