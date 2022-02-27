/* 14 Feb 2022 12:04
Examples for using symbol operators to add functions to received
	osc messages.

	Try these with booted local server to listen to '/status.reply'
*/
//:
Server.default.boot;
//:
'/status.reply' >>> { "STATUSREPLY RECEIVED".postln; };
'status.reply' >>> { "STATUSREPLY RECEIVED".postln; };
//:
OSC.activeMessages;
//:
'/status.reply' >>>.more { "more STATUSREPLY RECEIVED".postln; };
'status.reply' >>>.more { "more STATUSREPLY RECEIVED".postln; };
//:
'/status.reply'.removeOSC;
'status.reply'.removeOSC;
//:
'/status.reply'.removeOSC(\more);
'status.reply'.removeOSC(\more);
//:
'/status.reply' <<< '/status.reply';
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