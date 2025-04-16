/*  5 Jan 2023 09:03

*/
FileNavigator.gui;
 bs//:
a = FileNavigator();
\test.hlayout(
	a.outerListView,
	a.innerListView
);
//:
a.getOuterItems;
//:
a.changed(\setOuterListAndIndex, (1..3), 12345);
//:
a.save;
//:
a.load2;
//:
a.currentRoot;
a.outerList;
a.outerList[2];
a.outerItem;
a.innerItem;
//:
a.outerItem = PathName("/Users/iani/sc-projects/ATHENS_CONS_2210ff/AthensCons221029/");

//:
a.zoomIn;
a.zoomOut;
a.getOuterItems;
Preferences.get('FileNavigator2', \default);
//:
a.currentRoot;
b = a.prefs
a.outerIndex = 5;
a.innerIndex = 1;
a.innerIndex;
b[\outerIndex]
<b[\innerIndex]
b.keys;