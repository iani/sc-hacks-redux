/* 24 Feb 2022 18:14

*/

+ String {

	removeFirstLine {
		^this[this.indexOf(Char.nl)..]
	}
	sepcatList { arg separator = "", list;
		// concatenate this with a list as a string
		var string = this.copy;
		list.do({ arg item, i;
			string = string ++ separator ++ item;
		});
		^string
	}
	absPath {
		^PathName(this).asAbsolutePath;
	}

	share { this.interpretAndShare } // Shortcut. Nicer name
	interpretAndShare { // interpret and let OscGroups share
		// the interpreted code.
		Interpreter.changed(\code, this);
		^this.interpret;
	}

	ok {
		var window;
		window = this.vlayout(
			StaticText()
			.background_(Color(0.9, 1.0, 1.0))
			.font_(Font("Arial", 24))
			.string_(this),
			Button()
			.states_([["OK"]])
			.font_(Font("Arial", 24))
			.action_({ window.close })
		);
		window.bounds = Rect(400, 300, 500, 500);
	}

	name { ^PathName(this).fileNameWithoutExtension.asSymbol }
}

/*
+ SequenceableCollection {
	pathcat {

	}
}
*/
