OSCLooper : OSCData{
	/*220329 - Under Construction
	OSCLooper is a part of OSC library of sc-hacks-redux
	First select data using OSCData class
	OSCData.selectSession;
	then collect using OSCLooper
	OSCLooper.dataCollect;
	Use the ramp to collect data i.e from the first data element to the hundredth
	OSCLooper.ramp(0, 3, 1);
	*/

	classvar data;

	//messages = {^super.messages};
	*dataCollect{

	data = OSCData.loadedSession.data;
}


	*ramp{|start, end, dur|
			{

				var messages;
				var dt;
				messages = data.flop[0];
				dt = data.flop[1].differentiate;
				dt[6];
				(start..end) do: { | index, count |
			//if (count > 0) {dt[index]*dur.wait;}

				dt*dur.wait;
				postln("playing element at index:" + index + "COUNT IS" + count);
				postln("the message is:");
				//messages[index].postln;
				messages[count].postln;
	};
			}.fork;


	}


}