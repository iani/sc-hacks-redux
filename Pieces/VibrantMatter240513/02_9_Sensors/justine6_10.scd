

\justine6.envir.stopsynths
//:--[0.0027503329999945] JUSTINEL grainfrothlpanaz
'justine6'.envir play: ( 'vol': [ 3.356638951877, "", 'off' ],
	'endframe': 2451380,
	'buf': 'justine010',
	'amp': [ 1.0, "Xyz(6, 0.2, 0.1, 1.5) * 0.5", 'on' ],
  'startframe': 0, 'playfunc': 'grainfrothl8', 'trate': [ 0.48412698412698, "\\z5.sr", 'on' ], 'pos': [ 0.079365079365079, "\\x5.sr", 'on' ] )
//: JUSTINEL Control
//// REMOVING DELAY//:--[0.010009416000003]
{Xyz(6, 0.2, 0.1, 1.5) * 0.5}@>.justine6 \amp
//// REMOVING DELAY//:--[0.012848833000021]
{\x6.sr}@>.justine6 \pos
//// REMOVING DELAY//:--[0.01405833299998]
{\z6.sr}@>.justine6 \trate;
{\z6.sr}@>.justine6 \lpos;
{\z6.sr}@>.justine6 \rpos;
