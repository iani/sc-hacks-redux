/* 26 Jul 2023 09:06

*/


+ Button {
	menuActions { | labelsfuncs |
		this.action_({ | me |
			Menu(*labelsfuncs.collect({ | f | MenuAction(f[0], { f[1].(me, f[0]) }) })).front;
		})
	}
}