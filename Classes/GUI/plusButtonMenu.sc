/* 26 Jul 2023 09:06

*/


+ Button {
	menu { | labelfuncs |
		this.action_({ | me |
			Menu(*labelfuncs.collect({ | f | MenuAction(f[0], { f[1].(me, f[0]) }) })).front;
		})
	}
}