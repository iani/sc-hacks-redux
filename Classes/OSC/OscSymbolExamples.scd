/* 14 Feb 2022 12:04
Examples for using symbol operators to add functions to received
	osc messages.

	Try these with booted local server to listen to '/status.reply'
*/
//:
Server.default.boot;
//:
'/status.reply' >>> { "STATUSREPLY RECEIVED".postln; };
//doubles because the key is different!!!
'status.reply' >>> { "STATUSREPLY RECEIVED hah".postln; };
//: Here is how to remove both of the above:
'/status.reply' <<< '/status.reply';
'/status.reply' <<< 'status.reply';
//:
OSC.activeMessages;
//:
'/status.reply' >>>.more { "more STATUSREPLY RECEIVED".postln; };
//: does not double because the key is given explicitly
'status.reply' >>>.more { "more STATUSREPLY RECEIVED".postln; };
//:
'/status.reply'.removeOSC;
'status.reply'.removeOSC;
//:
'/status.reply'.removeOSC(\more);
'status.reply'.removeOSC(\more);
//:
//: remove all osc messages:
'more' <<< '/status.reply';

'/status.reply' <<< 'more'





OSC.clear;
//:
'/status.reply' >>@ NetAddr("127.0.0.1", 12000);


//: sharing evaluated code via oscgroups
\code.share;
//:
1 + 10;