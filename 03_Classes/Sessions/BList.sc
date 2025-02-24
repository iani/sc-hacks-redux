// A list that keeps only single instances for strings, testing for eqality.
// Mon 19 Feb 2024 17:04
// Note: Use Equality test mechanism for Sets in SuperCollider -
// did not work when second instance variable is added to element.


BList : List {
	add { | element |
		var p;
		p = element.path;
		if (this.detect({ | e | e.path == p }).isNil) {
			array = array add: element;
		}
	}
}