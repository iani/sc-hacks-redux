/*
Dancer Gesture Analyzer - Main File
This project analyzes movement sensor data from dancers to evaluate expressive
qualities and interpret them as personality/character traits.
*/

// Run this section first to make sure all classes are compiled
(
// Initialize environment
~projectPath = thisProcess.nowExecutingPath.dirname;
// Make sure classes are recompiled
thisProcess.recompile;
)

// Main execution
(
// Configuration
~dataFolder = ~projectPath +/+ "data";
~sampleFile = "sample_data.txt"; // Change this to your data file
~vizEnabled = true;

// Create sample data if no files exist
if(PathName(~dataFolder).files.isEmpty) {
	"Creating sample data file...".postln;
	~createSampleData.value(~dataFolder +/+ ~sampleFile);
};

// Main function
~processFile = { |filename|
	var data, analyzer, interpreter, results, traits, description;
	
	// Load and parse data
	"Loading data from %...".format(filename).postln;
	data = DataParser.parseFile(filename);
	
	if(data.isNil || data.isEmpty) {
		"No valid data found in file.".error;
		^nil;
	};
	
	"Successfully loaded % data points.".format(data.size).postln;
	
	// Analyze movement qualities
	"Analyzing movement qualities...".postln;
	analyzer = GestureAnalyzer(data);
	results = analyzer.analyzeAll;
	
	// Output basic movement quality results
	"Movement Quality Analysis:".postln;
	
	// Weight quality
	"=== Weight Quality ===".postln;
	"Light-Heavy: %".format(results[\weight][\lightHeavy]).postln;
	
	// Time quality
	"=== Time Quality ===".postln;
	"Sudden-Sustained: %".format(results[\time][\suddenSustained]).postln;
	"Rhythmicity: %".format(results[\time][\rhythmicality]).postln;
	
	// Space quality
	"=== Space Quality ===".postln;
	"Direct-Indirect: %".format(results[\space][\directIndirect]).postln;
	"Complexity: %".format(results[\space][\complexity]).postln;
	
	// Flow quality 
	"=== Flow Quality ===".postln;
	"Bound-Free: %".format(results[\flow][\boundFree]).postln;
	"Smoothness: %".format(results[\flow][\smoothness]).postln;
	
	// Interpret as personality/character traits
	"Interpreting as personality/character traits...".postln;
	interpreter = PersonalityInterpreter(results);
	traits = interpreter.interpret;
	
	// Output top traits for each category
	"Personality/Character Interpretation:".postln;
	interpreter.dominantTraits(2).keysValuesDo({ |category, dominants|
		"=== % ===".format(category.asString.capitalize).postln;
		dominants.do({ |pair|
			"% - %".format(pair[0], pair[1].round(0.01)).postln;
		});
	});
	
	// Generate and display full description
	description = interpreter.generateDescription;
	"".postln;
	"Character/Personality Description:".postln;
	description.postln;
	
	// Create visualization if enabled
	if(~vizEnabled) {
		"Creating visualization...".postln;
		~visualizer = MovementVisualizer(data, results);
		~visualizer.createWindow("Movement Analysis: " ++ filename.basename);
	};
	
	// Return results for further processing or saving
	(
		data: data,
		results: results,
		traits: traits,
		description: description
	)
};

// Function to create sample data for testing
~createSampleData = { |filepath|
	var file = File(filepath, "w");
	var numSamples = 500;
	var startTime = 1648234567.0;
	var sampleRate = 0.05; // 20 Hz sample rate
	var dimensions = 6; // 6D data (e.g., 3D accel + 3D gyro)
	
	// Create patterns for different movement qualities
	var patterns = [
		// Light, sudden, indirect, free movement (playful)
		{
			var time = startTime + (0.2 * numSamples * sampleRate);
			numSamples.do { |i|
				var t = i * sampleRate;
				var values = Array.fill(dimensions, {
					// Low magnitude, high frequency oscillations
					0.3 * sin(t * 10 + 2pi.rand) + 
					0.2 * sin(t * 20 + 2pi.rand) +
					0.1 * (i % 10 / 10)
				});
				file.write((time + t).asString ++ ", " ++ values.asString ++ "\n");
			};
		},
		
		// Strong, sustained, direct, bound movement (determined)
		{
			var time = startTime + (0.4 * numSamples * sampleRate);
			numSamples.do { |i|
				var t = i * sampleRate;
				var phase = i / numSamples;
				var values = Array.fill(dimensions, { |j|
					// Higher magnitude, slower oscillation, more direct
					if(j < dimensions/2) {
						0.7 * sin(t * 2 + (j * 0.5)) + 0.2 // First half: stronger in one direction
					} {
						0.3 * sin(t * 4 + (j * 0.2)) - 0.1 // Second half: more subtle
					}
				});
				file.write((time + t).asString ++ ", " ++ values.asString ++ "\n");
			};
		},
		
		// Light->Strong, sudden, direct, free->bound (passionate)
		{
			var time = startTime + (0.6 * numSamples * sampleRate);
			numSamples.do { |i|
				var t = i * sampleRate;
				var phase = i / numSamples;
				var intensity = phase.linlin(0, 1, 0.2, 0.8);
				var values = Array.fill(dimensions, { |j|
					// Increasing intensity over time
					if(j.even) {
						intensity * sin(t * (5 + (phase * 5)) + j) +
						(phase * 0.3 * sin(t * 20 + 2pi.rand))
					} {
						intensity * cos(t * (5 + (phase * 5)) + j) +
						(phase * 0.3 * cos(t * 15 + 2pi.rand))
					}
				});
				file.write((time + t).asString ++ ", " ++ values.asString ++ "\n");
			};
		},
		
		// Light, sustained, indirect, bound (reflective)
		{
			var time = startTime + (0.8 * numSamples * sampleRate);
			numSamples.do { |i|
				var t = i * sampleRate;
				var noise = 0.05 * [-1, 1].choose;
				var values = Array.fill(dimensions, { |j|
					// Low magnitude, slow changes, small random variations
					0.2 * sin(t * 0.5 + (j * 0.8)) + 
					0.1 * sin(t * 1.5 + (j * 1.2)) +
					noise
				});
				file.write((time + t).asString ++ ", " ++ values.asString ++ "\n");
			};
		}
	];
	
	// Execute all patterns to create different movement sections
	patterns.do { |pattern| pattern.value };
	
	file.close;
	"Created sample data file at %".format(filepath).postln;
};

// Start processing
~analysisResults = ~processFile.value(~sampleFile);

"Analysis complete!".postln;
)

// Audio feedback system (optional)
// Uncomment this section to sonify movement qualities
/*
(
~createAudioFeedback = { |results|
	// Create synth definitions based on movement qualities
	SynthDef(\movementSynth, { |out=0, freq=440, amp=0.3, pan=0, 
		weight=0.5, time=0.5, space=0.5, flow=0.5|
		
		var sig, env, mod, filtFreq;
		
		// Weight affects amplitude and bass content
		amp = amp * weight.linexp(0, 1, 0.5, 1.5);
		
		// Time affects attack and release
		env = EnvGen.kr(
			Env.perc(
				time.linexp(0, 1, 0.001, 0.2),  // attack (sudden to sustained)
				time.linexp(0, 1, 0.1, 2)       // release (sudden to sustained)
			), 
			doneAction: 2
		);
		
		// Space affects frequency spread and reverb
		mod = SinOsc.kr(
			space.linexp(0, 1, 8, 0.5),         // modulation speed (direct to indirect)
			0, 
			space.linlin(0, 1, 0.01, 0.1)       // modulation depth
		);
		
		// Flow affects filter smoothness and vibrato
		filtFreq = flow.linexp(0, 1, 500, 5000);
		
		sig = Mix.ar([
			SinOsc.ar(freq * (1 + mod)),
			LFTri.ar(freq * 0.5) * weight,
			LFNoise2.ar(freq * 0.75) * (1 - flow) * 0.2
		]);
		
		sig = LPF.ar(sig, filtFreq);
		sig = sig * env * amp;
		sig = Pan2.ar(sig, pan);
		
		Out.ar(out, sig);
	}).add;
	
	s.sync;
	
	// Create patterns based on movement qualities
	~movementPattern = Pbind(
		\instrument, \movementSynth,
		\dur, Pwhite(0.1, 0.3),
		\degree, Prand([0, 2, 4, 5, 7, 9, 11], inf),
		\octave, 4,
		\amp, 0.3,
		\weight, results[\weight][\lightHeavy],
		\time, results[\time][\suddenSustained],
		\space, results[\space][\directIndirect],
		\flow, results[\flow][\boundFree],
		\pan, Pwhite(-0.7, 0.7)
	);
	
	~player = ~movementPattern.play;
};

// Boot server and start audio feedback
s.waitForBoot {
	~createAudioFeedback.value(~analysisResults[\results]);
};
)
*/
