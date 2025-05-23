
+ SequenceableCollection {
	asAscii {  ^this.collect({|x| {x.asInteger.asAscii}.try ? "" }).join }
}
