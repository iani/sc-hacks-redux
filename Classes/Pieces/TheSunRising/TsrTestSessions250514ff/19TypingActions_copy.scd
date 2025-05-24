TypingActions {
	var <curr, <moving_diff, <moving_diff_scores, <tmp, <cnt;
	var <dist, <currIdx, <sumscores, <maxitem;
	var <verseIndex, <incipit, <verse;

	*new {
		^super.new.init;
	}

	init {
		this.onTyping;
	}

	onTyping {
		curr = Array.newClear(30);
		moving_diff = Array.newClear(30);
		moving_diff_scores = 0!30;
		moving_diff_scores  = moving_diff_scores.asArray;
		tmp = "";
		cnt = 0;

		Tsr.doOnType({ |ascii, cocoaModifiers, unicode, keycode, key|
			var char;
			// convert Ascii to Char
			char = ascii.asAscii;

			case
			{ unicode == 13 }{ // ascii code for Char.ret
				cnt = 0;
				tmp = "";
				curr = Array.newClear(30);
				moving_diff = Array.newClear(30);
				moving_diff_scores = 0!30;
			}
			{ (char.isAlpha || char.isPunct || char.isSpace) }{
				char.post;
				tmp = tmp ++ char.asString.last;
			};

			this.verseExraction;

			(instrument: \pinch, freq: char.ascii*2, ffreq: char.ascii / 2).play;
			(instrument: \pinch, freq: char.ascii*4, ffreq: char.ascii / 4).play;
		}, key: \gdmusic);
	}

	verseExraction {
		~verses do: { |v, idx|
			dist = v.editDistance(tmp);
			curr[idx] = curr[idx] ++ [dist];
		};
		curr do: { |v, idx|
			moving_diff[idx] = v.last / v[0];
		};
		if(cnt > 0){
			currIdx = moving_diff.removeNil.minIndex;
			moving_diff_scores[currIdx] = moving_diff_scores[currIdx] + 1;
		};
		cnt = cnt + 1;
		if(cnt > 10){
			maxitem = moving_diff_scores.maxItem;
			sumscores = moving_diff_scores.sum - maxitem;
			if(maxitem > sumscores){
				"\n>> VERSE: ".post; ~verses[moving_diff_scores.maxIndex].postln;
				verseIndex = moving_diff_scores.maxIndex;
				verse = ~verses[moving_diff_scores.maxIndex];
				incipit = ~incipits[moving_diff_scores.maxIndex];
				Tsr.verse(verseIndex, verse, incipit);
			};
		};
	}
}