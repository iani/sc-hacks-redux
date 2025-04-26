/*
PersonalityInterpreter.sc
Maps movement qualities to personality, emotional, and character traits
based on movement psychology research and Laban Movement Analysis principles
*/

PersonalityInterpreter {
	classvar <>traitMappings;
	var <>analysisResults;
	
	*initClass {
		// Define mappings between movement qualities and personality traits
		traitMappings = Dictionary[
			// Weight mappings (light vs. strong)
			'weight' -> Dictionary[
				'delicate' -> { |qualities| 1 - qualities[\weight][\lightHeavy] },
				'powerful' -> { |qualities| qualities[\weight][\lightHeavy] },
				'gentle' -> { |qualities| (1 - qualities[\weight][\lightHeavy]) * qualities[\flow][\smoothness] },
				'assertive' -> { |qualities| qualities[\weight][\lightHeavy] * (1 - qualities[\time][\suddenSustained]) }
			],
			
			// Time mappings (sudden vs. sustained)
			'time' -> Dictionary[
				'spontaneous' -> { |qualities| 1 - qualities[\time][\suddenSustained] },
				'deliberate' -> { |qualities| qualities[\time][\suddenSustained] },
				'impulsive' -> { |qualities| (1 - qualities[\time][\suddenSustained]) * qualities[\weight][\lightHeavy] },
				'methodical' -> { |qualities| qualities[\time][\suddenSustained] * qualities[\space][\directIndirect] }
			],
			
			// Space mappings (direct vs. indirect)
			'space' -> Dictionary[
				'focused' -> { |qualities| qualities[\space][\directIndirect] },
				'explorative' -> { |qualities| 1 - qualities[\space][\directIndirect] },
				'analytical' -> { |qualities| qualities[\space][\directIndirect] * qualities[\time][\suddenSustained] },
				'creative' -> { |qualities| (1 - qualities[\space][\directIndirect]) * qualities[\flow][\boundFree] }
			],
			
			// Flow mappings (bound vs. free)
			'flow' -> Dictionary[
				'controlled' -> { |qualities| 1 - qualities[\flow][\boundFree] },
				'expressive' -> { |qualities| qualities[\flow][\boundFree] },
				'restrained' -> { |qualities| (1 - qualities[\flow][\boundFree]) * qualities[\space][\directIndirect] },
				'spontaneous' -> { |qualities| qualities[\flow][\boundFree] * (1 - qualities[\time][\suddenSustained]) }
			],
			
			// Complex emotional/personality trait mappings
			'character' -> Dictionary[
				'confident' -> { |qualities| 
					(qualities[\weight][\lightHeavy] * 0.6) + 
					(qualities[\space][\directIndirect] * 0.3) + 
					(qualities[\flow][\boundFree] * 0.1)
				},
				
				'anxious' -> { |qualities| 
					((1 - qualities[\flow][\boundFree]) * 0.5) + 
					(qualities[\time][\spikes] * 0.3) + 
					(qualities[\flow][\interruptions] * 0.2)
				},
				
				'playful' -> { |qualities| 
					((1 - qualities[\weight][\lightHeavy]) * 0.4) + 
					(qualities[\flow][\boundFree] * 0.4) + 
					(qualities[\space][\complexity] * 0.2)
				},
				
				'determined' -> { |qualities| 
					(qualities[\weight][\lightHeavy] * 0.3) + 
					(qualities[\space][\directIndirect] * 0.4) + 
					(qualities[\time][\suddenSustained] * 0.3)
				},
				
				'reflective' -> { |qualities| 
					(qualities[\time][\suddenSustained] * 0.5) + 
					((1 - qualities[\weight][\lightHeavy]) * 0.3) + 
					((1 - qualities[\flow][\interruptions]) * 0.2)
				},
				
				'passionate' -> { |qualities| 
					(qualities[\weight][\lightHeavy] * 0.3) + 
					((1 - qualities[\time][\suddenSustained]) * 0.3) + 
					(qualities[\flow][\boundFree] * 0.4)
				},
				
				'reserved' -> { |qualities| 
					((1 - qualities[\flow][\boundFree]) * 0.4) + 
					((1 - qualities[\weight][\lightHeavy]) * 0.3) + 
					(qualities[\time][\suddenSustained] * 0.3)
				},
				
				'chaotic' -> { |qualities| 
					((1 - qualities[\time][\suddenSustained]) * 0.3) + 
					((1 - qualities[\space][\directIndirect]) * 0.4) + 
					(qualities[\space][\complexity] * 0.3)
				}
			]
		];
	}
	
	*new { |analysisResults|
		^super.new.init(analysisResults);
	}
	
	init { |results|
		analysisResults = results;
	}
	
	// Interpret movement qualities into personality traits
	interpret {
		var traits = Dictionary.new;
		
		traitMappings.keysValuesDo({ |category, mappings|
			mappings.keysValuesDo({ |trait, calcFunc|
				var score = calcFunc.value(analysisResults);
				
				if(traits[category].isNil) {
					traits[category] = Dictionary[];
				};
				
				traits[category][trait] = score.clip(0, 1);
			});
		});
		
		^traits;
	}
	
	// Find the dominant traits (top N from each category)
	dominantTraits { |n=2|
		var traits = this.interpret;
		var dominants = Dictionary[];
		
		traits.keysValuesDo({ |category, traitDict|
			var sorted = traitDict.asSortedArray.reverse;
			dominants[category] = sorted[0..n.min(sorted.size-1)];
		});
		
		^dominants;
	}
	
	// Generate a text description of the personality/character based on movement analysis
	generateDescription {
		var traits = this.interpret;
		var dominant = this.dominantTraits(2);
		var description = "";
		var weightDesc, timeDesc, spaceDesc, flowDesc, characterDesc;
		
		// Create weight description
		weightDesc = if(traits[\weight][\lightHeavy] > 0.7) {
			"The movement shows strong, grounded qualities with powerful energy."
		} {
			if(traits[\weight][\lightHeavy] < 0.3) {
				"The movement exhibits light, delicate qualities with gentle energy."
			} {
				"The movement balances between lightness and strength, showing adaptable use of weight."
			}
		};
		
		// Create time description
		timeDesc = if(traits[\time][\suddenSustained] > 0.7) {
			"Movements are executed with sustained timing, suggesting a deliberate and methodical approach."
		} {
			if(traits[\time][\suddenSustained] < 0.3) {
				"Movements tend to be quick and sudden, suggesting spontaneity and responsiveness."
			} {
				"The timing varies between sudden and sustained, showing adaptability to context."
			}
		};
		
		// Create space description
		spaceDesc = if(traits[\space][\directIndirect] > 0.7) {
			"Movement patterns are direct and focused, suggesting clarity of intention."
		} {
			if(traits[\space][\directIndirect] < 0.3) {
				"Movement patterns are indirect and multi-focused, suggesting an explorative nature."
			} {
				"The use of space varies between direct and indirect, showing contextual awareness."
			}
		};
		
		// Create flow description
		flowDesc = if(traits[\flow][\boundFree] > 0.7) {
			"The flow of movement is primarily free and fluid, suggesting expressiveness and openness."
		} {
			if(traits[\flow][\boundFree] < 0.3) {
				"The flow of movement is primarily bound and controlled, suggesting precision and restraint."
			} {
				"The flow varies between bound and free, showing adaptable control and release."
			}
		};
		
		// Create character summary
		characterDesc = "Character traits suggested by the movement include ";
		dominant[\character].do({ |pair, i|
			var trait = pair[0];
			var score = pair[1];
			var intensity;
			
			intensity = if(score > 0.8) {
				"strongly"
			} {
				if(score > 0.5) {
					"moderately"
				} {
					"somewhat"
				}
			};
			
			characterDesc = characterDesc ++ format("% % (%)", 
				if(i > 0) { 
					if(i == dominant[\character].size-1) { " and " } { ", " }
				} { "" },
				trait, 
				intensity
			);
		});
		characterDesc = characterDesc ++ ".";
		
		// Assemble full description
		description = description 
			++ weightDesc ++ "\n\n"
			++ timeDesc ++ "\n\n"
			++ spaceDesc ++ "\n\n"
			++ flowDesc ++ "\n\n"
			++ characterDesc;
		
		^description;
	}
}
