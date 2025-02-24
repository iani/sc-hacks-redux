/* 23 Feb 2021 05:27
Prepare shortcut method qsetRange

aBuffer.qsetRange(array, startIndex = 0);

set consecutive values in the buffer to values from the array, starting at position startIndex.

For example: 

If buffer b (an empty buffer created with Buffer.alloc(Server.default, 8)) contains the elements [0, 0, 0, 0, 0, 0, 0, 0],
then b.qsetRange([3, 2, 10], 1) will send the array [3, 2, 10] the buffer, starting at index 1. As a result, the contents of buffer b will be:

[0, 3, 2, 10, 0, 0, 0, 0]

*/
//:model qsetRange as a function
~qsetRange = { | buffer, array, index = 0 |
	var indices, maxIndex;
	// create indices:
	maxIndex = index + array.size - 1 min: (buffer.numFrames - 1);
	indices = (index .. maxIndex);
	buffer.qsetn(*indices.collect({ | i | [i, array[i - index]] }).flat);
};

//:test qsetRange prototype
var buffer, array;
array = ["localhost"].flatten collect: { | c | c.ascii };
array.postln;
buffer = Buffer.alloc(Server.default, array.size, 1);
~qsetRange.(buffer, array, 5);
buffer.qplot(minval: array.minItem, maxval: array.maxItem);


//:================================================================
//counter-test to see when sync is required
/*
Here the Server.default.sync statement inside the fork delays the entire
thread of execution, so it is guaranteed that the setn and get and plot 
statements are executed in order after the buffer is allocated.

By contrast, the q methods (q, qsetn, qget, etc.) execute the corresponding
operation on the buffer independently from the main thread. This means
that all of them have to be used if not running in a thread with a sync statement
inserted after the Buffer was created.

*/
{
        var b;
        b = Buffer.alloc(Server.default, 2 ** 9, 1);
        Server.default.sync; // ensure that buffer exists before setting values
        b.setn(*[(0..(2**9-1)), Signal.sineFill(2**9, [1, 1], [0, pi])].flop.flat);
        b.get(5, { | val | postf("value at 5 was: %\n", val); });
        b.plot;
}.fork(AppClock); // AppClock required to enable plot. (Alternative: 'defer')
