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
//:counterexample with strings:
a = "alpha";
b = "alpha";
a == b; // a is equal to b
a === b; // a is not identical to b
c = Set();
c add: a; // a was added
c add: b; // b was not added
//:
a = Bpath("test");
b = Bpath("test");
a == b; // a is equal to b
a === b; // a is not identical to b
c = Set();
c add: a; // a was added
c add: b; // b was added (!!!)