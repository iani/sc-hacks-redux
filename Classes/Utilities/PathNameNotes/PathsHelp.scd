// 日  1  6 2025 10:30
// using Paths
//:
var buf;
buf = Buffer.alloc(Server.default, 60 * 60 * 30, 161);
Paths.doGetPath({ | path |
	var p;
	p = (path  +/+ Date.localtime.stamp ++ ".aiff").postln;
	buf.write(p);
}, \bufsaving2)
//:
2.pow(16) * 30.reciprocal / 60;

60 * 60 * 30
//: