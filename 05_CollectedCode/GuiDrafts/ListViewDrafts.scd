//: Fri  2 Feb 2024 09:31
//:Theme & Hilite color and selection color for items
//Objecive:
// When a path points to nothing (file or directory does not exist),
// then:
// - if the item is not selected, color of the item is be gray,
// - if the item is selected, then the color of the item is pink
// So the user can always see which paths are not valid (do not correspond to
// any existing file or folder)
//:
var l, items;
items = (100..110);
\lview.vlayout(
	l = ListView();
);
l.postln;
l.items = items;
l.palette = QPalette.light;
l.hiliteColor = Color(0.7, 0.8, 1.0);
l.colors = items collect: { | i |
	if (i.isPrime) { Color.white } { Color.grey(0.9) }
};
l.selectionAction = { | me |
	var s;
	s = me.items[me.selection.first];
	postln("s: " + s + "is prime:" + s.isPrime);
	if (s.isPrime) {
		me.hiliteColor = Color(1.0, 0.8, 0.7);
	}{
		me.hiliteColor = Color(0.7, 0.8, 1.0);
	}
}
//:Test multiple selections - for selecting multiple resource sets.
var l;
\mview.vlayout(
	l = ListView();
);
l.hiliteColor = Color(0.7, 0.8, 1.0);
l.items = (100..110);
l.selectionMode = \multi;
