//水 14  5 2025 14:35
// Open an SC Document. Process typing from it:
// 1. forward characters as ascii to oscgroups
// 2. forward index of matching verses to oscgroups
// ---
// Only do this if the name given at instance creation matches the
// local user's name (User.localId).

TypeTsr : NamedSingleton2 {
	classvar <>verbose = false;
	classvar <verses;
	actions {
		^LocalUserAction(name, this);
	}
	typeVerse { | verseNum = 0 |
		var verse;
		verse = verses@@verseNum;
		// verse.postln;
		{
			verse do: { | char |
				verbose.if { char.postln; };
				Tsr.type(char);
				[0.1, 0.2, 0.25, 0.4].choose.wait;
			};
		}.fork;
	}

	typeVerses { | from = 0, to |
		var verse, section;
		from = from.clip(0, verses.size - 1);
		to = (to ? verses.size - 1).clip(0, verses.size - 1);
		section = (from..to);
		{
			section do: { | v |
				verses[v] do: { | char |
					Tsr.type(char);
					[0.1, 0.2, 0.25, 0.4].choose.wait;
				};
				Tsr.type(Char.nl, nil, 13);
				2.exprand(5.0).wait;
			};
		}.fork;
	}

	testVerse {
		~verses1.postln;
	}

	init {
		verses = [
			"Busy old fool, unruly Sun,",
			"Why dost thou thus,",
			"Through windows, and through curtains, call on us?",
			"Must to thy motions lovers' seasons run?",
			"Saucy pedantic wretch, go chide",
			"Late school-boys and sour prentices,",
			"Go tell court-huntsmen that the king will ride,",
			"Call country ants to harvest offices;",
			"Love, all alike, no season knows nor clime,",
			"Nor hours, days, months, which are the rags of time.",
			// ------------
			"Thy beams so reverend, and strong",
			"Why shouldst thou think?",
			"I could eclipse and cloud them with a wink,",
			"But that I would not lose her sight so long.",
			"If her eyes have not blinded thine,",
			"Look, and to-morrow late tell me,",
			"Whether both th' Indias of spice and mine",
			"Be where thou left'st them, or lie here with me.",
			"Ask for those kings whom thou saw'st yesterday,",
			"And thou shalt hear, \"all here in one bed lay.\"",
			// ------------
			"She's all states, and all princes I;",
			"Nothing else is;",
			"Princes do but play us; compared to this,",
			"All honour's mimic, all wealth alchemy.",
			"Thou, Sun, art half as happy as we,",
			"In that the world's contracted thus;",
			"Thine age asks ease, and since thy duties be",
			"To warm the world, that's done in warming us.",
			"Shine here to us, and thou art everywhere;",
			"This bed thy center is, these walls thy sphere.",
		]
	}
}
