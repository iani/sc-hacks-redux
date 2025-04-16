/*  7 Dec 2022 13:42
Send OSC from inside a pattern,

asNetAddr

Pconcat
*/



Pconcat {
	var <head, <tail, <headStream, <tailStream;

	*new { | head, tail |
		^this.newCopyArgs(head, tail);
	}

	asStream {
		headStream = head.asStream;
		tailStream = tail.asStream;
	}

	next {
		var headVal, tailVal;
		headVal = headStream.next;
		tailVal = tailStream.next;
		if (headVal.isNil or: { tailVal.isNil }) { ^nil };
		^headVal.asArray ++ tailVal.asArray;
	}
}

Posc {
	var <>pattern, <>addr, <stream;
	*new { | pattern, addr |
		^this.newCopyArgs(pattern, addr.asNetAddr);
	}

	asStream {
		// 'making stream'.postln;
		stream = pattern.asStream;
	}

	next {
		var next;
		next = stream.next;
		next !? { addr.sendMsg(*next) };
		^next;
	}
}

OscMsgPat {

}

+ Nil {
	asNetAddr { ^LocalAddr() }
}

+ Integer {
	asNetAddr {
		^NetAddr.local(this)
	}
}

+ NetAddr {
	*local { | port = 57120 |
		^this.new("127.0.0.1", port)
	}

	asNetAddr {} // needed by Posc!
}

+ Object {
	++++ { | tail | ^Pconcat(this, tail) }
}