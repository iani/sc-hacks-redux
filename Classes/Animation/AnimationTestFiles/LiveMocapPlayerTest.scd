// 日 22  6 2025 15:33
OscMonitor.gui;
Animation.gui;

//:
l = LiveMocapPlayer(\test);

l.activate;

l.sendFromSynth = true;
l.sendFromSynth = false;


l.deactivate;
//:
a = LiveMocapPlayer(\test);
a.inspect;
//:
RokokoJoints.joints
// [ hip, spine, chest, neck, head, leftShoulder, leftUpperArm, leftLowerArm, leftHand, rightShoulder, rightUpperArm, rightLowerArm, rightHand, leftUpLeg, leftLeg, leftFoot, leftToe, leftToeEnd, rightUpLeg, rightLeg, rightFoot, rightToe, rightToeEnd ]
//
//:Build the LiveMocapPlayer synth.  Prototype:
a = {
	arg hipx = 0, hipy = 0, hipz = 0, hipqx = 0, hipqy = 0, hipqz = 0, hipqw = 0,
	spinex = 0, spiney = 0, spinez = 0, spineqx = 0, spineqy = 0, spineqz = 0, spineqw = 0,
	chestx = 0, chesty = 0, chestz = 0, chestqx = 0, chestqy = 0, chestqz = 0, chestqw = 0,
	neckx = 0, necky = 0, neckz = 0, neckqx = 0, neckqy = 0, neckqz = 0, neckqw = 0,
	headx=0, heady=0, headz=0, headqx=0, headqy=0, headqz=0, headqw=0,
	leftShoulderx=0, leftShouldery=0, leftShoulderz=0, leftShoulderqx=0, leftShoulderqy=0, leftShoulderqz=0, leftShoulderqw=0,
	leftUpperArmx=0, leftUpperArmy=0, leftUpperArmz=0, leftUpperArmqx=0, leftUpperArmqy=0, leftUpperArmqz=0, leftUpperArmqw=0,
	leftLowerArmx=0, leftLowerArmy=0, leftLowerArmz=0, leftLowerArmqx=0, leftLowerArmqy=0, leftLowerArmqz=0, leftLowerArmqw=0,
	leftHandx=0, leftHandy=0, leftHandz=0, leftHandqx=0, leftHandqy=0, leftHandqz=0, leftHandqw=0,
	rightShoulderx=0, rightShouldery=0, rightShoulderz=0, rightShoulderqx=0, rightShoulderqy=0, rightShoulderqz=0, rightShoulderqw=0,
	rightUpperArmx=0, rightUpperArmy=0, rightUpperArmz=0, rightUpperArmqx=0, rightUpperArmqy=0, rightUpperArmqz=0, rightUpperArmqw=0,
	rightLowerArmx=0, rightLowerArmy=0, rightLowerArmz=0, rightLowerArmqx=0, rightLowerArmqy=0, rightLowerArmqz=0, rightLowerArmqw=0,
	rightHandx=0, rightHandy=0, rightHandz=0, rightHandqx=0, rightHandqy=0, rightHandqz=0, rightHandqw=0,
	leftUpLegx=0, leftUpLegy=0, leftUpLegz=0, leftUpLegqx=0, leftUpLegqy=0, leftUpLegqz=0, leftUpLegqw=0,
	leftLegx=0, leftLegy=0, leftLegz=0, leftLegqx=0, leftLegqy=0, leftLegqz=0, leftLegqw=0,
	leftFootx=0, leftFooty=0, leftFootz=0, leftFootqx=0, leftFootqy=0, leftFootqz=0, leftFootqw=0,
	leftToex=0, leftToey=0, leftToez=0, leftToeqx=0, leftToeqy=0, leftToeqz=0, leftToeqw=0,
	leftToeEndx=0, leftToeEndy=0, leftToeEndz=0, leftToeEndqx=0, leftToeEndqy=0, leftToeEndqz=0, leftToeEndqw=0,
	rightUpLegx=0, rightUpLegy=0, rightUpLegz=0, rightUpLegqx=0, rightUpLegqy=0, rightUpLegqz=0, rightUpLegqw=0,
	rightFootx=0, rightFooty=0, rightFootz=0, rightFootqx=0, rightFootqy=0, rightFootqz=0, rightFootqw=0,
	rightToex=0, rightToey=0, rightToez=0, rightToeqx=0, rightToeqy=0, rightToeqz=0, rightToeqw=0,
	rightToeEndx=0, rightToeEndy=0, rightToeEndz=0, rightToeEndqx=0, rightToeEndqy=0, rightToeEndqz=0, rightToeEndqw=0;

	Out.kr(0, [
		hipx, hipy, hipz, hipqx, hipqy, hipqz, hipqw,
		spinex, spiney, spinez, spineqx, spineqy, spineqz, spineqw,
		chestx, chesty, chestz, chestqx, chestqy, chestqz, chestqw,
		neckx, necky, neckz, neckqx, neckqy, neckqz, neckqw,
		headx, heady, headz, headqx, headqy, headqz, headqw,
		leftShoulderx, leftShouldery, leftShoulderz, leftShoulderqx, leftShoulderqy, leftShoulderqz, leftShoulderqw,
		leftUpperArmx, leftUpperArmy, leftUpperArmz, leftUpperArmqx, leftUpperArmqy, leftUpperArmqz, leftUpperArmqw,
		leftLowerArmx, leftLowerArmy, leftLowerArmz, leftLowerArmqx, leftLowerArmqy, leftLowerArmqz, leftLowerArmqw,
		leftHandx, leftHandy, leftHandz, leftHandqx, leftHandqy, leftHandqz, leftHandqw,
		rightShoulderx, rightShouldery, rightShoulderz, rightShoulderqx, rightShoulderqy, rightShoulderqz, rightShoulderqw,
		rightUpperArmx, rightUpperArmy, rightUpperArmz, rightUpperArmqx, rightUpperArmqy, rightUpperArmqz, rightUpperArmqw,
		rightLowerArmx, rightLowerArmy, rightLowerArmz, rightLowerArmqx, rightLowerArmqy, rightLowerArmqz, rightLowerArmqw,
		rightHandx, rightHandy, rightHandz, rightHandqx, rightHandqy, rightHandqz, rightHandqw,
		leftUpLegx, leftUpLegy, leftUpLegz, leftUpLegqx, leftUpLegqy, leftUpLegqz, leftUpLegqw,
		leftLegx, leftLegy, leftLegz, leftLegqx, leftLegqy, leftLegqz, leftLegqw,
		leftFootx, leftFooty, leftFootz, leftFootqx, leftFootqy, leftFootqz, leftFootqw,
		leftToex, leftToey, leftToez, leftToeqx, leftToeqy, leftToeqz, leftToeqw,
		leftToeEndx, leftToeEndy, leftToeEndz, leftToeEndqx, leftToeEndqy, leftToeEndqz, leftToeEndqw,
		rightUpLegx, rightUpLegy, rightUpLegz, rightUpLegqx, rightUpLegqy, rightUpLegqz, rightUpLegqw,
		rightFootx, rightFooty, rightFootz, rightFootqx, rightFootqy, rightFootqz, rightFootqw,
		rightToex, rightToey, rightToez, rightToeqx, rightToeqy, rightToeqz, rightToeqw,
		rightToeEndx, rightToeEndy, rightToeEndz, rightToeEndqx, rightToeEndqy, rightToeEndqz, rightToeEndqw,
	]);
}.play;
//:
a;
a.set(\hipy, [0.15, 0.2]);
//:
o = [ '/rokoko/', 2.8506901264191, 'Baubo', 'hip', 0.2604533135891, 0.95789831876755, 0.73915308713913, 0.1527751237154, -0.062332402914762, -0.103251978755, 0.98087382316589, 'spine', 0.28295120596886, 1.0518680810928, 0.72791111469269, -0.063651286065578, -0.20704711973667, 0.96554124355316, 0.14425659179688, 'chest', 0.37736985087395, 1.277388215065, 0.88235527276993, 0.065359696745872, 0.31271806359291, -0.9206719994545, -0.22427368164062, 'neck', 0.45575818419456, 1.4256629943848, 1.0098406076431, -0.063545256853104, 0.25677707791328, -0.94512313604355, -0.19175393879414, 'head', 0.50087440013885, 1.5163974761963, 1.0624824762344, -0.19109027087688, 0.19534087181091, -0.94934773445129, -0.15513044595718, 'leftShoulder', 0.3550988137722, 1.3907344341278, 0.9779035449028, 0.21731689572334, 0.10006612539291, -0.86896270513535, 0.43320173025131, 'leftUpperArm', 0.24366959929466, 1.4753378629684, 0.97589194774628, 0.38582733273506, 0.69687074422836, 0.035345159471035, 0.60353881120682, 'leftLowerArm', 0.10934363305569, 1.2854721546173, 0.83616524934769, 0.12867201864719, 0.75961315631866, -0.060154616832733, 0.63467532396317, 'leftHand', 0.028127066791058, 0.99876034259796, 0.81467097997665, 0.13175198435783, 0.75775021314621, -0.052695661783218, 0.63692939281464, 'rightShoulder', 0.48549389839172, 1.3243054151535, 0.97498786449432, 0.22936011850834, -0.13146197795868, 0.52628839015961, 0.80816596746445, 'rightUpperArm', 0.61295771598816, 1.2766156196594, 0.94247716665268, -0.0072603048756719, -0.69006055593491, 0.030085748061538, 0.72308957576752, 'rightLowerArm', 0.62204366922379, 1.0058126449585, 0.95659196376801, 0.33093628287315, 0.65563732385635, -0.35275495052338, -0.57981461286545, 'rightHand', 0.61460888385773, 0.84684133529663, 1.2094445228577, 0.31127339601517, 0.67760026454926, -0.29181244969368, -0.59900975227356, 'leftUpLeg', 0.17869961261749, 0.97655779123306, 0.73151314258575, -0.042543340474367, -0.71653801202774, 0.093944430351257, -0.68988239765167, 'leftLeg', 0.093109108507633, 0.53702545166016, 0.76561218500137, 0.2473459392786, 0.67599582672119, 0.10880924761295, 0.68557286262512, 'leftFoot', 0.0091009363532066, 0.15276199579239, 0.54676026105881, -0.051064725965261, 0.86380392313004, -0.49884709715843, 0.048853494226933, 'leftToe', 0.023284709081054, 0.029134377837181, 0.63315659761429, -0.043512687087059, 0.78313434123993, -0.61782377958298, 0.055685874074697, 'leftToeEnd', 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 'rightUpLeg', 0.3422070145607, 0.93923884630203, 0.7467930316925, -0.1036654189229, -0.64438933134079, 0.13714444637299, 0.74512225389481, 'rightLeg', 0.37399184703827, 0.51669961214066, 0.89554607868195, 0.052564091980457, -0.65016853809357, -0.044137585908175, 0.75668334960938, 'rightFoot', 0.3753345310688, 0.070831298828125, 0.83380818367004, -0.11308985203505, 0.69147205352783, -0.69947040081024, -0.14077852666378, 'rightToe', 0.42134088277817, 0.0065839886665344, 0.96305596828461, -0.11308985203505, 0.69147205352783, -0.69947040081024, -0.14077852666378, 'rightToeEnd', 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 ];
//:Testing the algorithm as implemented:

RokokoJoints().osc2synth(o);
//:developing the algorithm ...
o.size;
//:
o = o[3..].clump(8).flop;
o;
o[1..];
p = o[1..].flop;
p.size;
p = p.flat;
p.size;
//:
r = RokokoJoints();
RokokoJoints.synthArgs;
RokokoJoints.synthArgs.size;
//:
[RokokoJoints.synthArgs, p].flop;
//:
OSC.add(\x, { | n, msg | "x received".postln; msg.postln; });
NetAddr.localAddr.sendMsg(\x, 1, 2, 3);
OSC.remove(\x);
