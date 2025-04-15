// 火 15  4 2025 05:05
// Holds the 7 buses for a Joint data from Rokoko: x, y, z, ....
/* Geometry data format is
	Position: x, y, z    (cartesian)
	Rotation: x, y, z, w (quaternion)

Corresponding variables are:
	Position: x, y, z
	Rotation: rx, ry, rz, rw

The formula for converting a quaternion to euler notation (i.e. x, y, z)
is given in Python code in file Quaternion2Euler.py in the present folder.

*/
Joint {
	var <envir, <name;
	var <x, <y, <z, <rx, <ry, <rz, <rw; // Buses!

	*new { | envir, name |
		^this.newCopyArgs(envir, name).getBusses;
	}

	setBuses { | argValues |
		// Set bus values from value array in order sent by Rokoko
		var xv, yv, zv, rxv, ryv, rzv, rwv;
		#xv, yv, zv, rxv, ryv, rzv, rwv = argValues;
		x.set(xv);
		y.set(yv);
		z.set(zv);
		rx.set(rxv);
		ry.set(ryv);
		rz.set(rzv);
		rw.set(rwv);
	}
}