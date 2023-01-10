/* 12 Nov 2022 10:45
Utility methods.
Array must be in the format stored in allData classvar of OscDataReader.
[[time, [message]], [time, [message]] ...]

*/

+ Array {

	playOsc { | repeats = 1, player = \oscdata, envir = \oscdata,  enableCodeEvaluation = true |
		this.oscPlayer.play(repeats, player, envir,  enableCodeEvaluation);
	}
	oscPlayer { ^OscDataPlayer(this) }
}