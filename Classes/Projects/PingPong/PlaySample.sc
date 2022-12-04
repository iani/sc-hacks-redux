/*  4 Dec 2022 10:55
Shortcut for playing samples with ping pong.
*/

+ Symbol {
	pIng {
		\myping.addNotifier(\pingpong, \pongIsOn, {
			this.buf.play;
		});
	}

	pOng {
		\mypong.addNotifier(\pingpong, \pingIsOn, {
			this.buf.play;
		})

	}
}