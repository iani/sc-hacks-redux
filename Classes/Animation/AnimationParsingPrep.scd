
a = Animation.sessions.asArray;
b = a.first;
b.files;
b.readData;
c = b.numdata;
d = c.first;
d.size;
d.[150..];
//:
c = OscData(b.files);
//:Lookig at times. Finding out the average sample playback rate
t = c.times; // these are times relative to 0 the first item.
t.last; // this is the sum of dtimes ...
t.size;
v = t.last / t.size; // so this is the average, which is:
v; // -> 0.033559528011669 // slightly less than 30 frames per second
x = t.differentiate; // looking at dt: times between frames
x.maxItem; // 0.0713...
x.minItem;
x[1..].minItem; // 0.00098 ... // large variance in dt!

//: looking at the messages
m = c.messages;
m.first.class;
o = m.first.interpret;
o.first; // '/rokoko' : This defines the animation format.
// a peak at timestamps:
m[2].interpret;
// collect the timestamps of the first 11 elements:
y = m[..10] collect: { | x | x.interpret[1] };
y.last / y.size; // average
// -> 0.033170599829067 // framerate slightly over 30 fps

//:
b.name;
(b.name.asString +/+ "*.scd")
Animation.gui;
//: