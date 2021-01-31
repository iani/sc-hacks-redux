/* 29 Jan 2021 09:47
Add state without adding variables.
See discussion in https://github.com/iani/sc-hacks-redux/blob/master/README.org
*/

// Function:play was breaking Library for unknown reason
// Therefore using my own here, till the Library/play problem is fixed
MyLibrary {
	classvar <global;

	*initClass {
		global = MultiLevelIdentityDictionary.new;		
	}
}

+ Class {
	// return what is stored in library at [this, key]
	atLibKey { | key | ^MyLibrary.global.at(this, key) }
	// if not present at key, then store new instance in key and return that
	fromLib { | key ... args |
		var new;
		new = this.atLibKey(key);
		new ?? {
			MyLibrary.global.postln;
			postf("I am making a new %\n", this);
			new = this.new(*args);
			// FIXME: this broke triggering from OscTrig. DEBUG!
			// this.addNotifier(new, \objectClosed, { this removeLibKey: key });
			MyLibrary.global.put(this, key, new);
		};
		^new;
	}
	removeLibKey { | key | MyLibrary.global.put(this, key, nil) }
	// return what has been stored in Library under me
	libTree { ^MyLibrary.global.at(this) }
}
