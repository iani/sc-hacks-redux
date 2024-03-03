//Fri 23 Feb 2024 12:24
//plusSymbolOSC

+ Symbol {
	>>! { | func, key |
		// Like >>> without prepending / to self.
		// For use with SendReply.
		OSC.addRaw(this, func, key);
	}
}