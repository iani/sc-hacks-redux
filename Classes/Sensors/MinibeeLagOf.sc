/* 28 Aug 2023 10:17
Send sensor input from minibee to openframeworks.

- Send the x, y, z tuplet each individual sensor separately with the id number
- Smooth the value changes of x, y, z by applying lag.kr(0.5) on the inputs from the busses.
- Only send a tuple when any one of the 3 values x, y, z has changed.
*/