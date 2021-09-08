/*  8 Sep 2021 15:19
	Utilities for adding - removing actions to symbols listening
	to IdeFantasy notifications issued by OSCFunctions.
*/

+ Symbol {
	+++> { | action, key = \default |
		postf("adding IDE action named % to %\n", key, this);
		key.addNotifier(IdeFantasy, this, action);
	}

	---> { | key = \default | this remove: key }

	remove { | key = \default |
		postf("removing IDE action named % from %\n", key, this);
		key.removeNotifier(IdeFantasy, this);
	}
}