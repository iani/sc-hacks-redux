//Building function pipelines for filtering
//rokoko playback data
// ----- Examples -----
//: Example 1

var funcs, input, result;

funcs = [
    { |x| x + 1 },
    { |x| x * 2 },
    { |x| x.asString }
];

input = 3;

result = funcs.fold(input, { |val, func| func.value(val) });
result.postln; // "8"

//: Example 2
(
~pipe = { |val, funcs|
    funcs.fold(val, { |v, f| f.value(v) });
};

var result = ~pipe.(3, [
    { |x| x + 1 },
    { |x| x * 2 },
    { |x| x.asString }
]);

result.postln; // "8"
);
//: Trying out method 2:
var result;
~pipe = { |val, funcs|
    funcs.fold(val, { |v, f| f.value(v) });
};

result = ~pipe.(3, [
    { |x| x + 1 },
    { |x| x * 2 },
    { |x| x.asString }
]);

result.postln; // "8"
//: New approach, as the above do not work.
f = { | ... funcs |
    { | input |
        funcs do: { | func | input = func.(input) };
        input
    }
};
//:
g = f.value(
    { | i | i.asString ++"_I*" },
    { | i | i.asString ++"+J!" },
);
//:
g.("alpha");