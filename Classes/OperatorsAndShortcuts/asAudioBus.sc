/* 13 Aug 2023 15:55

*/

+ Nil {
	asAudioBus { ^0 }
}

+ SimpleNumber {
	asAudioBus { ^this.asInteger }
}

+ Bus {
	asAudioBus { ^this }
}

+ Symbol {
	asAudioBus {  | numChannels = 2 | ^this.ab(numChannels) }
}