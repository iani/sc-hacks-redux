/*  6 Feb 2021 17:23
How to evaluate a Routine explicitly in a chosen currentEnviroment?
*/
//:
currentEnvironment.postln;
currentEnvironment[\test] = 1;
{
	10 do: { currentEnvironment.postln; 1.wait }
}.fork;
//:
(\key: 100000) use: {
{
	10 do: { currentEnvironment.postln; 1.wait }
}.fork
}
//:
~forMethod = { | func, key = \default |
	currentEnvironment use: {
		currentEnvironment[key] = Routine(func).play(AppClock) 
	}
};
//:
~forMethod.({
	{ currentEnvironment.postln; 1.wait} ! 10;
})
