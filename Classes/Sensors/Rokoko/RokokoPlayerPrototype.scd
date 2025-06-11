// 水  4  6 2025 17:05
// Play recorded Rokoko sessions into a bus.
// The bus has one channel per sensor dimension.
// Minimum number of channels, for basic Rokoko version: 161.
// Sessions with more channels are in preparation.

// This here is a prototype to figure out the data format
// that will be used in the class.

//: 1. Create a collection of paths storing rokoko data sessions.
a = PathCollection(\rokoko)
a.gui;
a.save;
a.paths;
a.paths = [a.paths];
Path