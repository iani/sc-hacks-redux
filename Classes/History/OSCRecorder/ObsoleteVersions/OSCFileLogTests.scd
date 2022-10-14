/* 31 Aug 2022 09:49
From SC-OSCFileLog/OSCFileLog.sc
	(OSCFileLog repository).
*/
// to record, create a OSCFileLog
~timelog = OSCFileLog.new( "test" ); // "test" is the base for the filename, a datetime stamp will be automatically added to the name
//:
~timelog.inspect;
//: send some osc data to test:
n = NetAddr.new( "localhost", NetAddr.langPort );
//:
(
Task({ 10.do{
	n.sendMsg( "/hello", 0, 20.rand, 19.rand, "hello", 3, 4, 2.003);
	1.0.rand.max(0.01).wait;
}}).play;
);
//:
// close the file again:
~timelog.close;
