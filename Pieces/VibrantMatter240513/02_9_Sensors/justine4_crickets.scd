

\justine4.envir.stopsynths
//:--[0.0027503329999945] JUSTINEL grainfrothlpanaz
'justine4'.envir play: ( 'vol': [ 3.356638951877, "", 'off' ],
	'endframe': 2451380,
	'buf': 'crickets',
	'amp': [ 1.0, "Xyz(4, 0.2, 0.1, 1.5) * 0.5", 'on' ],
  'startframe': 0, 'playfunc': 'grainfrothl8', 'trate': [ 0.48412698412698, "\\z5.sr", 'on' ], 'pos': [ 0.079365079365079, "\\x5.sr", 'on' ] )
//: JUSTINEL Control
//// REMOVING DELAY//:--[0.010009416000003]
{Xyz(4, 0.2, 0.1, 1.5) * 0.5}@>.justine4 \amp
//// REMOVING DELAY//:--[0.012848833000021]
{\x4.sr}@>.justine4 \pos
//// REMOVING DELAY//:--[0.01405833299998]
{\z4.sr}@>.justine4 \trate;
{\z4.sr}@>.justine4 \lpos;
{\z4.sr}@>.justine4 \rpos;