//:snippet1
//simple snippet
10 do: { | i | postf("counting! : %\n", i); };

//:+
10 d: { | i |
	postf("counting! : %\n", i);
	0.25.wait;
}

//:*myloopno1
postf("random! : %\n", 100.rand);
0.25.wait;

//:
// about message syntax:
// standard form is:
<receiver>.<message>(<arguments>...);

// binary operator syntax is:
<receiver> <message>: <single argument>;


//: