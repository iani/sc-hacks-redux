// 23 Aug 2023 16:31
//Experimental score for trigger funcs. Run trigger funcs from the score gui.
//Trigger OSC messages are hard coded in each snippet of the script.
//:
{ \x1.sr.sendReply(\jun); Silent.ar } +> \trigger1;
//:
{ Silent.ar } +> \trigger1;
//:
{ Impulse.kr(1) } +>> \jun;
