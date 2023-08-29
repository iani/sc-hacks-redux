//multiple sines diverging
//:Target sound - diverging gliss cluster LFNoise2 100+- 20+- sec
25 +>.test \fadeTime;
{ SinOsc.ar(\freq.brdup(200, 800)).amix.amplify.pan } +> \test;
200 do: { | i |
	{
		LFNoise2.kr(10).range(800, XLine.kr(800, 800 - i + -0.1.rrand(0.1), 7.rrand(12)))
	}  @>.test (\freq +++ i);
}
//:Above cluster with wider range
10 +>.test \fadeTime;
{ SinOsc.ar(\freq.brdup(200, 800)).amix.amplify.pan } +> \test;
200 do: { | i |
	{
		LFNoise2.kr(10).range(800, XLine.kr(800, 800 - i - -0.rrand(300), 7.rrand(12)))
	}  @>.test (\freq +++ i);
}

//:Above cluster 50 sines with wider range
10 +>.test \fadeTime;
{ SinOsc.ar(\freq.brdup(50, 800)).amix.amplify.pan } +> \test;
50 do: { | i |
	{
		LFNoise2.kr(10).range(800, XLine.kr(800, 800 - i - -0.rrand(300), 7.rrand(12)))
	}  @>.test (\freq +++ i);
}

//:Above cluster 50 sines with wider range
10 +>.test \fadeTime;
{ SinOsc.ar(\freq.brdup(50, 800)).amix.amplify.pan } +> \test;
50 do: { | i |
	{
		LFNoise0.kr(10).range(800, XLine.kr(800, 800 - i - -0.rrand(300), 7.rrand(12)))
	}  @>.test (\freq +++ i);
}

//:Above cluster 50 sines with wider range
10 +>.test \fadeTime;
{ SinOsc.ar(\freq.brdup(50, 800)).amix.amplify.pan } +> \test;
50 do: { | i |
	{
		LFNoise0.kr(10).range(800, XLine.kr(800, 800 - i - -0.rrand(500), 7.rrand(12)))
	}  @>.test (\freq +++ i);
}

//:Building the target sound step by step. Step 1
{ SinOsc.ar(\freq.br(400)).amplify.pan } +> \test;
//:Step 2
{ SinOsc.ar({ \freq.br(400) } ! 2).amix.amplify.pan } +> \test;
//:Step 3
{ SinOsc.ar(\freq.brdup(2, 400)).amix.amplify.pan } +> \test;
//:Step 4 selectively modify freq controls:
401 @>.test \freq1;
//:selectively modify freq controls:
440 @>.test \freq0;
//:selectively modify freq controls - diverging cluster 20
{ SinOsc.ar(\freq.brdup(2, 800)).amix.amplify.pan } +> \test;
{ XLine.kr(800, 800 - 120, 20) }  @>.test \freq1;
//:diverging gliss cluster 100 20 sec
{ SinOsc.ar(\freq.brdup(100, 800)).amix.amplify.pan } +> \test;
100 do: { | i |
	{ XLine.kr(800, 800 - i, 20) }  @>.test (\freq +++ i);
}
//:diverging gliss cluster 100 10+- sec
{ SinOsc.ar(\freq.brdup(100, 800)).amix.amplify.pan } +> \test;
100 do: { | i |
	{ XLine.kr(800, (800 - i).postln, 8.rrand(12)) }  @>.test (\freq +++ i);
}
//:diverging gliss cluster 100+- 10+- sec
{ SinOsc.ar(\freq.brdup(100, 800)).amix.amplify.pan } +> \test;
100 do: { | i |
	{ XLine.kr(800, 800 - i + -0.1.rrand(0.1), 8.rrand(12)) }  @>.test (\freq +++ i);
}
//:diverging gliss cluster LFNoise2 100+- 20+- sec
{ SinOsc.ar(\freq.brdup(100, 800)).amix.amplify.pan } +> \test;
100 do: { | i |
	{
		LFNoise2.kr(1).range(800, XLine.kr(800, 800 - i + -0.1.rrand(0.1), 18.rrand(22)))
	}  @>.test (\freq +++ i);
}
//:testing fadetime 1
5 +>.test \fadeTime;
{ SinOsc.ar((50..100).choose.midicps, 0, 0.1).dup } +> \test;
//:testing fadetime 2
25 +>.test \fadeTime;
{ SinOsc.ar((50..100).choose.midicps, 0).amplify.pan } +> \test;
//:testing fadetime 2
25 +>.test \fadeTime;
{ SinOsc.ar(\freq.brdup(1, 800), 0).amix.amplify.pan } +> \test;
//:diverging gliss cluster LFNoise2 100+- 20+- sec
25 +>.test \fadeTime;
{ SinOsc.ar(\freq.brdup(200, 800)).amix.amplify.pan } +> \test;
200 do: { | i |
	{
		LFNoise2.kr(10).range(800, XLine.kr(800, 800 - i + -0.1.rrand(0.1), 7.rrand(12)))
	}  @>.test (\freq +++ i);
}
//:Cluster
{ XLine.kr(100, 100, 20) } @>.test \something;

\x respondsTo: \conc

0.001.exprand(100);