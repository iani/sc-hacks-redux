//:  8 Mar 2021 06:28
/* read a file containing recorded osc data from sensor system.
Play back the data.
*/
//:Select file using dialog window
SensePlayer.readDialog;
//:Set the address for sending to local + your port of choice
SensePlayer.setLocalPort(12345);
//:Play
SensePlayer.play;
//:
/*
NOTE: Format of osc messages is: 

[ '/minibee/data', 1, 0.47039434313774, 0.50091564655304, 0.50994992256165 ]

I.e. osc message 'minibee/data' followed by 
- The id of the sensor (an integer)
- the Sensor position data x, y, z : floats

*/