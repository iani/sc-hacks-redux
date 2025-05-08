//: 金  9  5 2025 01:26
// reformatting entry array for better access to joints
~rokoko = \session1.rdata; // load some data in advance
//:start a playback loop
~rokoko.loopSegment; // run a little to get an entry
//: examine and reformat entry
~rokoko.entry;
~joints = ~rokoko.entry[3..].clump(8);
~joints.flop[0] indexOf: \leftToe;
~joints.flop[0] indexOf: \rightToeEnd;
~joints[~joints.flop[0] indexOf: \rightToeEnd];
~joints[~joints.flop[0] indexOf: \rightToeEnd];
~joints;
//:prototype for method to access and alter a joint:
f = { | joint = \rightHand, dim = 1, value = "BLAH" |
	var all, header, joints, index;
	all = ~rokoko.entry;
	header = all[..2];
	joints = all[3..].clump(8);
	index = joints.flop[0] indexOf: joint;
	joints[index][dim + 1] = value;
	header ++ joints.flat;
};
//:
g = f.value;
g.clump(8) do: _.postln;
//: add value to a dim of a joint
f = { | joint = \rightHand, dim = 1, value = 0.01 |
	var all, header, joints, index;
	all = ~rokoko.entry;
	header = all[..2];
	joints = all[3..].clump(8);
	index = joints.flop[0] indexOf: joint;
	joints[index][dim + 1] = joints[index][dim + 1] + value;
	header ++ joints.flat;
};
