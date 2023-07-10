/* 10 Jul 2023 00:00
Construct 64 distinct using values obtained from chat gpt query:
give me the rgb color numbers for a palette of 64 colors, where the perceptual color distances are maximised
*/

SelectionColors {
	*new {
		var a, b;
		/*  9 Jul 2023 23:52
			From chat-gpt.
		*/

		a = [[0, 0, 0],
			[128, 128, 128],
			[255, 255, 255],
			[255, 0, 0],
			[0, 255, 0],
			[0, 0, 255],
			[255, 255, 0],
			[0, 255, 255],
			[255, 0, 255],
			[128, 0, 0],
			[0, 128, 0],
			[0, 0, 128],
			[128, 128, 0],
			[0, 128, 128],
			[128, 0, 128],
			[192, 192, 192],
			[128, 128, 64],
			[128, 64, 128],
			[64, 128, 128],
			[128, 64, 0],
			[64, 128, 0],
			[0, 64, 128],
			[128, 0, 64],
			[64, 0, 128],
			[64, 64, 128],
			[128, 64, 64],
			[64, 128, 64],
			[64, 64, 0],
			[0, 64, 64],
			[64, 0, 64],
			[96, 96, 96],
			[96, 0, 0],
			[0, 96, 0],
			[0, 0, 96],
			[96, 96, 0],
			[0, 96, 96],
			[96, 0, 96],
			[32, 32, 32] * [3, 2, 1],
			[32, 0, 0]* [3, 2, 1],
			[0, 32, 0]* [3, 2, 1],
			[0, 0, 32]* [3, 2, 1],
			[32, 32, 0]* [3, 2, 1],
			[0, 32, 32]* [3, 2, 1],
			[32, 0, 32]* [3, 2, 1],
			[48, 48, 48]* [3, 2, 1],
			[48, 0, 0]* [3, 2, 1],
			[0, 48, 0]* [3, 2, 1],
			[0, 0, 48]* [3, 2, 1],
			[48, 48, 0]* [3, 2, 1],
			[0, 48, 48]* [3, 2, 1],
			[48, 0, 48]* [3, 2, 1],
			[16, 16, 16]* [3, 2, 1],
			[16, 0, 0]* [3, 2, 1],
			[0, 16, 0]* [3, 2, 1],
			[0, 0, 16]* [3, 2, 1],
			[16, 16, 0]* [3, 2, 1],
			[0, 16, 16]* [3, 2, 1],
			[16, 0, 16]* [3, 2, 1],
			[24, 24, 24]* [3, 2, 1],
			[24, 0, 0]* [3, 2, 1],
			[0, 24, 0]* [3, 2, 1],
			[0, 0, 24]* [3, 2, 1],
			[24, 24, 0]* [3, 2, 1],
			[0, 24, 24]* [3, 2, 1]];
		b = a collect: { | x | x /255 }
		^b collect: Color(*_);
	}
}