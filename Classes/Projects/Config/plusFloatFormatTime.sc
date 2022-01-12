//: 10 Aug 2021 13:42
/*
    Utility: format as time string showing
	"HH:MM:SS..."
*/

+ SimpleNumber {
	formatTime {
		var hrs, mins;
		hrs = (this / 60 / 60).floor;
		mins = (this - (hrs * 60 * 60) / 60).floor;
		^format("%:%:%", hrs.asInteger, mins.asInteger, this % 60);
	}
	hrs { ^this * 60 * 60 }
	mins { ^this * 60 }
}