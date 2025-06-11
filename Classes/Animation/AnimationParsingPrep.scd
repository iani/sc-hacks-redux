//: Part 1: Read data from osc message recorded on file,
//: Convert data to
a = Animation.sessions.asArray;  // ok
b = a.first; // ok
b.files; // ok
b.readData; // ok
c = b.numdata; // ok
d = c.first; // ok
d.size; // ok: 161 floats
d.[150..]; //comparison with original data on file. OK
b.type; // '/rokoko'  OK
//: Part 2: Save data as aiff sound files, for playback in control busses.
// ------ TBD! ------
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
//:parsing yolo
// from: oscdata/241018_Goussot_Yolo/241018_144235/241018_144236.scd
y = [ '/poseperson', 1, 0.85145431756973, 0.48440232872963, 0.88653075695038, 0.98921716213226, 0.47711366415024, 0.90985441207886, 0.96373224258423, 0.49169093370438, 0.90985441207886, 0.97064632177353, 0, 0, 0.72061663866043, 0, 0, 0.83250683546066, 0.45524773001671, 0.74658906459808, 0.9930100440979, 0.54271137714386, 0.7232654094696, 0.9954748749733, 0.44795912504196, 0.67661821842194, 0.96849679946899, 0.57915455102921, 0.56000000238419, 0.97942674160004, 0.4625363945961, 0.81655991077423, 0.9582781791687, 0.56457722187042, 0.42005828022957, 0.97064632177353, 0.46982502937317, 0.32676374912262, 0.99824070930481, 0.52813410758972, 0.32676374912262, 0.99847906827927, 0.4625363945961, 0.0, 0.99248558282852, 0.52813410758972, 0.0, 0.9930100440979, 0.44067049026489, 0.0, 0.94860512018204, 0.52813410758972, 0.0, 0.944933116436 ];
// N.B: element 1 is index of person recognized. Element 2 is global confidence of
// recognized pose. From the above we skip:
// 0 (Message), 1 (index of person), 2 (global confidence)
// Therefore we only deal with data starting from index 3
//:
y.size; // 54 : 3 header values and 51 numeric values.
z = y[3..]; // strip away the header
z.size; // 51 = 17 points, 3 dimensions per point (x,y,z).
z.size/3 // 17.0.  17 points.
17*3 // 51.