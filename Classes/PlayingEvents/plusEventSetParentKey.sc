/* 21 Jan 2021 10:54

This route is now abandoned. Resuming with rebuilding event playing in pattern approach from scratch. 

COULD NOT GET THIS TO WORK WITH EventPattern or Pbind or anything.

Tried these:

//:
EventPattern((dur: 0.2).setParentKey(\freq, 1000)).play;
//:
(dur: 0.2).setParentKey(\freq, 1000).parent.keys.asArray.sort.postln;
(dur: 0.2).setParentKey(\freq, 1000).parent[\freq].postln;
//:
a = (dur: 0.2);
EventPattern(a).play;
//:
b = (dur: 0.2).setParentKey(\freq, 1000);
EventPattern(b).play;
b.freq;

Copy current parent event or defaultParentEvent, then set a new key value in it, and use it as parent to this Event.
Thereby ensure that: 
- this Event has a parent
- the modifications to this parent are local to this event, and not shared by other events.
*/

+ Event {
	 setParentKey { | key, newValue |
		 // preserve previous changes to parent!
		 parent = (parent ? defaultParentEvent).copy;
		 parent.put(key, newValue);
		 //		 parent.postln;
	 }

	/*	
	//  Temporary test for testing syntax usage:

	argtest { | player, input |
		/* works with all of the below: 
().argtest;
() argtest: 1;
().argtest(1)
().argtest(1, 2);
		*/
		postf("Temporary test method. player: %, input: %\n", player, input);
	}
	*/


}



