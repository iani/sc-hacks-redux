

\justine7.envir.stopsynths
//:--[0.0027503329999945] JUSTINEL grainfrothlpanaz
'justine7'.envir play: ( 'vol': [ 3.356638951877, "", 'off' ],
	'endframe': 2451380,
	'buf': 'justine012',
	'amp': [ 1.0, "Xyz(7, 0.2, 0.1, 1.5) * 0.5", 'on' ],
  'startframe': 0, 'playfunc': 'grainfrothl8', 'trate': [ 0.48412698412698, "\\z5.sr", 'on' ], 'pos': [ 0.079365079365079, "\\x5.sr", 'on' ] )
//: JUSTINEL Control
//// REMOVING DELAY//:--[0.010009416000003]
{Xyz(7, 0.2, 0.1, 1.5) * 0.5}@>.justine7 \amp
//// REMOVING DELAY//:--[0.012848833000021]
{\x7.sr}@>.justine7 \pos
//// REMOVING DELAY//:--[0.01405833299998]
{\z7.sr}@>.justine7 \trate;
{\z7.sr}@>.justine7 \lpos;
{\z7.sr}@>.justine7 \rpos;
