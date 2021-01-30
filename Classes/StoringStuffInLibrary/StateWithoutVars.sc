/* 29 Jan 2021 09:47
Add state without adding variables.
See discussion in https://github.com/iani/sc-hacks-redux/blob/master/README.org
*/

+ Class {
	// return what is stored in library at [this, key]
	access { | key | ^Library.global.at(this, key) }
	// if not present at key, then store new instance in key and return that
	obtain { | key ... args |
		var new;
		new = this.access(key);
		new ?? {
			new = this.new(*args);
			Library.global.put(this, key, new);
		};
		^new;
	}
	removeKey { | key | Library.global.put(this, key, nil) }
	// return what has been stored in Library under me
	libTree { ^Library.global.at(this) }
}
