// Various ways to enable User:
//: 1. Just enable User with default session name and default
// local user named after your home directory:
User.enable;
//: 2. Enable User with custom session name
User.enable("MySession") // use session name "MySession"
// session name is used to save ALL CODE from all users in the session
// under Platform.userAppSupportDir +/+ "Sessions" +/+ (timestamp + sessionname)
// Example of a session folder:
//  /Users/iani/Library/Application Support/SuperCollider/Sessions/250423_130945the_sun_rising
//: 3. Run enable with a delay, to prevent receiving scripts from
// other users at StartUpTime:
User.enable("MySession", 3); // delay enable by 3 seconds.
//: 4. Add and activate some users after enabling:
User.enable("the_sun_rising", nil, \iani, \geodia, \vasilis, \kosmas, \peny);
// Delay argument nil above means run enable immediately with no delay.
// The above command creates the users:
// \iani, \geodia, \vasilis, \kosmas, \peny
//: 5. Local user name
// Your local user name is the same as your home directory.
// You can also confirm this by letting User generate it and ask for it:
//: a. Run User.enable with no arguments.
User.enable;
//: b. Ask your local id name:
User.localId;