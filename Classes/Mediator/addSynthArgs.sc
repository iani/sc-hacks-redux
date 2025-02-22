/* Wed 12 Feb 2025 13:12

Add value to argument array only if applicable.

*/

+ Object {
    addSynthArgs { | array, key |
       ^array;
	}
}

+ SimpleNumber {
    addSynthArgs { | array, key |
       ^array ++ [key, this];
	}
}

+ ValueAdapter {
   addSynthArgs { | array, key |
       ^array ++ [key, value ? 0];
	}
}
