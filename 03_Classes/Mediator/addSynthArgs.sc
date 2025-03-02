/* Wed 12 Feb 2025 13:12

Add value to argument array only if applicable.

*/

+ Object {
    synthArgs { | key |
       ^[];
	}
}

+ SimpleNumber {
    synthArgs { | key |
       ^[key, this];
	}
}

/*
+ ValueAdapter {
   synthArgs { | key |
       ^[key, value ? 0];
	}
}
*/