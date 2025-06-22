// 日 22  6 2025 15:33

a = LiveMocapPlayer(\test);
a.inspect;
//:
RokokoJoints.joints
// [ hip, spine, chest, neck, head, leftShoulder, leftUpperArm, leftLowerArm, leftHand, rightShoulder, rightUpperArm, rightLowerArm, rightHand, leftUpLeg, leftLeg, leftFoot, leftToe, leftToeEnd, rightUpLeg, rightLeg, rightFoot, rightToe, rightToeEnd ]
//
//:Build the LiveMocapPlayer synth.  Prototype:
a = {
	arg hip = 0, hipy = 0, hipz = 0, hipqx = 0, hipqy = 0, hipqz = 0, hipqw = 0,
	spine = 0, spiney = 0, spinez = 0, spineqx = 0, spineqy = 0, spineqz = 0, spineqw = 0,
	chest = 0, chesty = 0, chestz = 0, chestqx = 0, chestqy = 0, chestqz = 0, chestqw = 0,
	neck = 0, necky = 0, neckz = 0, neckqx = 0, neckqy = 0, neckqz = 0, neckqw = 0,
	head = 0,
	leftShoulder = 0,
	leftUpperArm = 0,
	leftLowerArm = 0,
	leftHand = 0,
	rightShoulder = 0,
	rightUpperArm = 0,
	rightLowerArm = 0,
	rightHand = 0,
	leftUpLeg = 0,
	leftLeg = 0,
	leftFoo = 0,
	leftToe = 0,
	leftToeEnd = 0,
	rightUpLeg = 0,
	rightLeg = 0,
	rightFoot = 0,
	rightToe = 0,
	rightToeEnd = 0;

	Out.kr(0, [
		hip, hipy, hipz, hipqx, hipqy, hipqz, hipqw,
		spine, spiney, spinez, spineqx, spineqy, spineqz, spineqw,
		chest, chesty, chestz, chestqx, chestqy, chestqz, chestqw,
		neck, necky, neckz, neckqx, neckqy, neckqz, neckqw,
		head, heady, headz, headqx, headqy, headqz, headqw,
		leftShoulder, leftShouldery, leftShoulderz, leftShoulderqx, leftShoulderqy, leftShoulderqz, leftShoulderqw,
		leftUpperArm, leftUpperArmy, leftUpperArmz, leftUpperArmqx, leftUpperArmqy, leftUpperArmqz, leftUpperArmqw,
		leftLowerArm, leftLowerArmy, leftLowerArmz, leftLowerArmqx, leftLowerArmqy, leftLowerArmqz, leftLowerArmqw,
		leftHand, leftHandy, leftHandz, leftHandqx, leftHandqy, leftHandqz, leftHandqw,
		spine, spiney, spinez, spineqx, spineqy, spineqz, spineqw,
		spine, spiney, spinez, spineqx, spineqy, spineqz, spineqw,

	]);
}.play;