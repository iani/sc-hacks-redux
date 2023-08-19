/*  1 Jul 2023 12:02

*/

SoundBufferGui {
	// classvar <>players;
	classvar colors;
	var <name;
	var <path;
	var <buffer, <sfv;
	// Restore selections when switching buffers:
	var <selectionDict; // selections for all buffers.
	var <>selections; // selections instance for current buffer
	// var <playfuncs; // TODO: transfer these from EditSoundPlayer. Use a different directory
	var <playfunc = \phasebuf; // name of playfunc selected from menu
	var <zoomfrac = 1, scrollpos = 0;
	var <timestamp; //used as id for updating stats
	// var <>player;
	// to load them.
	*initClass {
		// TODO: Rewrite this to use own playfuncs from sc-hacks repository,
		// and if found, also extra funcs from sc-project.
		// StartUp add: {}
	}

	*openOnProjectBufferLoad {
		this.addNotifier(Project, \loadedBuffers, {this.new(\default).gui})
	}

	*default { ^this.new(\default) }

	*fromFile { | path |
		// path.postln;
		^this.new(PathName(path).fileNameWithoutExtension);
	}
	*new { | name = \default |
		name = name.asSymbol;
		 ^Registry(this, name.asSymbol, { this.newCopyArgs(name).init })
	}

	colors { ^this.class.colors }
	*colors {
		colors !? { ^colors };
		colors = (((1..9).normalize * (2/3) + (1/3)).collect({ | i |
			[
				Color(0, i, 0), Color(0, 0, i),
				Color(i, i, 0), Color(i, 0, i), Color(0, i, i), Color(i, 0, 0), Color(i, i, i)
			] // last selection reserved for deactivation. Color = black.
		}).flat.reverse add: (Color.black));
		colors[0] = Color(0.95, 0.95, 0.5);
		^colors;
	}

	init {
		this.colors; // lazy init;
		timestamp = Date.getDate.stamp;
		selectionDict = ();
		buffer = Buffer.all.first.buf;
		Buffer.all do: { | b |
			selectionDict[b] = SfSelections(this, b.buf);
		};
		selections = selectionDict[buffer.name];

		// decorative detail:
		ServerQuit add: {
			this.save;
			this.changed(\closeGui);
			this.objectClosed;
		};
	}

	buffer_ { | argBuffer |
		var bufname;
		postln("changing from buffer" + buffer.name + "to buffer" + argBuffer.buf.name);
		if (buffer.name == argBuffer.buf.name) {
			postln("I will skip changing to the same buffer!");
		}{
			postln("changing to the new buffer now");
			bufname = argBuffer;
			buffer = argBuffer.buf;
			// "CHECKING before updating selections from sfv!!!!!!!".postln;
			// postln("the selections for buffer" + bufname + "are\n" + selectionDict[bufname].selections);
			// selections.getSelectionsFromSfv(sfv);
			selections = selectionDict[bufname];
			// "CHECKING AFTER updating selections from sfv!!!!!!!".postln;
			// postln("the selections for buffer" + bufname + "are\n" + selectionDict[bufname].selections);
			// postln("the LOCAL selections for buffer" + buffer.name + "are\n" + selections.selections);
			sfv.soundfile_(SoundFile(buffer.path))
			.readWithTask(0, buffer.numFrames, {
				// "RESTORING SELEXTIONS after read with task".postln;
				// selections.selections.postln;
				// selections.restoreSelectionsToSfv(sfv);
				// this.setSelection(0)
			});
			{   // "restoring again".postln;
				selections.restoreSelectionsToSfv(sfv);
				// sfv.setSelection(0, [ 1171670, 376275 ]);
			}.defer(0.05);
			// selections = SfSelections(this);
			this.changed(\selection);
		}
	}

	*gui { ^this.default.gui }

	gui {
		this.bl_(1400, 400).hlayout(
			VLayout(
				sfv = this.sfView,
				this.rangeSlider,
				this.posDisplay(sfv)
			),
			this.selectionListView,
			this.selectionEditedView
		).name_(name)
		.addNotifier(this, \closeGui, { | n | n.listener.close; });
		{ selections.restoreSelectionsToSfv(sfv); }.defer(0.05);
		// "SoundBufferGui buffer is:".postln;
		// { buffer.name.postln; } ! 5;
		// "SoundBufferGui's current selection buffer is:".postln;

		// { selections.buffer.name.postln; } ! 5;
	}

	// basic selection actions to use throughout for selection management
	switchToNewSelection { | index | // (NEW)
		// User switched to a new selection.
		// Called by view elements that choose selection index.
		// Tell the view + selection to switch selection index.
		sfv.currentSelection = index;
		selections.setCurrentSelectionIndex(index);
	}

	// prevent modification of useable selectios by mouselicks,
	// by setting the current selection index to 63 (unused by this app).
	divertSelection { sfv.currentSelection = 63; }

	zeroSelection {
		sfv.setSelection(sfv.currentSelection, [0, 0]);
		this.changed(\selection);
	}

	selectionFrac {
		selections.currentSelection;
		^(selections.currentSelection[1] / buffer.numFrames);
	}

	selectionBegFrac {
		selections.currentSelection;
		^(selections.currentSelection[0] / buffer.numFrames);
	}

	sfView {
		sfv = SoundFileView()
		.background_(Color(0.25, 0.1, 0.1))
		.soundfile_(SoundFile(buffer.path))
		.readWithTask(0, buffer.numFrames, { this.setSelection(0) })
		.timeCursorOn_(true)
		.keyDownAction_({ | me, char ... args |
			switch(char,
				$-, { this.clearSelection; },
				$=, { this.maximizeCurrentSelection },
				$z, { this.toggleSelectionZoom }, // TO BE REMOVED?
				// $a, { this.maximizeCurrentSelection },
				$c, { this.clearSelection },
				$p, { this.play },
				$., { this.stop },
				$ , { this.togglePlay; },
				$e, { this.edit },
				$1, { this.moveSelectionStartBy(1); },
				$2, { this.moveSelectionStartBy(10); },
				$3, { this.moveSelectionStartBy(100); },
				$4, { this.moveSelectionStartBy(1000); },
				$5, { this.moveSelectionStartBy(10000); },
				$6, { this.moveSelectionStartBy(-10000); },
				$7, { this.moveSelectionStartBy(-1000); },
				$8, { this.moveSelectionStartBy(-100); },
				$9, { this.moveSelectionStartBy(-10); },
				$0, { this.moveSelectionStartBy(-1); },
				$q, { this.moveSelectionDurBy(1); },
				$w, { this.moveSelectionDurBy(10); },
				$e, { this.moveSelectionDurBy(100); },
				$r, { this.moveSelectionDurBy(1000); },
				$t, { this.moveSelectionDurBy(10000); },
				$p, { this.moveSelectionDurBy(-1); },
				$o, { this.moveSelectionDurBy(-10); },
				$i, { this.moveSelectionDurBy(-100); },
				$u, { this.moveSelectionDurBy(-1000); },
				$y, { this.moveSelectionDurBy(-10000); },
				$a, { this.moveSelectionBy(1); },
				$s, { this.moveSelectionBy(10); },
				$d, { this.moveSelectionBy(100); },
				$f, { this.moveSelectionBy(1000); },
				$g, { this.moveSelectionBy(10000); },
				$h, { this.moveSelectionBy(-10000); },
				$j, { this.moveSelectionBy(-1000); },
				$k, { this.moveSelectionBy(-100); },
				$l, { this.moveSelectionBy(-10); },
				$;, { this.moveSelectionBy(-1); },

				$>, { sfv.scroll(1/10);  this.changed(\zoom) },
				$<, { sfv.scroll(-1/10);  this.changed(\zoom) },
				$R, { this.focusRangeSlider },
				$Z, { this.toggleSelectionZoom }
			);
		})
		.mouseUpAction_({ |view, x, y, mod| //
			// "MOUSEUP ACTION ".post;
			// sfv.currentSelection.post; " -- ".post;
			// sfv.selection(sfv.currentSelection).postln;
			// store selection range in selections, and send to sound (if playing);
			// "mouseUpAction skipped the next line. restore it".postln;
			// // selections.setSelectionFromGui(
			// 	sfv.currentSelection,
			// 	sfv.selection(sfv.currentSelection));
			// this.sendSelectionToServer; // OBSOLETE. selections does this.
			// Prevent zeroing of current selection by next click:
			// "mouseUpAction skipped the next line. restore it".postln;
			this.divertSelection; // this does not send data to selections!
		})
		.action_({ | me | // Runs both on mouseclick amd on mousedrag.
			// Mouseclick immediately changes the current selection.
			// To avoid zeroing the current selection inadvertedly,
			// Change the current selection to 63 as whenever the size is < 100.
			if (sfv.selectionSize(sfv.currentSelection) < 100) {
				// Mouse will modify the selection which is not used in this app.
					// "sfv action skipped the next line. restore it".postln;
				this.divertSelection;
				// Method above is equivalent to:
				// sfv.currentSelection = 63; // divert to last selection
			}{  // when range dragged is > 100, set currentSelection index to the last
				// selection chosen by the user.
					// "sfv action skipped the next line. restore it".postln;
				// postln("selection index" + sfv.currentSelection
				// 	+ "selection size:" + sfv.selectionSize(sfv.currentSelection));
				// Note: the first time >= 100 there will be a glitch/inaccurate
				// frames left from null selection. But the effect
				// when changing the loop dimensions live is negligible.

				sfv.currentSelection = selections.currentSelectionIndex;
				// postln("frames after changing to current selection" +
				// 	sfv.selection(sfv.currentSelection)
				// );
				selections.setSelectionFromGui(
					sfv.currentSelection,
					sfv.selection(sfv.currentSelection)
				);
				this.changed(\selection);
			};
		})
		.addNotifier(this, \selection, { | n |
			sfv.currentSelection = selections.currentSelectionIndex;
			this.restoreSelectionValuesFromSelections;
		})
		.addNotifier(this, \selectionIndex, { | n |
			postln("updating sfv selection index to" + selections.currentSelectionIndex);
			sfv.currentSelection = selections.currentSelectionIndex;
		})
		.addNotifier(this, \focusSoundFileView, { | n |
			"Sound File View will focus!".postln;
			n.listener focus: true;
		});
		colors do: { | c, i | sfv.setSelectionColor(i, c) };
		^sfv;
	}

	posDisplay { // | sfv |
		^HLayout(
			Button().maxWidth_(10).states_([["x"]])
			.action_({ CmdPeriod.run }),
			Button().maxWidth_(10).states_([["x", Color.yellow, Color.red]])
			.action_({ "CmdPeriod.run".share }),
			Button().maxWidth_(50).states_([["stats"]])
			.action_({ this.stats }),
			StaticText().string_("selection")
			.addNotifier(this, \selection, { | n |
				var index;
				index = this.selectionIndex;
				n.listener.background = colors[index];
				n.listener.stringColor = colors[index].complement;
			}),
			NumberBox()
			.maxWidth_(40)
			.decimals_(0)
			.clipHi_(63)
			.clipLo_(0)
			.action_({ | me | this.setSelectionIndex(me.value.asInteger); })
			.addNotifier(this, \selection, { | n | n.listener.value = this.selectionIndex; }),
			StaticText().string_("begin"),
			NumberBox()
			.maxDecimals_(6)
			.addNotifier(this, \selection, { | n | n.listener.value = this.selectionStart; }),
			StaticText().string_("end"),
			NumberBox()
			.maxDecimals_(6)
			.addNotifier(this, \selection, { | n | n.listener.value = this.selectionEnd; }),
			StaticText().string_("dur"),
			NumberBox()
			.maxDecimals_(6)
			.addNotifier(this, \selection, { | n | n.listener.value = this.selectionDur;}),
			Button()
			.states_([["controls", Color.white, Color.red]])
			.action_({ this.openParameterGui }),
			StaticText().string_("buffer:"),
			Button()
			.canFocus_(false)
			.states_([[buffer.name, Color.blue, Color.white]])
			.addNotifier(this, \selection, { | n |
				n.listener.states_([[buffer.name, Color.blue, Color.white]]);
			})
			.action_({ | me | Menu(
				*Buffer.all
				.collect({ | f | MenuAction(f.asString, {
					me.states_([[f.asString, Color.blue, Color.white]]);
					this.buffer = f.asSymbol;
					this.changed(\buffer);
				})})
			).front }),
			StaticText().string_("playfunc:"),
			Button()
			.canFocus_(false)
			.states_([["phasebuf", Color.red, Color.white]])
			.action_({ | me | Menu(
				*BufferSynth.playfuncs.keys.asArray.sort
				.collect({ | f | MenuAction(f.asString, {
					me.states_([[f.asString, Color.red, Color.white]]);
					this.setPlayfunc(f.asSymbol);
				})})
			).front }),
			Button().states_([["clone"]]) .action_({ this.cloneSelections }),
			Button().states_([["save"]]) .action_({ this.save })
		)
	}

	setPlayfunc { | argPlayfunc |
		playfunc = argPlayfunc;
		selections.setPlayfunc(playfunc);
	}

	selectionIndex { ^selections.currentSelectionIndex }
	selectionStart { ^this.startFrame / buffer.sampleRate; }
	selectionEnd { ^this.endFrame / this.sampleRate;}
	selectionDur { ^this.numFrames / this.sampleRate; }
	startFrame { ^selections.startFrame; }
	endFrame { ^selections.endFrame; }
	numFrames { ^selections.numFrames }
	sampleRate { ^buffer.sampleRate }

	selectionListView {
		^ListView().maxWidth_(30)
		.items_((0..63))
		.colors_(colors)
		.action_({ | me |
			me.hiliteColor = colors[me.value];
			me.selectedStringColor_(colors[me.value].complement);
			this.setSelectionIndex(me.value.asInteger);
		})
		.addNotifier(this, \selection, { | n |
			// postln("listener" + n.listener + "value:" + n.listener.value);
			n.listener.value = this.selectionIndex;
			n.listener.hiliteColor = colors[n.listener.value];
			n.listener.selectedStringColor_(colors[n.listener.value].complement);
		})
		.keyDownAction_({ | me, char ... args |
			switch(char.ascii,
				122, { this.toggleSelectionZoom },//z
				127, { this.zeroSelection },// delete
			);
		})
	}

	selectionEditedView {
		^VLayout(
			NumberBox().maxWidth_(20)
			.decimals_(0)
			.clipHi_(63)
			.clipLo_(0)
			.action_({ | me | this.setSelectionIndex(me.value.asInteger); })
			.addNotifier(this, \selection, { | n | n.listener.value = this.selectionIndex; }),
			ListView().maxWidth_(20)
			.hiliteColor_(Color.white)
			.selectedStringColor_(Color.red)
			.action_({ | me | this.setSelectionIndex(me.value.asInteger); })
			.addNotifier(this, \selection, { | n, index |
				var theindex;
				n.listener.items = selections.edited;
				theindex = n.listener.items indexOf: selections.currentSelectionIndex;
				theindex !?  { n.listener.value = theindex };
			})
		)
	}

	focusRangeSlider {
		this.changed(\focusRangeSlider)
	}

	edit {
		"edit not yet implemented".postln;
		// BufCode(this).makeDoc;
	}

	start { this.play }
	play { // TODO: rewrite this. SfSelections should play the current selection
		// SfSelections.playCurrentSelection;
		// "SoundBufferGui:play should be changed! It should not use @@".postln;
		if (this.selectionDur == 0) {
			^postln("refusing to play selection" + selections.currentSelectionIndex
				+ "because its duration is 0");
		};
		postln("playing selection" + selections.currentSelectionIndex + "of duration"
		+ this.selectionDur + "with" + playfunc);
		buffer.name.perform(
			'@@',
			(
				startpos: this.selectionStart,
				dur: this.selectionDur,
				startframe: this.startFrame, // for frame-based players like PhaseBuf_
				endframe: this.endFrame, // for frame-based players like PhaseBuf_
			),
			(playfunc ?? { \phasebuf }));
	}
	stop {
		buffer.name.envir.stopSynths;
	}

	togglePlay {
		if (this.isPlaying) { this.stop } { this.start };
	}

	isPlaying {
		^buffer.name.envir[buffer.name].isPlaying;
	}

	maximizeCurrentSelection {// set current selection to full size of buffer
		// set the range of current selection to 0-full buffer range
		// the current selection is taken from selections, not from sfv,
		// because sfv has "null" selection 63 after mouseUp.
		// var restore;
		// restore = this.currentSelection; ///????
		sfv.setSelection(selections.currentSelectionIndex, [0, buffer.numFrames]);
		selections.setSelectionFromGui(
			selections.currentSelectionIndex, [0, buffer.numFrames]
		);
		this.changed(\selection);
	}

	clearCurrentSelection { // TODO: Rework and check this.
		// var restore;
		// restore = this.currentSelection;
		// sfv.setSelection(selections.currentSelectionIndex, [0, 0]);
		// this.changed(\selection);
		// sfv.currentSelection = restore;
		// this.changed(\selection);
		selections.setSelectionFromGui(selections.currentSelectionIndex, [0, 0]);
		this.changed(\selection);
	}

	// EXPERIMENTAL _ IMPORTANT _ CHECK!
	setSelectionIndex { | index | selections.setCurrentSelectionIndex(index) }

	// setSelection { | beg, duration | // TODO: implement this
	// 	// set frames of current selection to beginning and end
	// 	// test version:
	// 	selections.setCurrentSelectionValues(beg, duration);
	// 	this.getSelection;
	// }

	performOnSelection { | method, frames | // used by methods below.
		// modify selections' selection by applying method with frames,
		// set sfv selection to it, and divert.
		selections.perform(method, frames);
		this.getSelection;
		this.changed(\selection);
	}
	getSelection {
		// set sfv views current selection index + values from selecgtions
		// divert selection to null (63)
		// cause update of views
		this.restoreSelectionValuesFromSelections;
		this.divertSelection;
	}

	moveSelectionStartBy { | frames | // move start only. dur changes
		this.performOnSelection(\moveSelectionStartBy, frames);
	}

	moveSelectionDurBy { | frames | // move dur only
		this.performOnSelection(\moveSelectionDurBy, frames);
	}
	moveSelectionBy { | frames | // move start, keeping dur as is
		this.performOnSelection(\moveSelectionBy, frames);
	}

	resizeSelectionBy { | frames | // move start + end by equal amounts
		this.performOnSelection(\resizeSelectionBy, frames);
	}

	currentSelection { ^selections.currentSelectionIndex }

	restoreSelectionIndex {
		sfv.currentSelection = this.currentSelection;
		this.changed(\selection);
	}

	restoreSelectionValuesFromSelections {
		sfv.setSelection(selections.currentSelectionIndex,
			selections.selections[selections.currentSelectionIndex]
		)
	}

	toggleSelectionZoom {
		if (this.isZoomedOut) {
			this.zoomSelection;
		}{
			this.zoomOut;
		};
		this.changed(\zoom);
	}

	isZoomedOut {
		^sfv.xZoom.round(0.00001) == buffer.dur.round(0.00001);
	}

	zoomSelection {
		{
			sfv.zoomSelection(selections.currentSelectionIndex);
			sfv.setSelection(selections.currentSelectionIndex, selections.currentSelection);
			this.divertSelection;
			this.changed(\zoom);
		}.fork(AppClock);
	}

	zoomOut {
		sfv.zoomToFrac(1);
		sfv.setSelection(selections.currentSelectionIndex, selections.currentSelection);
		this.restoreSelectionIndex;
		this.divertSelection;
		this.changed(\zoom);
	}

	rangeSlider {
		^RangeSlider().orientation_(\horizontal)
		.palette_(QPalette.dark)
		.knobColor_(Color.green)
		.addNotifier(this, \zoom, { | n |
			var offsetRatio, scrollRatio, scrollPos;
			// postln("updating zoom view");
			scrollPos = sfv.scrollPos;
			scrollRatio = sfv.viewFrames / buffer.numFrames;
			offsetRatio = scrollRatio * scrollPos;
			// postln("scrollRatio" + scrollRatio + "scrollPos" + scrollPos);
			n.listener.lo = 1 - scrollRatio * scrollPos;
			n.listener.hi = 1 - scrollRatio * scrollPos + scrollRatio;
		})
		.mouseUpAction_({ |view, x, y, mod| //
			// this.sendSelectionToServer;
			this.divertSelection;
		})
		.focusColor_(Color.red)
		.keyDownAction_({ | me, char |
			switch(char,
				$s, { // set current selection to zoom
					this.restoreSelectionIndex;
					selections.setCurrentSelectionValues(
						(me.lo * buffer.numFrames).asInteger,
						sfv.viewFrames
					);
					this.restoreSelectionValuesFromSelections;
					this.divertSelection;
				},
				$z, { this.focusSoundFileView; },
				$1, { this.zoomToFrac(zoomfrac - 0.0001); },
				$2, { this.zoomToFrac(zoomfrac - 0.001); },
				$3, { this.zoomToFrac(zoomfrac - 0.01); },
				$4, { this.zoomToFrac(zoomfrac - 0.1); },
				$5, { this.zoomToFrac(1); },
				$6, { this.toggleSelectionZoom; },
				$7, { this.zoomToFrac(zoomfrac + 0.1); },
				$8, { this.zoomToFrac(zoomfrac + 0.01); },
				$9, { this.zoomToFrac(zoomfrac + 0.001); },
				$0, { this.zoomToFrac(zoomfrac + 0.0001); },
				$q, { this.scrollTo(scrollpos - 0.0001); },
				$w, { this.scrollTo(scrollpos - 0.001); },
				$e, { this.scrollTo(scrollpos - 0.01); },
				$r, { this.scrollTo(scrollpos - 0.1); },
				$t, { this.scrollTo(0); },
				$y, { this.scrollTo(1); },
				$u, { this.scrollTo(scrollpos + 0.1); },
				$i, { this.scrollTo(scrollpos + 0.01); },
				$o, { this.scrollTo(scrollpos + 0.001); },
				$p, { this.scrollTo(scrollpos + 0.0001); },
				// $(, { sfv.scrollTo(0); this.changed(\zoom) },
				$>, { this.zoomToFrac(zoomfrac + 0.1); },
				$<, { this.zoomToFrac(zoomfrac - 0.1); },
				$z, { this.focusSoundFileView },
				$Z, { this.focusSoundFileView },
				$G, { this.openParameterGui },
				$ , { this.togglePlay }
			)
		})
		.action_({ | me |
			var zoomratio;
			zoomratio = me.hi-me.lo;
			sfv.zoomToFrac(zoomratio);
			sfv.scrollTo(scrollpos = (me.lo / ( 1 - zoomratio)));
			zoomfrac = me.range;
		})
		.addNotifier(this, \focusRangeSlider, { | n |
			n.listener focus: true;
		})
	}

	zoomToFrac { | frac |
		sfv.zoomToFrac(zoomfrac = frac.clip(0, 1));
		this.scrollTo(scrollpos);
		this.changed(\zoom);
	}

	scrollTo { | pos |
		sfv.scrollTo(scrollpos = pos.clip(0, 1));
		this.changed(\zoom);
	}

	focusSoundFileView {
		postln("switching focus to sound view");
		this.changed(\focusSoundFileView);
	}

	openParameterGui { selections.openParameterGui; }

	save {
		var code, path;
		code = selectionDict.values.collect(_.asCode).select(_.notNil); // do: _.postln;
		if (code.size == 0) {
			"There are no selections to save".postln;
		}{
			postln("saving selections for" + code.size + "buffers");
			{ | t | this.saveToFile(t, code.flat) }.inputText(Date.getDate.stamp, "Filename for saving:");
		}
	}

	saveToFile { | basename, code |
		var path;
		path = this.defaultPath +/+ basename ++ ".scd";
		// postln("the path is" + path);
		File.use(path, "w", { | f | code.flat do: { | c | f.write(c) }});
		// { postln("opening" + path);
		{ Document.open(path); }.defer(0.1);
	}

	defaultPath {
		^PathName(Platform.userHomeDir +/+ "sc-projects/local/").fullPath;
	}

	*loadFile { | p |
		var snippets, interpreted, instance, test;
		"READING SNIPPETS".postln;
		snippets = p.readSnippets;
		"CREATING INSTANCE".postln;
		instance = this.fromFile(p);
		snippets.postln;
		snippets do: { | s | instance.addSelectionFromDict(s.interpret); };
		instance.gui;
		// {   // "restoring again".postln;
				// instance.selectionDict[instance.buffer.name].restoreSelectionsToSfv(instance.sfv);
		// }.defer(0.5);

	}

	bufSelection { | buf | ^selectionDict[buf] }

	addSelectionFromDict { | argDict |
		selectionDict[argDict[\buf]].addSelectionFromDict(argDict);
	}

	bufName { ^buffer.name }
	envir { ^buffer.name.envir; }

	stats {
		var lv;
		(name++timestamp).asSymbol.vlayout(
			lv = ListView()
		).bounds_(Rect(0, 0, 1000, 450))
		.name_("stats" + name);
		lv.postln;
		lv.items = this.statList;
		lv.addNotifier(this, \stats, { | n | n.listener.items = this.statList;});
		this.changed(\stats);
	}

	statList {
		^selectionDict.keys.asArray.sort collect: { | k |
			selectionDict[k].buffer.name.asString + selectionDict[k].nonNullSelections;
		};
	}

	cloneSelection { | srcbuf, srcid, targetbuf, targetid |

	}

	cloneSelectionsEarlyPrototype {
		this.sources do: { | p | p.first.dict[\buf].postln };
	}

	bufNames { ^selectionDict.keys.asArray.sort }
	bufStats { | bufname |
		bufname ?? {
			^this.bufNames.collect({ | n | selectionDict[n].bufStats })
		};
		^selectionDict[bufname].bufStats;
	}

	cloneSelections { // TODO: Test this!
		var allBuffers, sourceBuffers, sourceBuffer, selectedParams, selectedDict;
		var sourceParam, targetBuffer;
		var freeSelectionSlot;
		allBuffers = selectionDict.keys.asArray.sort;
		targetBuffer = allBuffers.first;
		sourceBuffers = this.sources.collect({ | p | p.first.dict[\buf] });
		\cloneSelections.vlayout(
			HLayout(
				StaticText().string_("Source:").maxWidth_(50),
				Button().states_([["choose source buffer"]]).maxWidth_(140)
				.menu(sourceBuffers.collect({ | k | [k, { | me, k |
					k.postln;
					me.states_([[k]]);
					sourceBuffer = k;
					// postln("you selected buffer" + sourceBuffer);
					selectedParams = selectionDict[sourceBuffer].nonNullSelectionsParams;
					// postln("the parameters selected are" + selectedParams);
					this.changed(\sources);
				}]}))
				.addNotifier(this, \selection, { | n |
					sourceBuffers = this.sources.collect({ | p | p.first.dict[\buf] });
					n.listener.menu(sourceBuffers.collect({ | k | [k, { | me, k |
						k.postln;
						me.states_([[k]]);
						sourceBuffer = k;
						// postln("you selected buffer" + sourceBuffer);
						selectedParams = selectionDict[sourceBuffer].nonNullSelectionsParams;
						// postln("the parameters selected are" + selectedParams);
						this.changed(\sources);
					}]}))
				}),
				Button().states_([["choose a setting"]])
				.addNotifier(this, \sources, { | n |
					n.listener.menu(selectedParams.collect({ | p |
						[p.info, { | me |
							me.states_([[p.info]]);
							sourceParam = p;
						}]
					}))
				})
			),
			HLayout(
				StaticText().string_("target:").maxWidth_(50),
				StaticText().string_("-------")
				.background_(Color(0.25, 0.47, 0.8))
				.stringColor_(Color.yellow)
				.addNotifier(this, \clonetarget, { | n, i |
					n.listener.string = selectionDict[allBuffers[i]].bufStats;
				}),
				NumberBox() // TODO: clean up the multiple notifiers...
				.string_("---")
				.addNotifier(this, \selection, { | n |
					postln("current target is" + targetBuffer);
					freeSelectionSlot = this.bufSelection(targetBuffer).firstFreeSelection;
					postln("the first free selection is" + freeSelectionSlot);
					if (freeSelectionSlot.isNil) {
						postln("there are no free slots in" + targetBuffer);
						"Please choose a different target buffer".postln;
						n.listener.string_("---")
					}{ n.listener.value = freeSelectionSlot }
				})
				.addNotifier(this, \sources, { | n |
					postln("current target is" + targetBuffer);
					freeSelectionSlot = this.bufSelection(targetBuffer).firstFreeSelection;
					postln("the first free selection is" + freeSelectionSlot);
					if (freeSelectionSlot.isNil) {
						postln("there are no free slots in" + targetBuffer);
						"Please choose a different target buffer".postln;
						n.listener.string_("---")
					}{ n.listener.value = freeSelectionSlot }
				})
				.addNotifier(this, \clonetarget, { | n |
					postln("current target is" + targetBuffer);
					freeSelectionSlot = this.bufSelection(targetBuffer).firstFreeSelection;
					postln("the first free selection is" + freeSelectionSlot);
					if (freeSelectionSlot.isNil) {
						postln("there are no free slots in" + targetBuffer);
						"Please choose a different target buffer".postln;
						n.listener.string_("---")
					}{ n.listener.value = freeSelectionSlot }
				}),
				Button().maxWidth_(60).states_([["clone!", Color.white, Color.red]])
				.action_({
					if (sourceParam.isNil) {
						"Please choose a source parameter first".postln;
					}{
						postln("the source param is" + sourceParam.info);
						postln("the target buffer is" + targetBuffer);
						freeSelectionSlot = this.bufSelection(targetBuffer).firstFreeSelection;
						postln("the first free slot is:" + freeSelectionSlot);
						if (freeSelectionSlot.isNil) {
							postln("I cannot clone the selection to " + targetBuffer);
							"because it has no free selections".postln;
						}{
							postln("I will clone selection" + sourceParam.info +
								"to selection" + freeSelectionSlot + "of buffer" + targetBuffer;
								this.bufSelection(targetBuffer)
								.cloneParam(sourceParam, freeSelectionSlot);
							);
						}
					}
				})
			),
			ListView()
			.palette_(QPalette.light
				.highlight_(Color(0.7, 1.0, 0.9))
				.highlightText_(Color(0.0, 0.0, 0.0))
			)
			.items_(this.bufStats)
			.addNotifier(this, \sources, { | n |
				n.listener.items_(this.bufStats);
			})
			.action_({ | me |
				targetBuffer = allBuffers[me.value];
				this.changed(\clonetarget, me.value);
			})
		).bounds_(Rect(400, 0, 500, 450));
		{ this.changed(\clonetarget, 0) }.defer(0.1);
	}

	sources { // non-null selections
		^selectionDict.keys.asArray.sort
		.select({ | k | selectionDict[k].nonNullSelections.size > 0})
		.collect({ | s | selectionDict[s].nonNullSelectionsParams })
		// .flat;
		// .collect({ | s | selectionDict[s].nonNullSelectionsInfo });
	}

	targets { // null selections

	}
}