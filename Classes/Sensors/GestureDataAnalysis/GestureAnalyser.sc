GestureAnalyser {
	var <>data;
	var <>sampleRate;  // estimated sample rate based on timestamps

	*new { |data|
		^super.new.init(data);
	}

	init { |inputData|
		data = inputData;
		this.calculateSampleRate;
	}

	calculateSampleRate {
		var timePoints, intervals, mean;

		if(data.size < 2) {
			sampleRate = 1;
			^this;
		};

		timePoints = data.collect({ |item| item[0] });
		intervals = (1..(timePoints.size-1)).collect({ |i|
			timePoints[i] - timePoints[i-1];
		});

		mean = intervals.sum / intervals.size;
		sampleRate = if(mean > 0, 1/mean, 1);

		^this;
	}

	// Analyze weight quality (light vs. strong)
	analyzeWeight {
		var acceleration, magnitudes;

		// Use the magnitude of acceleration (combining all dimensions if available)
		acceleration = data.collect({ |item|
			var values = item[1];
			if(values.size >= 3) {
				// Calculate magnitude if we have 3D data
				(values[0].squared + values[1].squared + values[2].squared).sqrt;
			} {
				// Otherwise use the average of available dimensions
				values.sum / values.size;
			};
		});

		// Map acceleration magnitude to weight quality (0 = light, 1 = strong)
		magnitudes = acceleration.normalize(0, 1);

		^Dictionary[
			'lightHeavy' -> magnitudes.mean,
			'peaks' -> this.findPeaks(magnitudes),
			'variance' -> magnitudes.variance
		];
	}

	// Analyze time quality (sudden vs. sustained)
	analyzeTime {
		var velocities, changeRates;

		// Calculate velocities between consecutive data points
		velocities = (1..(data.size-1)).collect({ |i|
			var curr = data[i][1], prev = data[i-1][1];
			var dt = data[i][0] - data[i-1][0];

			if(dt <= 0) { dt = 1/sampleRate };

			if(curr.isArray && prev.isArray) {
				(0..(curr.size.min(prev.size)-1)).collect({ |j|
					(curr[j] - prev[j]) / dt;
				}).abs.mean;
			} {
				((curr - prev) / dt).abs;
			};
		});

		// Calculate rate of velocity change
		changeRates = velocities.differentiate.abs;

		^Dictionary[
			'suddenSustained' -> changeRates.mean.linlin(0, changeRates.maxItem, 0, 1),
			'spikes' -> this.countSpikes(changeRates),
			'rhythmicality' -> this.analyzeRhythmicity(velocities)
		];
	}

	// Analyze space quality (direct vs. indirect)
	analyzeSpace {
		var directions, directness;

		// Extract direction changes if we have multi-dimensional data
		if(data[0][1].isArray && data[0][1].size >= 2) {
			directions = (1..(data.size-1)).collect({ |i|
				var curr = data[i][1], prev = data[i-1][1];

				// Calculate direction change between points
				if(curr.size >= 2 && prev.size >= 2) {
					// Simplified directness calculation for 2D or 3D
					var directionality = 0;

					// Calculate change in direction between consecutive vectors
					if(i > 1) {
						var prevDiff = (0..(curr.size.min(prev.size)-1)).collect({ |j|
							prev[j] - data[i-2][1][j];
						});

						var currDiff = (0..(curr.size.min(prev.size)-1)).collect({ |j|
							curr[j] - prev[j];
						});

						// Calculate directness based on vector similarity
						var dot = (0..(currDiff.size-1)).sum({ |j|
							currDiff[j] * prevDiff[j];
						});

						var magA = prevDiff.squared.sum.sqrt;
						var magB = currDiff.squared.sum.sqrt;

						if(magA > 0 && magB > 0) {
							directionality = (dot / (magA * magB) + 1) * 0.5; // normalize to 0-1
						};
					};

					directionality;
				} {
					0.5; // Default middle value if we can't determine
				};
			});

			directness = directions.mean;
		} {
			// If single dimensional, use variability as proxy for indirectness
			directness = 1 - data.collect({ |item| item[1] }).variance.linlin(0, 1, 0, 1);
		};

		^Dictionary[
			'directIndirect' -> directness,
			'complexity' -> this.calculateComplexity,
			'dimensionality' -> this.estimateDimensionality
		];
	}

	// Analyze flow quality (bound vs. free)
	analyzeFlow {
		var jerk, continuity;

		// Calculate jerk (rate of change of acceleration)
		jerk = (2..(data.size-1)).collect({ |i|
			var a1 = data[i][1].asArray;
			var a2 = data[i-1][1].asArray;
			var a3 = data[i-2][1].asArray;
			var dt1 = data[i][0] - data[i-1][0];
			var dt2 = data[i-1][0] - data[i-2][0];
			var acc1, acc2;

			if(dt1 <= 0) { dt1 = 1/sampleRate };
			if(dt2 <= 0) { dt2 = 1/sampleRate };

			acc1 = (a1 - a2) / dt1;
			acc2 = (a2 - a3) / dt2;

			((acc1 - acc2) / ((dt1 + dt2) * 0.5)).abs.mean;
		});

		// High jerk indicates bound flow, low jerk indicates free flow
		continuity = 1 - jerk.mean.linlin(0, jerk.maxItem, 0, 1);

		^Dictionary[
			'boundFree' -> continuity,
			'smoothness' -> continuity,
			'interruptions' -> this.countInterruptions
		];
	}

		// Comprehensive analysis of all movement qualities
	analyzeAll {
		^Dictionary[
			'weight' -> this.analyzeWeight,
			'time' -> this.analyzeTime,
			'space' -> this.analyzeSpace,
			'flow' -> this.analyzeFlow
		];
	}

	// Helper methods

	findPeaks { |values, threshold=0.7|
		var peaks = List[];
		values.doAdjacentPairs({ |a, b, i|
			if(a < b && b > values.atWrap(i+2) && b > threshold) {
				peaks.add(i+1);
			};
		});
		^peaks;
	}

	countSpikes { |values, threshold=2|
		var mean = values.mean;
		var deviation = values.stdDev;
		var count = 0;

		values.do({ |val|
			if((val - mean).abs > (deviation * threshold)) {
				count = count + 1;
			};
		});

		^count / values.size; // normalized count
	}

		analyzeRhythmicity { |values|
		var acf, peaks, periodicity;

		// Auto-correlation to find rhythmic patterns
		acf = (0..(values.size/4)).collect({ |lag|
			var sum = 0;
			(0..(values.size-lag-1)).do({ |i|
				sum = sum + (values[i] * values[i + lag]);
			});
			sum / (values.size - lag);
		});

		// Normalize autocorrelation
		if(acf[0] != 0) {
			acf = acf / acf[0];
		};

		// Find peaks in autocorrelation (indicators of periodicity)
		peaks = this.findPeaks(acf, 0.5);

		// Higher periodicity score indicates more rhythmic movement
		periodicity = if(peaks.size > 1, peaks.size / (acf.size * 0.25), 0);

		^periodicity.clip(0, 1);
	}

		calculateComplexity {
		var dimensions = data[0][1].size;
		var values = data.collect({ |item| item[1] });
		var complexity = 0;

		// Calculate approximate entropy as measure of complexity
		dimensions.do({ |dim|
			var series = values.collect({ |val| val[dim] ? 0 });
			var diffSeries = series.differentiate;
			var entropy = 0;

			// Calculate entropy using frequency of value changes
			if(diffSeries.size > 0) {
				var total = diffSeries.size;
				var bins = (-10..10); // discretize changes into bins
				var freqs = Dictionary[];

				diffSeries.do({ |diff|
					var bin = bins.detect({ |b| diff <= (b * 0.1) });
					bin = bin ? bins.last;
					freqs[bin] = (freqs[bin] ? 0) + 1;
				});

				// Shannon entropy calculation
				freqs.keysValuesDo({ |bin, count|
					var p = count / total;
					entropy = entropy - (p * log2(p));
				});

				// Normalize entropy
				complexity = complexity + entropy.linlin(0, 4, 0, 1);
			};
		});

		^(complexity / dimensions).clip(0, 1);
	}

	estimateDimensionality {
		var values = data.collect({ |item| item[1] });
		var dims = values[0].size;
		var activeDims = 0;

		dims.do({ |dim|
			var series = values.collect({ |val| val[dim] ? 0 });
			var range = series.maxItem - series.minItem;

			if(range > 0.1) { // This dimension has significant movement
				activeDims = activeDims + 1;
			};
		});

		^activeDims / dims; // Normalized active dimensions
	}

	countInterruptions {
		var velocities = (1..(data.size-1)).collect({ |i|
			var curr = data[i][1].asArray;
			var prev = data[i-1][1].asArray;
			var dt = data[i][0] - data[i-1][0];

			if(dt <= 0) { dt = 1/sampleRate };

			(0..(curr.size.min(prev.size)-1)).collect({ |j|
				(curr[j] - prev[j]) / dt;
			}).squared.sum.sqrt;
		});

		// Count velocity near-zero points that follow non-zero velocities
		var stops = 0;
		var threshold = velocities.mean * 0.2;

		velocities.doAdjacentPairs({ |a, b|
			if(a > threshold && b < threshold) {
				stops = stops + 1;
			};
		});

		^(stops / velocities.size).clip(0, 1); // Normalized count
	}
}