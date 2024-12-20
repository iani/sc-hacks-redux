/* create a patterns and its stream in one step from an object 

Wed, Mar  5 2014, 16:20 EET: Since PatternTask takes patterns, 
no longer needed to get the Stream here.
(It is more convenient to work with the pattern, to be able to restart it.)

Maybe add another set of shortcuts with prefix sp : 

array.sprandi becomes: Pub(Prand(array, inf)) etc. 

*/

+ Object {
	pn { | repeats = inf | ^Pn(this, repeats); }
	p1 { ^Pn(this, 1) }
}

+ Pattern {
	pdup { | n = 2 | ^Pdup(n, this) }
}

+ SequenceableCollection {
    pseq { | repeats = inf | ^Pseq(this, repeats); }
    pseqi { | repeats = inf | ^this.pseq(repeats); } // shortcut/synonym
    pseqn { | repeats = 1 | ^this.pseq(repeats); } // shortcut/synonym
    pseq1 { | repeats = 1 | ^this.pseq(repeats); } // shortcut/synonym

    pser { | repeats = inf | ^Pser(this, repeats); }
    pseri { | repeats = inf | ^this.pser(repeats); }
    psern { | repeats = 1 | ^this.pser(repeats); }
    pser1 { | repeats = 1 | ^this.pser(repeats); }

	pseries { | repeats = inf | ^Pseries(*this add: repeats) }

	prrand { | repeats = inf |
		^Pfuncn({ this[0].rrand(this[1]) }, repeats)
	}
	pexprand { | repeats = inf |
		^Pfuncn({ this[0].exprand(this[1]) }, repeats)
	}
    prand { | repeats = inf | ^Prand(this, repeats); }
    prandi { | repeats = inf | ^this.prand(repeats); }
    prandn { | repeats = 1 | ^this.prand(repeats); }
    prand1 { | repeats = 1 | ^this.prand(repeats); }
	pwhite { | repeats = inf | ^Pwhite(*(this add: repeats)) }
	pbrown { | repeats = inf | ^Pbrown(*(this ++ [-1, 1, 1][this.size..] add: repeats)) }
}

+ Function {
    pfunc {
        ^Pfunc(this)
    }
    pfuncn { | repeats = 1 |
        ^Pfuncn(this, repeats)
    }

    pcollect { | pattern |
        ^Pcollect(this, pattern)
    } 
    pselect { | pattern |
        ^Pselect(this, pattern)
    }

    preject { | pattern |
        ^Preject(this, pattern)
    }
}
