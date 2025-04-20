OscGroups.enable;
//:
NetAddr.localAddr.sendMsg('/code', "1000.rand", \iani);
NetAddr.localAddr.sendMsg('/code', "~x = 1000.rand", \iani);
//:
\iani.userEnvir;
//: Push should operate on
\x.push;
currentEnvironment.pop;
