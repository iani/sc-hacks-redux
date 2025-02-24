

\justine3.envir.stopsynths
//:--[0.0027503329999945] JUSTINEL grainfrothlpanaz
'justine3'.envir play: ( 'vol': [ 3.356638951877, "", 'off' ],
	'endframe': 2451380,
	'buf': 'justine020',
	'amp': [ 1.0, "Xyz(3, 0.2, 0.1, 1.5) * 0.5", 'on' ],
  'startframe': 0, 'playfunc': 'grainfrothl8', 'trate': [ 0.48412698412698, "\\z5.sr", 'on' ], 'pos': [ 0.079365079365079, "\\x5.sr", 'on' ] )
//: JUSTINEL Control
//// REMOVING DELAY//:--[0.010009416000003]
{Xyz(3, 0.2, 0.1, 1.5) * 0.5}@>.justine3 \amp
//// REMOVING DELAY//:--[0.012848833000021]
{\x3.sr}@>.justine3 \pos
//// REMOVING DELAY//:--[0.01405833299998]
{\z3.sr}@>.justine3 \trate;
{\z3.sr}@>.justine3 \lpos;
{\z3.sr}@>.justine3 \rpos;
