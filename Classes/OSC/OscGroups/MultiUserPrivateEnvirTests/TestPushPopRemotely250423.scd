//:
var addr;
addr = NetAddr.localAddr;
5 do: { | i |
	addr.sendMsg('/code', format("User(\\auco).push(\\aucopushenv%)", i), \auco);
}
//: local currentEnvironment is unaffected
currentEnvironment
//:5 new environments have been added to User \auco
User(\auco).envirs.keys;
//:Current environment of \auco is the last new environment pushed
User(\auco).postEnvir;
//: pop 5 times and check the environment popped
//Because this is local, currentEnvironment is always the top of auco's stack:
5 do: {
	User(\auco).pop;
	postln("currentEnvironment:" + currentEnvironment);
	postln("auco's envir" + User(\auco).envir);
};
//:
currentEnvironment;
//:
User(\auco).envir;
//:
currentEnvironment === User(\auco).envir;
