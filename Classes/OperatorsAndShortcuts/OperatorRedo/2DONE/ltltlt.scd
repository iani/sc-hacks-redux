//Fri 23 Feb 2024 12:25
//plusSymbolOsc

+ Symbol {
	<<< { | key | // remove action registered under this message and key pair.
		this.removeOSC(key);
	}
}