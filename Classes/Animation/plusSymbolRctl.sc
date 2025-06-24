// 火 24  6 2025 15:20
// Shortcut for getting control bus index

+ Symbol {
	rctl { | dim |
		^AnimationController.current.getCtl(this, dim);
	}
}