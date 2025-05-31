/*
left up keeping to same side
right down keeping to same side
rotate 90 degrees clockwise

rotate


same but

bottom right to top left
top right to bottom right


*/
~drawNecklace.(Ritem(5,32).())

0 0 1 0 0 0 1 0
0 0 1 0 0 0 1 0
0 0 0 0 1 1 0 0
0 0 0 0 1 1 0 0
0 0 1 0 0 0 0 1
0 0 1 0 0 0 0 1
0 0 1 0 0 0 1 0
0 0 1 0 0 0 1 0
0 0 1 1 0 0 0 0
0 0 1 1 0 0 0 0
0 0 0 1 0 0 1 0
0 0 0 1 0 0 1 0

a=[0,0,1,0,0,0,1,0]
(
~func = {arg arr, off;
	var old = arr.rotate(-2*off);
	var new = arr.rotate(-2*off);
	new[0]=old[4];
	new[1]=old[0];
	new[4]=old[1];
	new.rotate(2*off);
}
)
(
~temp = [0,0,1,0,0,0,1,0];
~r = Array.series(20,0,1).collect({arg i;
	~temp=~func.(~temp,i%4);
});
~chord = [0,2,3,4,6,7,8,10];
~c=~r.collect({ arg arr;
	arr.indicesOfEqual(1);
});
)
Pbind(\dur,0.5, \degree, Pseq(~c,inf)).play;