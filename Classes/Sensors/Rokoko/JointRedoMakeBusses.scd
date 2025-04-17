//:
Rokoko.atDate.oscDataGui;
//:
Rokoko.atDate.push;
~baubo;
//:
~baubo.push;
~hip;
//: Make busses for the 7 vars of a single joint
~makeBusses = {
	var joint, name, jointNames, shortJointNames, varNames;
	var inBusNames, shortInBusNames;
	var outBusNames, shortOutBusNames;
	joint = ~hip;
	joint.postln;
	name = joint.name;
	jointNames = Joint.jointNames;
	shortJointNames = Joint.shortJointNames;
	varNames = Joint.varNames;
	inBusNames = varNames collect: { | n | (name ++ n).asSymbol };
	shortInBusNames = varNames collect: { | n | (name ++ n).asSymbol }
};
//:
~makeBusses.value;