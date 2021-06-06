/*  4 Jun 2021 17:56
All sc-hacks-redux operators for all classes, in one file.
*/

+ SimpleNumber {
	+> { | key |
		postf("Yep! This is % +> %\n", this, key);
	}
}


+ Symbol {
	push {
		^Mediator.fromLib(this).push;
	}
} 
