

\justine1.envir.stopsynths
//:--[0.0027503329999945] JUSTINEL grainfrothlpanaz
'justine1'.envir play: ( 'vol': [ 3.356638951877, "", 'off' ],
	'endframe': 2451380,
	'buf': 'justine002',
	'amp': [ 1.0, "Xyz(1, 0.2, 0.1, 1.5) * 0.5", 'on' ],
  'startframe': 0, 'playfunc': 'grainfrothl8', 'trate': [ 0.48412698412698, "\\z5.sr", 'on' ], 'pos': [ 0.079365079365079, "\\x5.sr", 'on' ] )
//: JUSTINEL Control
//// REMOVING DELAY//:--[0.010009416000003]
{Xyz(1, 0.2, 0.1, 1.5) * 0.5}@>.justine1 \amp
//// REMOVING DELAY//:--[0.012848833000021]
{\x1.sr}@>.justine1 \pos
//// REMOVING DELAY//:--[0.01405833299998]
{\z1.sr}@>.justine1 \trate;
{\z1.sr}@>.justine1 \lpos;
{\z1.sr}@>.justine1 \rpos;
