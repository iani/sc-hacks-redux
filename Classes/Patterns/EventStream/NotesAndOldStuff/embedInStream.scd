//: 23 Jan 2021 17:58
/*
old embed in stream code from EventStream.
Not needed any more.
Kept here as example of how one could make an EventStream embeddable,
if needed
*/

+ EventStream {
	embedInStream {
		var outval;
		while {
			outval = this.next;
			outval.notNil;
		}{
			outval.yield;
		};
		nil;
	}
}