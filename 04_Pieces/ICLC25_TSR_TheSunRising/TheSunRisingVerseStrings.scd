~verses = [
    "Busy old fool, unruly sun,",
    "WhyDost thou thus,", // merge 2 words to avoid duplicate incipits
    "Through windows, and through curtains call on us?",
    "Must to thy motions lovers' seasons run?",
    "Saucy pedantic wretch, go chide",
    "Late school boys and sour prentices,",
    "Go tell court huntsmen that the king will ride,",
    "Call country ants to harvest offices,",
    "Love, all alike, no season knows nor clime,",
    "Nor hours, days, months, which are the rags of time.",

    "Thy beams, so reverend and strong",
    "WhyShouldst thou think?", // merge 2 words to avoid duplicate incipits
    "I could eclipse and cloud them with a wink,",
    "But that I would not lose her sight so long;",
    "If her eyes have not blinded thine,",
    "Look, and tomorrow late, tell me,",
    "Whether both th' Indias of spice and mine",
    "Be where thou leftst them, or lie here with me.",
    "Ask for those kings whom thou saw'st yesterday,",
    "And thou shalt hear, All here in one bed lay.",

    "She's all states, and all princes, I,",
    "Nothing else is.",
    "Princes do but play us; compared to this,",
    "All honor's mimic, all wealth alchemy.",
    "Thou, sun, art half as happy as we,",
    "In that the world's contracted thus.",
    "Thine age asks ease, and since thy duties be",
    "To warm the world, that's done in warming us.",
    "Shine here to us, and thou art everywhere;",
    "This bed thy center is, these walls, thy sphere."
];
//:
~verses.size;
//:
~inits = ~verses.collect({ | v | v.split($ )[0].asSymbol });
~inits.size;
//:
Set().addAll(~inits).size;
//:
~inits do: _.postln;
//:
/*
Busy
WhyDost
Through
Must
Saucy
Late
Go
Call
Love,
Nor
Thy
WhyShouldst
I
But
If
Look,
Whether
Be
Ask
And
She's
Nothing
Princes
All
Thou,
In
Thine
To
Shine
This
*/

//:
~dups = ~inits collect: { | i | ~inits.select({|j| j === i}); };
/*

	Busy_old
	Why_dost
	Through_windows,
	Must_to
	Saucy_pedantic
	Late_school
	Go_tell
	Call_country
	Love,_all
	Nor_hours,
	Thy_beams,
	Why_shouldst
	I_could
	But_that
	If_her
	Look,_and
	Whether_both
	Be_where
	Ask_for
	And_thou
	She's_all
	Nothing_else
	Princes_do
	All_honor's
	Thou,_sun,
	In_that
	Thine_age
	To_warm
	Shine_here
	This_bed
*/
