/* 12 Jul 2023 07:08
Reads templates from a directory.
Constructs code for BufCode.

SuperClass for BufferSynths, PlainSynths - which look for their templates inside their
separate folders.

A SynthTemplate can search and return 2 things with 2 methods:
1. getFunc: The Function used to play in a Preset or ...
2. getTemplate: the template - a SynthTemplate or subclass instance containing
	some information needed:
	- the name, function, specs, code used to make the template, and the template itself
	which is an array of classes used to create the specs
*/

SynthTemplate {
	classvar <homefolder, <templatesfolder, <playfuncsfolder, <codefolder;
	classvar alltemplates; // dict with all templates from all subclasses

	var <path, <name, <func, <specs, <code, <template;

	*templateNames { ^this.alltemplates.keys.asArray.sort }
	*alltemplates { // (lazily???) collect all templates in one dict.
		^alltemplates ?? { alltemplates = IdentityDictionary() };
	}

	alltemplates { ^this.class.alltemplates }

	*initClass {
		StartUp add: { this.init }
	}

	*init {
		Spec.addSC_Hacks_Specs;
		this.allSubclasses do: _.getAll;
	}

	*getAll {
		this.templatePaths do: { | p | this.new(p) };
	}

	*templatePaths { // Load from subfolder "templates" from your definition folder.
		^(PathName(this.filenameSymbol.asString).pathOnly +/+ "templates" +/+ "*.scd").pathMatch;
	}

	*new { | path |
		^this.newCopyArgs(path).init;
	}

	init {
		name = PathName(path).fileNameWithoutExtension.asSymbol;
		this.load;
		this.alltemplates[name] = this;
	}

	*makePreset { | playfunc, presetList, index |
		^this.getTemplate(playfunc).makePreset(presetList, index);
	}

	makePreset { | presetList, index |
		^Preset.newCopyArgs(presetList, index).importDict(this.dict)
	}

	*getFunc { | funcname |
		^this.getTemplate(funcname).func.amplify; // amplify: add amp control!
	}

	*getTemplate { | funcname | ^this.alltemplates[funcname]; }

	load {
		var delimiters;
		code = File.readAllString(path);
		delimiters = code.findAll("\n//:");
		if (delimiters.size == 0) {
			specs = this.defaultSpecs;
			func = code.interpret;
		}{
			func = code[delimiters[0]..].interpret;
			template = code[..delimiters[0]].interpret ?? { this.defaultSpecs };
			specs = [\amp.asSpec.units_(\amp)] ++ (template collect: _.specs).flat;
		};
	}

	defaultSpecs { ^[PlayBuf_] }

	dict { // dictionary for creating presets
		// merge default basicDict with entries from the individual template.
		^this.basicDict addEvent: this.customDict;
	}

	basicDict { // basic dictionary for all SynthTemplates.
		// subclasses may add more keys to this.  See BufferSynths
		// amp is provided to all template funcs using "amplify!" in method getFunc
		^(amp: [1, "", \off], playfunc: name);
	}

	customDict { // dict from template specs
		var dict;
		dict = ();
		this.specs.flat do: { | s |
			dict[s.units] = [s.default, ""];
		};
		^dict;
	}

	edit { Document open: path; }

	playView { | view, preset | // new version: Wed 16 Aug 2023 09:33
		var buffermenu;
		// postln("this is SynthTemplate.playView");
		buffermenu = Buffer.all collect: { | p |
			[p, { | me |
				preset.switchBuffer(p);
				me.states_([[p]])
			}]
		};
		{
			preset.changed(\frames, preset.startFrame, preset.endFrame, preset.dur);
		}.defer(0.1);
		^HLayout(
			CheckBox().string_("play").maxWidth_(50)
			.action_({ | me |
				if (me.value) { preset.play }{ preset.stop }
			})
			.addNotifier(preset.presetList, \stopped, { | n, who |
				if (who !== preset) { n.listener.value = false };
			})
			// TODO: FIX PRESET!!!!!:
			.addNotifier(preset.envir, preset.player, { | n |
				// "Received notification from envir".postln;
				if (envir(preset.player).isPlaying) {
					n.listener.value = false;
					n.listener.focus(true);
				}
			}),
			StaticText().maxWidth_(20).string_(preset.index.asString)
			.addNotifier(preset, \index, { | n | n.listener.string = preset.index.asString }),
			StaticText().maxWidth_(100).string_(preset.playfunc.asString),
			Button().maxWidth_(150).states_([[preset.bufname]])
			.keyDownAction_({ | me, key, mod, asci |
				if (asci == 13) { preset.switchBuffer }
			})
			.focusColor_(Color.red)
			.menuActions(buffermenu),
			StaticText().maxWidth_(35).string_("startf"),
			NumberBox().maxWidth_(80).focusColor_(Color.red)
			.addNotifier(preset, \frames, { | n, start |
				n.listener.value = start;
			}),
			StaticText().maxWidth_(30).string_("endf"),
			NumberBox().maxWidth_(80).focusColor_(Color.red)
			.addNotifier(preset, \frames, { | n, start, end |
				n.listener.value = end;
			}),
			StaticText().maxWidth_(30).string_("dur"),
			NumberBox().maxWidth_(50).focusColor_(Color.red)
			.addNotifier(preset, \frames, { | n, start, end, dur |
				n.listener.value = dur;
			}),
			// preset.templateMenu,
			PfuncMenu(preset).view,
			PscoreMenu(preset).view,
			PdeleteButton(preset, view).view
		)
	}
}