/* 火 15  4 2025 19:59
Create a Rokoko instance using this date as timestamp in the name.
Start the instance listening to incoming '/rokoko/' OSC messages.
Create an OscData gui with data obtained from a folder chosen by the user,
via FileDialog.
Note: Once the folder is chosen, it is saved in userAppSupportDir,
	under the name of the Rokoko instance.
	Rokoko instance will read the data from the saved path if available.
*/
Rokoko.atDate.enable.oscDataGui;
// Once the OscData gui is open, click the play Checkbox to send some
// data to the Rokoko instance.
// Then: Inspect the environment of the Rokoko, containing its actors:
Rokoko.atDate.envir;
// Obtain the array of created actors:
Rokoko.atDate.actors;
// Inspect the first actor:
Rokoko.atDate.actors.first.inspect;
// Obtain the array of joints in the first actor:
Rokoko.atDate.actors.first.joints;