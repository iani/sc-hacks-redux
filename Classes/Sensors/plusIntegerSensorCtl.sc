/* 24 Jul 2023 00:04
Shortcuts for playing control functions with input from sensors.
Called by SensorCtl in Param, SoundParams.
*/

+ Integer {
	sensorClip { ^this.clip(1, 12) } // constrain to existing sensor ids in current systsm
	off {  | player, param, lo, hi, map | // turn control synth off.
		format("nil @>.% %", player, param.slash).share;
	}

	lx { | player, param, lo, hi, map |
		// this.sensorClip.

	}
	lz { | player, param, lo, hi, map |

	}
	gx { | player, param, lo, hi, map |

	}
	gz { | player, param, lo, hi, map |

	}
	xyz { | player, param, lo, hi, map |

	}

	x { | player, param, lo, hi, map |

	}

	z { | player, param, lo, hi, map |

	}

}