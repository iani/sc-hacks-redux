/*
MovementVisualizer.sc
Provides visualization tools for movement data and analysis results
*/

MovementVisualizer {
	var <>window;
	var <>data;
	var <>analysisResults;
	var <>userViews;
	var <>colors;
	
	*new { |data, analysisResults|
		^super.new.init(data, analysisResults);
	}
	
	init { |inputData, results|
		data = inputData;
		analysisResults = results;
		userViews = Dictionary[];
		
		colors = Dictionary[
			\weight -> Color.red,
			\time -> Color.blue,
			\space -> Color.green,
			\flow -> Color.yellow
		];
	}
	
	createWindow { |title="Movement Data Visualization"|
		var width = 800, height = 600;
		var tabs;
		
		window = Window(title, Rect(100, 100, width, height))
			.front
			.onClose_({ this.cleanup });
			
		tabs = TabbedView2(window, Rect(0, 0, width, height))
			.resize_(5)
			.tabHeight_(30);
			
		this.createRawDataView(tabs.add("Raw Data"));
		this.createMovementQualitiesView(tabs.add("Movement Qualities"));
		this.createPersonalityView(tabs.add("Personality/Character"));
		
		window.refresh;
	}
	
	createRawDataView { |parent|
		var plotView, selectView;
		var dimensions = Dictionary[];
		var currentDim = 0;
		
		plotView = UserView(parent, parent.bounds.moveTo(0, 0).insetBy(10, 40))
			.resize_(5)
			.background_(Color.white);
		
		// Create selection for data dimensions
		if(data.size > 0 && data[0][1].isArray) {
			var numDimensions = data[0][1].size;
			
			selectView = PopUpMenu(parent, Rect(10, 10, 200, 20))
				.items_(["All Dimensions"] ++ (0..(numDimensions-1)).collect({ |i| "Dimension " ++ i }))
				.action_({ |menu|
					currentDim = menu.value - 1; // -1 because "All" is index 0
					plotView.refresh;
				});
				
			// Extract data for each dimension
			numDimensions.do({ |dim|
				dimensions[dim] = data.collect({ |item| [item[0], item[1][dim]] });
			});
		};
		
		userViews[\rawData] = plotView;
		
		plotView.drawFunc = {
			var bounds = plotView.bounds.moveTo(0, 0);
			var timeRange, valueRange;
			var timePoints, values;
			var timeMin, timeMax, valMin, valMax;
			var xScale, yScale;
			
			if(data.size > 0) {
				// Draw time-series data
				if(currentDim == -1) { // All dimensions
					// Get time range across all data
					timePoints = data.collect({ |item| item[0] });
					timeMin = timePoints.minItem;
					timeMax = timePoints.maxItem;
					timeRange = timeMax - timeMin;
					
					// Draw each dimension with its own color
					dimensions.keysValuesDo({ |dim, dimData|
						var dimValues = dimData.collect({ |item| item[1] });
						valMin = dimValues.minItem;
						valMax = dimValues.maxItem;
						valueRange = valMax - valMin;
						
						if(valueRange == 0) { valueRange = 1 };
						
						xScale = bounds.width / timeRange;
						yScale = bounds.height / valueRange;
						
						Pen.strokeColor = Color.hsv(dim / dimensions.size, 0.7, 0.9);
						Pen.width = 1.5;
						
						Pen.moveTo(Point(
							(dimData[0][0] - timeMin) * xScale,
							bounds.height - ((dimData[0][1] - valMin) * yScale)
						));
						
						dimData[1..].do({ |item|
							Pen.lineTo(Point(
								(item[0] - timeMin) * xScale,
								bounds.height - ((item[1] - valMin) * yScale)
							));
						});
						
						Pen.stroke;
						
						// Label
						Pen.stringAtPoint(
							"Dim " ++ dim, 
							Point(bounds.right - 60, bounds.height - (dim * 15) - 20),
							Font(Font.defaultSansFace, 9),
							Pen.strokeColor
						);
					});
				} {
					// Single dimension
					var dimData = dimensions[currentDim];
					values = dimData.collect({ |item| item[1] });
					timePoints = dimData.collect({ |item| item[0] });
					
					timeMin = timePoints.minItem;
					timeMax = timePoints.maxItem;
					valMin = values.minItem;
					valMax = values.maxItem;
					
					timeRange = timeMax - timeMin;
					valueRange = valMax - valMin;
					
					if(valueRange == 0) { valueRange = 1 };
					
					xScale = bounds.width / timeRange;
					yScale = bounds.height / valueRange;
					
					// Draw axis
					Pen.strokeColor = Color.black;
					Pen.width = 1;
					
					// Draw x-axis
					Pen.moveTo(Point(0, bounds.height - 20));
					Pen.lineTo(Point(bounds.width, bounds.height - 20));
					
					// Draw data
					Pen.strokeColor = Color.blue;
					Pen.width = 2;
					
					Pen.moveTo(Point(
						(dimData[0][0] - timeMin) * xScale,
						bounds.height - 20 - ((dimData[0][1] - valMin) * yScale)
					));
					
					dimData[1..].do({ |item|
						Pen.lineTo(Point(
							(item[0] - timeMin) * xScale,
							bounds.height - 20 - ((item[1] - valMin) * yScale)
						));
					});
					
					Pen.stroke;
					
					// Labels
					Pen.stringAtPoint(
						"Min: " ++ valMin.round(0.001),
						Point(10, bounds.height - 10),
						Font(Font.defaultSansFace, 9)
					);
					
					Pen.stringAtPoint(
						"Max: " ++ valMax.round(0.001),
						Point(10, 10),
						Font(Font.defaultSansFace, 9)
					);
				};
			} {
				Pen.stringCenteredIn(
					"No data available",
					bounds,
					Font(Font.defaultSansFace, 14),
					Color.black
				);
			};
		};
	}
	
	createMovementQualitiesView { |parent|
		var plotView, qualitySelect;
		var currentQuality = \weight;
		
		plotView = UserView(parent, parent.bounds.moveTo(0, 0).insetBy(10, 40))
			.resize_(5)
			.background_(Color.white);
		
		qualitySelect = PopUpMenu(parent, Rect(10, 10, 200, 20))
			.items_(["Weight", "Time", "Space", "Flow"])
			.action_({ |menu|
				currentQuality = [\weight, \time, \space, \flow][menu.value];
				plotView.refresh;
			});
			
		userViews[\qualities] = plotView;
		
		plotView.drawFunc = {
			var bounds = plotView.bounds.moveTo(0, 0);
			
			if(analysisResults.notNil && analysisResults[currentQuality].notNil) {
				var qualities = analysisResults[currentQuality];
				var barWidth = bounds.width / (qualities.size + 1);
				var barGap = barWidth * 0.2;
				var maxBarHeight = bounds.height - 60;
				
				// Draw bars for each quality
				qualities.keysValuesDo({ |quality, value|
					var x, height, color;
					
					// Skip special qualities like "peaks" or "spikes" that aren't scalar values
					if(value.isKindOf(Number)) {
						x = qualities.keys.asList.indexOf(quality) * barWidth + barGap;
						height = value * maxBarHeight;
						color = colors[currentQuality].blend(Color.white, 0.5);
						
						// Draw bar
						Pen.fillColor = color;
						Pen.fillRect(Rect(x, bounds.height - height - 30, barWidth - (barGap*2), height));
						
						// Draw label
						Pen.stringCenteredIn(
							quality.asString,
							Rect(x, bounds.height - 25, barWidth - (barGap*2), 20),
							Font(Font.defaultSansFace, 9),
							Color.black
						);
						
						Pen.stringCenteredIn(
							value.round(0.01).asString,
							Rect(x, bounds.height - height - 45, barWidth - (barGap*2), 20),
							Font(Font.defaultSansFace, 9),
							Color.black
						);
					};
				});
				
				// Draw title
				Pen.stringCenteredIn(
					currentQuality.asString.capitalize ++ " Quality Analysis",
					Rect(0, 5, bounds.width, 20),
					Font(Font.defaultSansFace, 14, true),
					Color.black
				);
			} {
				Pen.stringCenteredIn(
					"No analysis results available",
					bounds,
					Font(Font.defaultSansFace, 14),
					Color.black
				);
			};
		};
	}
	
	createPersonalityView { |parent|
		var textView, interpreter;
		
		if(analysisResults.notNil) {
			interpreter = PersonalityInterpreter(analysisResults);
			
			textView = TextView(parent, parent.bounds.moveTo(0, 0).insetBy(10, 10))
				.resize_(5)
				.font_(Font(Font.defaultSansFace, 12))
				.background_(Color.white)
				.hasVerticalScroller_(true)
				.editable_(false);
				
			textView.string = interpreter.generateDescription();
			
			// Add radar chart of traits
			this.addPersonalityRadar(parent, interpreter);
		} {
			textView = StaticText(parent, parent.bounds.moveTo(0, 0).insetBy(10, 10))
				.resize_(5)
				.string_("No analysis results available")
				.align_(\center)
				.font_(Font(Font.defaultSansFace, 14));
		};
		
		userViews[\personality] = textView;
	}
	
	addPersonalityRadar { |parent, interpreter|
		var traits = interpreter.interpret;
		var radar;
		
		radar = UserView(parent, Rect(parent.bounds.width / 2, 50, parent.bounds.width / 2 - 20, parent.bounds.height / 2 - 60))
			.resize_(5)
			.background_(Color.white);
			
		radar.drawFunc = {
			var bounds = radar.bounds.moveTo(0, 0);
			var center = bounds.center;
			var radius = bounds.width.min(bounds.height) * 0.4;
			var characterTraits = traits[\character];
			var numTraits = characterTraits.size;
			var angle, point;
			
			// Draw axis for each trait
			Pen.strokeColor = Color.gray(0.8);
			Pen.width = 1;
			
			characterTraits.keysValuesDo({ |trait, value, i|
				angle = 2 * pi * (i / numTraits);
				point = Point(
					center.x + (sin(angle) * radius),
					center.y - (cos(angle) * radius)
				);
				
				Pen.moveTo(center);
				Pen.lineTo(point);
				Pen.stroke;
				
				// Draw trait label
				Pen.stringCenteredIn(
					trait,
					Rect(
						center.x + (sin(angle) * (radius + 10)) - 40,
						center.y - (cos(angle) * (radius + 10)) - 10,
						80, 20
					),
					Font(Font.defaultSansFace, 9),
					Color.black
				);
			});
			
			// Draw concentric circles
			[0.25, 0.5, 0.75, 1].do({ |scale|
				Pen.strokeColor = Color.gray(0.8);
				Pen.width = 0.5;
				Pen.addArc(center, radius * scale, 0, 2pi);
				Pen.stroke;
			});
			
			// Draw data points
			characterTraits.keysValuesDo({ |trait, value, i|
				angle = 2 * pi * (i / numTraits);
				point = Point(
					center.x + (sin(angle) * radius * value),
					center.y - (cos(angle) * radius * value)
				);
				
				Pen.fillColor = Color.red(0.7, 0.5);
				Pen.fillOval(Rect(point.x - 3, point.y - 3, 6, 6));
			});
			
			// Connect data points
			Pen.strokeColor = Color.red(0.7, 0.5);
			Pen.width = 1.5;
			
			characterTraits.keysValuesDo({ |trait, value, i|
				angle = 2 * pi * (i / numTraits);
				point = Point(
					center.x + (sin(angle) * radius * value),
					center.y - (cos(angle) * radius * value)
				);
				
				if(i == 0) {
					Pen.moveTo(point);
				} {
					Pen.lineTo(point);
				};
			});
			
			// Connect back to first point
			if(numTraits > 0) {
				angle = 0;
				point = Point(
					center.x + (sin(angle) * radius * characterTraits[characterTraits.keys.asArray[0]]),
					center.y - (cos(angle) * radius * characterTraits[characterTraits.keys.asArray[0]])
				);
				Pen.lineTo(point);
			};
			
			Pen.stroke;
			
			// Title
			Pen.stringCenteredIn(
				"Character Traits",
				Rect(0, 5, bounds.width, 20),
				Font(Font.defaultSansFace, 12, true),
				Color.black
			);
		};
	}
	
	// Clean up resources
	cleanup {
		window = nil;
		userViews = nil;
	}
}
