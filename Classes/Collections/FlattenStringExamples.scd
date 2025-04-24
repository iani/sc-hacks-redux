//:木 24  4 2025 15:30
//: Per default, ...
/*

Ο νοών νοείτω …..
分かる者は分かれ...

If you want the method =flat= to behave like in classic SC Class library, in other words to flatten strings into character arrays like this:

=["abc", "def"].flat=

makes:

=[ $a, $b, $c, $d, $e, $f ]=

and (example 2)

=["abc", "def", ["xyz", 123]].flat=

makes:

=[ $a, $b, $c, $d, $e, $f, $x, $y, $z, 123 ]=


then you should put this in your startup.scd file:

=FlattenString.yes;=

Otherwise, per default this library will *not* flatten strings into characters. In other words:

=["abc", "def"].flat=

makes:

=[ "abc", "def" ]=

and (example 2)

=["abc", "def", ["xyz", 123]].flat=

makes:

[ "abc", "def", "xyz", 123 ]

Ο νοών νοείτω …..
分かる者は分かれ...

*/
//: EXAMPLES:
FlattenString.mode; // per default: false.  Meaning DO NOT FLATTEN STRINGS
//:
FlattenString.no;
"abcde".flat;
["abcde"].flat;
["abcde", "123"].flat;
["x", 1, ["abcde", ["123", 4, [5, "pq"]]], "yz"].flat;
["x", 1, ["abcde", ["123", 4, [5, "pq"]]], "yz"].flat.asCompileString;
//:
FlattenString.yes;
"abcde".flat;
["abcde"].flat;
["abcde", "123"].flat;
["x", 1, ["abcde", "123"]].flat;
["x", 1, ["abcde", ["123", 4, [5]]]].flat;
["x", 1, ["abcde", "123"]].flat.asCompileString;
["x", 1, ["abcde", ["123", 4, [5, "pq"]]], "yz"].flat.asCompileString;
//:

["abc", "def"].flat.asCompileString;


["abc", "def", ["xyz", 123]].flat.asCompileString;