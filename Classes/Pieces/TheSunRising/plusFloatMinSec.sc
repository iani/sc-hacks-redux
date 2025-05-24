+ Float {
	minsec {
		var mins, secs;
		mins = (this / 60).round(1);
		secs = this % 60
		^format("%:%", mins.asInteger, secs.round(0.001)) }
}