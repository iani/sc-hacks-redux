/*  8 Sep 2021 15:19
	Utilities for adding - removing actions to symbols listening
	to IdeFantasy notifications issued by OSCFunctions.
*/

+ Symbol {
	oscify {
		// add initial / to self to make into a regular osc command name
		var myString;
		myString = this.asString;
		if (myString[0] == $/) { ^this };
		^("/" ++ myString).asSymbol;
	}

	+++> { | action, key = \default |
		postf("adding IDE action named % to %\n", key, this);
		key.addNotifier(IdeFantasy, this.oscify, action);
	}

	---> { | key = \default | this remove: key }

	remove { | key = \default |
		postf("removing IDE action named % from %\n", key, this);
		key.removeNotifier(IdeFantasy, this.oscify);
	}
}