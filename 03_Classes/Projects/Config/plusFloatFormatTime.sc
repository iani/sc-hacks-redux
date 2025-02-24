//: 10 Aug 2021 13:42
/*
    Utility: format as time string showing
	"HH:MM:SS..."
*/

+ SimpleNumber {
	formatTime {
		var hrs, mins, hString, mString;
		hrs = (this / 60 / 60).floor;
		mins = (this - (hrs * 60 * 60) / 60).floor;
		hrs = hrs.asInteger;
		mins = mins.asInteger;
		if (hrs == 0) { hString = "" } { hString = format("%h:", hrs) };
		if(mins == 0) {
			if (hrs > 0) { hString = hString ++ "0'" }
		}{
			hString = hString ++ format("%'", mins);
		};
		^hString ++ (this % 60).round(0.001).asString ++ "\"";
	}
	hrs { ^this * 60 * 60 }
	mins { ^this * 60 }
}