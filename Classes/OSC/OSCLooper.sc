OSCLooper : OSCData{
	/*220329 - Under Construction
	OSCLooper is a part of OSC library of sc-hacks-redux
	First select data using OSCData class
	OSCData.selectSession;
	then collect using OSCLooper
	OSCLooper.dataCollect;
	Use the ramp to collect data i.e from the first data element to the hundredth
	OSCLooper ramp method arguments (start, end, dur)
	OSCLooper.ramp(2, 15, 0.5);
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
				//dt[6];
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

*magneticCollect{
~files = "~/Documents/Projects/Publications/Conferences/COSPAR/sonification_SuperCollider/data/*.dat.txt".pathMatch.postln;

//:load and collect data
	"load data".postln;

~load = { | path |
	var data;
	// select only these rows which contain 7 columns exactly:
	data = CSVFileReader.read(path) select: { | row, column |
		row.size == 7;
		//column.size == 100;
	};
// collect 2 to 4 rows from the list and replace symbols such as "+"
	data.flop[2..4].flop collect: { | row |
		row collect: { | string |
			string.replace("+", "").interpret;

		}
	};
};


}


//:
// load from the data files the first one
*magneticStart{|start, end, dur |
			fork{
				var data;
				var x,y,z,all;

	data = ~load.(~files.first);
				//	x = data.flop[0];
				//	y = data.flop[1];
				//	z = data.flop[2];


				"run data: storm starts".postln;


				/*

				(start..end) do: { | row |

					data.flop[0];
					row[0].wait.postln;
					//row[0].postln;
						//~row1 = row[0].abs.linlin(38.0, 39.0, 0.1, 1).postln;
					//	dur.wait;
						//[row[0],row[1], row[2]].postln;
					};

				*/
				data do: {|row|


var addr = NetAddr("127.0.0.1", 12345);
//"TO - SYNTH".postln;
					/*~rows = [	row[0],
					row[1],
					row[2]
						]*dur.wait.postln;*/
// Parameter mapping
					//[row[0], row[1], row[2]].postln;

					~row1 = row[0].abs.linlin(38.0, 39.0, 0.1, 1).postln;
					~row2 = row[1].linlin(66.0, 67.0, 0.1, 1).postln;
					~row3 = row[2].linlin(46.0, 47.0, 0.1, 1).postln;
					//~column1 = column.postln;
					dur.wait;

							};


}

}

}