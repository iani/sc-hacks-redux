

\justine5.envir.stopsynths
//:--[0.0027503329999945] JUSTINEL grainfrothlpanaz
'justine5'.envir play: ( 'vol': [ 3.356638951877, "", 'off' ],
	'endframe': 2451380,
	'buf': 'justine009',
	'amp': [ 1.0, "Xyz(5, 0.2, 0.1, 1.5) * 0.5", 'on' ],
  'startframe': 0, 'playfunc': 'grainfrothl8', 'trate': [ 0.48412698412698, "\\z5.sr", 'on' ], 'pos': [ 0.079365079365079, "\\x5.sr", 'on' ] )
//: JUSTINEL Control
//// REMOVING DELAY//:--[0.010009416000003]
{Xyz(5, 0.2, 0.1, 1.5) * 0.5}@>.justine5 \amp
//// REMOVING DELAY//:--[0.012848833000021]
{\x5.sr}@>.justine5 \pos
//// REMOVING DELAY//:--[0.01405833299998]
{\z5.sr}@>.justine5 \trate;
{\z5.sr}@>.justine5 \lpos;
{\z5.sr}@>.justine5 \rpos;
