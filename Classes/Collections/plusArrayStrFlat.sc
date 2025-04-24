// For Vasilis Agiomyrianakis
// 水 23  4 2025 21:51
+ Array {
	strFlat {
		var array;
		this do: { | string |
			string do: { | char | array = array add: char };
		};
		^array;
	}
}
