//onsetcode
//Exporting 64 code snippets on 231019_110527
//Source: [ 231019_110222 ]
//:--[0] CLEAR
\justineL.envir.stopsynths
//:--[0.0027503329999945] JUSTINEL grainfrothlpanaz
'justineL'.envir play: ( 'vol': [ 3.356638951877, "", 'off' ],
    'endframe': 2451380, 'buf': 'crickets', 'amp': [ 1.0, "Xyz(5, 0.2, 0.1, 1.5) * 0.5", 'on' ],
  'startframe': 0, 'playfunc': 'grainfrothl8', 'trate': [ 0.48412698412698, "\\z5.sr", 'on' ], 'pos': [ 0.079365079365079, "\\x5.sr", 'on' ] )
//: JUSTINEL Control
//// REMOVING DELAY//:--[0.010009416000003]
{Xyz(5, 0.2, 0.1, 1.5) * 0.5}@>.justineL \amp
//// REMOVING DELAY//:--[0.012848833000021]
{\x5.sr}@>.justineL \pos
//// REMOVING DELAY//:--[0.01405833299998]
{\z5.sr}@>.justineL \trate;
{\z5.sr}@>.justineL \lpos;
{\z5.sr}@>.justineL \rpos;

//:--[1.6810296250001] JUSTINER stop
\justineR.envir.stopSynths
//:--[1.681997041] JUSTINER grainfrothlpanaz
'justineR'.envir play: ( 'vol': [ 3.356638951877, "", 'off' ], 'endframe': 2451380, 'buf': 'fones', 'amp': [ 1.0, "Xyz(6, 0.2, 0.1, 1.5)", 'on' ], 
  'startframe': 0, 'playfunc': 'grainfrothl8', 'trate': [ 0.48412698412698, "\\z5.sr", 'on' ], 'pos': [ 0.079365079365079, "\\x5.sr", 'on' ] )
//:--[1.688779583] JUSTINER Control
{Xyz(6, 0.2, 0.1, 1.5) * 0.5 }@>.justineR \amp
//:--[1.69148425]
{\x6.sr}@>.justineR \pos
//:--[1.692816333]
{\z6.sr}@>.justineR \trate
{\z6.sr}@>.justineR \trate;
{\z6.sr}@>.justineR \lpos;
{\z6.sr}@>.justineR \rpos;

//:--[4.9310054580001] // YOSHIMITSUL STOP
\yoshimitsuL.envir.stopSynths
//:--[4.932683666] // YOSHIMITSUL grainfrothl8
'yoshimitsuL'.envir play: ( 'vol': [ 3.356638951877, "", 'off' ], 'endframe': 2451380, 'buf': 'weddell', 'amp': [ 1.0, "Xyz(1, 0.2, 0.1, 1.5) * 0.5", 'on' ], 
  'startframe': 0, 'playfunc': 'grainfrothl8', 'trate': [ 0.48412698412698, "\\z1.sr", 'on' ], 'pos': [ 0.079365079365079, "\\x1.sr", 'on' ] )
//:--[4.939823333] YOSHIMITSUL CONTROL
{Xyz(1, 0.2, 0.1, 1.5) * 0.5}@>.yoshimitsuL \amp
////:--[4.942495625]
{\x1.sr}@>.yoshimitsuL \pos
////:--[4.943716958]
{\z1.sr}@>.yoshimitsuL \trate
//:--[6.88114525]
\yoshimitsuR.envir.stopSynths
//:--[6.882109083]
'yoshimitsuR'.envir play: ( 'vol': [ 3.356638951877, "", 'off' ], 'endframe': 6750651, 'buf': 'wind_moderate', 'amp': [ 1.0, "Xyz(2, 0.2, 0.1, 1.5) * 0.5", 'on' ], 
  'startframe': 0, 'playfunc': 'grainfrothl8', 'trate': [ 0.48412698412698, "\\z2.sr", 'on' ], 'pos': [ 0.079365079365079, "\\x2.sr", 'on' ] )
//:--[6.889431791]
{Xyz(2, 0.2, 0.1, 1.5) * 0.5}@>.yoshimitsuR \amp
//:--[6.8922245]
{\x2.sr}@>.yoshimitsuR \pos
//:--[6.893516125]
{\z2.sr}@>.yoshimitsuR \trate
//:--[17.980995458]
\justineL.envir.stopSynths
//:--[17.982130958]
'justineL'.envir play: ( 'vol': [ 3.356638951877, "", 'off' ], 'endframe': 6750651, 'buf': 'swallows', 'amp': [ 1.0, "Xyz(5, 0.2, 0.1, 1.5) * 0.5", 'on' ], 
  'startframe': 0, 'playfunc': 'grainfrothl8', 'trate': [ 0.48412698412698, "\\z5.sr", 'on' ], 'pos': [ 0.079365079365079, "\\x5.sr", 'on' ] )
//:--[17.988661541]
{Xyz(5, 0.2, 0.1, 1.5) * 0.5}@>.justineL \amp
//:--[17.991503541]
{\x5.sr}@>.justineL \pos
//:--[17.992753583]
{\z5.sr}@>.justineL \trate
//:--[20.04656175]
\justineR.envir.stopSynths
//:--[20.047504041]
'justineR'.envir play: ( 'vol': [ 3.356638951877, "", 'off' ], 'endframe': 6750651, 'buf': 'noisebirdsclick', 'amp': [ 1.0, "Xyz(6, 0.2, 0.1, 1.5)", 'on' ], 
  'startframe': 0, 'playfunc': 'grainfrothl8', 'trate': [ 0.48412698412698, "\\z7.sr", 'on' ], 'pos': [ 0.079365079365079, "\\x7.sr", 'on' ] )
//:--[20.053010625]
{Xyz(6, 0.2, 0.1, 1.5)}@>.justineR \amp
//:--[20.055554833]
{\x7.sr}@>.justineR \pos
//:--[20.056745291]
{\z7.sr}@>.justineR \trate
//:--[24.665179083]
\yoshimitsuL.envir.stopSynths
//:--[24.666390291]
'yoshimitsuL'.envir play: ( 'vol': [ 3.356638951877, "", 'off' ], 'endframe': 6750651, 'buf': 'yang_bird', 'amp': [ 1.0, "Xyz(1, 0.2, 0.1, 1.5) * 0.5", 'on' ], 
  'startframe': 0, 'playfunc': 'grainfrothl8', 'trate': [ 0.48412698412698, "\\z1.sr", 'on' ], 'pos': [ 0.079365079365079, "\\x1.sr", 'on' ] )
//:--[24.674043666]
{Xyz(1, 0.2, 0.1, 1.5) * 0.5}@>.yoshimitsuL \amp
//:--[24.677021375]
{\x1.sr}@>.yoshimitsuL \pos
//:--[24.67830425]
{\z1.sr}@>.yoshimitsuL \trate
//:--[30.898154625]
\yoshimitsuR.envir.stopSynths
//:--[30.899210958]
'yoshimitsuR'.envir play: ( 'vol': [ 3.356638951877, "", 'off' ], 'endframe': 3105211, 'buf': 'yangdrums', 'amp': [ 1.0, "Xyz(2, 0.2, 0.1, 1.5) * 0.5", 'on' ], 
  'startframe': 0, 'playfunc': 'grainfrothl8', 'trate': [ 0.48412698412698, "\\z2.sr", 'on' ], 'pos': [ 0.079365079365079, "\\x2.sr", 'on' ] )
//:--[30.9078105]
{Xyz(2, 0.2, 0.1, 1.5) * 0.5}@>.yoshimitsuR \amp
//:--[30.910836916]
{\x2.sr}@>.yoshimitsuR \pos
//:--[30.912203]
{\z2.sr}@>.yoshimitsuR \trate
//:--[48.665947875]
\justineL.envir.stopSynths
//:--[48.666726083]
'justineL'.envir play: ( 'vol': [ 3.356638951877, "", 'off' ], 'endframe': 2451380, 'buf': 'crickets', 'amp': [ 1.0, "Xyz(5, 0.2, 0.1, 1.5) * 0.5", 'on' ], 
  'startframe': 0, 'playfunc': 'grainfrothl8', 'trate': [ 0.48412698412698, "\\z5.sr", 'on' ], 'pos': [ 0.079365079365079, "\\x5.sr", 'on' ] )
//:--[48.672033083]
{Xyz(5, 0.2, 0.1, 1.5) * 0.5}@>.justineL \amp
//:--[48.674647791]
{\x5.sr}@>.justineL \pos
//:--[48.675822125]
{\z5.sr}@>.justineL \trate
//:--[53.765236375]
\justineR.envir.stopSynths
//:--[53.766307958]
'justineR'.envir play: ( 'vol': [ 3.356638951877, "", 'off' ], 'endframe': 2451380, 'buf': 'fones', 'amp': [ 1.0, "Xyz(6, 0.2, 0.1, 1.5)", 'on' ], 
  'startframe': 0, 'playfunc': 'grainfrothl8', 'trate': [ 0.48412698412698, "\\z5.sr", 'on' ], 'pos': [ 0.079365079365079, "\\x5.sr", 'on' ] )
//:--[53.774448291]
{Xyz(6, 0.2, 0.1, 1.5)}@>.justineR \amp
//:--[53.777292833]
{\x5.sr}@>.justineR \pos
//:--[53.778531666]
{\z5.sr}@>.justineR \trate
//:--[61.466199166]
\yoshimitsuL.envir.stopSynths
//:--[61.467387416]
'yoshimitsuL'.envir play: ( 'vol': [ 3.356638951877, "", 'off' ], 'endframe': 2451380, 'buf': 'weddell', 'amp': [ 1.0, "Xyz(1, 0.2, 0.1, 1.5) * 0.5", 'on' ], 
  'startframe': 0, 'playfunc': 'grainfrothl8', 'trate': [ 0.48412698412698, "\\z1.sr", 'on' ], 'pos': [ 0.079365079365079, "\\x1.sr", 'on' ] )
//:--[61.474621833]
{Xyz(1, 0.2, 0.1, 1.5) * 0.5}@>.yoshimitsuL \amp
//:--[61.477400125]
{\x1.sr}@>.yoshimitsuL \pos
//:--[61.478739541]
{\z1.sr}@>.yoshimitsuL \trate
//:--[66.64935375]
\yoshimitsuR.envir.stopSynths
//:--[66.650356458]
'yoshimitsuR'.envir play: ( 'vol': [ 3.356638951877, "", 'off' ], 'endframe': 107520, 'buf': 'yang_bird', 'amp': [ 1.0, "Xyz(2, 0.2, 0.1, 1.5)", 'on' ], 
  'startframe': 0, 'playfunc': 'grainfrothl8', 'trate': [ 0.48412698412698, "\\z2.sr", 'on' ], 'pos': [ 0.079365079365079, "\\x2.sr", 'on' ] )
//:--[66.656561708]
{Xyz(2, 0.2, 0.1, 1.5)}@>.yoshimitsuR \amp
//:--[66.659211125]
{\x2.sr}@>.yoshimitsuR \pos
//:--[66.660504291]
{\z2.sr}@>.yoshimitsuR \trate
//:--[71.879256125]
\justineL.envir.stopSynths
//:--[73.131429125]
\justineR.envir.stopSynths
//:--[74.449004416]
\yoshimitsuL.envir.stopSynths
//:--[76.2665175]
\yoshimitsuR.envir.stopSynths

//the end

