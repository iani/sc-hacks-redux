//: 日 22  6 2025 15:28
//Adapt AnimationPlayer to work with live OSC data from
//Rokoko suit (instead of sound-file playback).

LiveMocapPlayer : AnimationPlayer {
	init {
		this.makeController;
	}

	makeController {
		controller = AnimationController(name);
		controller.player = this;
	}

	activate {
		this.makeSynth;
		CmdPeriod add: this;
		OSC.add('/rokoko/', { | n, msg |
			if (synth.isPlaying) {
				synth.set(*controller.oscMaker.osc2synth(msg));
				this.changed(\msg, [msg]);
			}
		});
	}

	addListener { | addr |
		addr.asSymbol.addNotifier(this, \msg, { | n, msg |
			// postln("Sending to addr" + addr);
			// postln("Message:" + msg);
			addr.sendMsg(*msg);
		})
	}

	postOsc {
		\posting.addNotifier(this, \msg, { | n, msg |
			postln("I will send" + msg);
		})
	}

	unpostOsc {
		\posting.removeNotifier(this, \msg)
	}

	removeListener { | addr |
		addr.asSymbol.removeNotifier(this, \msg);
	}

	doOnCmdPeriod { this.makeSynth;	}

	deactivate {
		OSC.remove(\x);
		synth.free;
		CmdPeriod remove: this;
	}

	makeSynth {
		synth = {
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

			Out.kr(controller.databus.index, [
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
		// enable tracking if synth has started:
		synth onStart: { postln("Started forwarding osc to animation" + this) };
	}
}