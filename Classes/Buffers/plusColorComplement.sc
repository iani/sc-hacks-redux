/* 10 Jul 2023 00:36
Helper for creating complementary color for text in ListView of SoundBufferGui.

*/

+ Color {
	complement { ^Color(1 - this.red, 1 - this.green, 1 - this.blue) }
}