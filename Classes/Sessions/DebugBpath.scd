a = Set();
b = Bpath("test");
c = Bpath("test");
b == c;
a add: b;
a add: c; // b == c, therefore Set a should not add c!
//:Counterexample using new class BList:
a = BList();
b = Bpath("test");
c = Bpath("test");
d = Bpath("test2");
b == c;
a add: b;
a add: c; // b == c, therefore c was not added
a add: d; // d was added, because it has a different path