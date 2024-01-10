/* 30 Sep 2021 12:11

makaronisti

kaminastori
staminokari
nikamaristo
nostikamira
kiminastora

*/
//: Ελληνικά ...
var pre, post, set;
pre = ["μ", "ν", "στ", "ρ", "κ"];
post = ["ο", "α", "ι", "α", "ι"];
set = IdentitySet();
{ | i |
	{ | j |
		set add: "".catList([pre.permute(i), post.permute(j)].flop.flat).asSymbol;
	} ! 120;
} ! 120;
set do: { | t | postf("%.\n", t); };
set.size;
// set.asArray.sort.reverse do: _.postln;
//: Ελληνικά ...
var vowels, consonants, allPermutations, vowelPermutations;
consonants = ["μ", "ν", "στ", "ρ", "κ"];
vowels = ["ο", "α", "ι", "α", "ι"];
allPermutations = [];
vowelPermutations = [];
{ | i |
	var vowelPermutation, vowelPermutationSymbol;
	vowelPermutation = vowels.permute(i);
	vowelPermutationSymbol = "".catList(vowelPermutation).asSymbol;
	if (vowelPermutations includes: vowelPermutationSymbol) {
		postf("Permutation nr. %, % already included. skipping ...\n", i,
			vowelPermutationSymbol);
	}{
		postf("Collecting permutations with nr. % : %\n", i,
			vowelPermutationSymbol);
		vowelPermutations = vowelPermutations add: vowelPermutationSymbol;
		{ | j |
			allPermutations = allPermutations add:
			"".catList([consonants.permute(j), vowelPermutation].flop.flat).asSymbol;
		} ! 120;
	};
} ! 120;
allPermutations.clump(120) do: { | c |
	postf("\n\n================= % =================\n", c.first);
		c do: { | m | postf("%.\n", m); }
};
"------------------------------------------------------------".postln;
"------------------------------------------------------------".postln;
"------------------------------------------------------------".postln;
vowelPermutations.clump(8) do: _.postln;
allPermutations.size;
// set.asArray.sort.reverse do: _.postln;
