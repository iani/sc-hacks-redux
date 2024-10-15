//Wed  7 Feb 2024 20:53
//a cycle is subdivided into an array of fractions.
//we can start with a simple representation:
[1, 2]
// or rather
[1, 1, 0]
// each element within the subdivision has the same duration,
// but a 0 means that there is no beat for that subdivision.
// so [1, 1, 0] produces 2 beats of relative duration [1, 2]
// so the number of elements in the array automatically gives us the
// duration of a single element:
// [1, 1] -> 1/2, 1/2
// [1, 1, 1] -> 1/3, 1/3, 1/3
// [1, 0, 1] -> 2/3, 1/3
// but then an element can itself be an array of subdivisions:
// these are notated in the same way as the top level, but the duration
// of the beats becomes a fraction of the total duration of the subdivided part.
// simple case
[[1, 1], 1]
// becomes:
[[1/4, 1/4], 1/2]
// question: can there be subdivisions (nestings) spanning several units?
// i.e. is this allowed:

| 1 + 1 + 1 | = 3
|---|---|---|
  2/3 + 2/3 + 2/3 + 1 = 3
|--|--|-|---|

i.e. subdvide the first 2 beats of a 3/3 bar by 3.

This could be indicated by an extra element attached in some way to
the subdivision, to indicate the total number of beats that it should last.
Something like:

[[1, 1, 1]:2, 1];

We'll definitely need a way to notate this compactly.
If