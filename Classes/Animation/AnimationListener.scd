// 火 24  6 2025 16:47
// Send osc messages from AnimationController or from LiveMocapPlayer
// to NetAddr instances, by name
// Note: we cannot use aNetAddr.addNotifier because Notification listeners
AnimationListener {
	classvar <listeners;
	classvar <localPort = 22245;

	*initClass {
		listeners = IdentityDictionary();
		this.makeLocalListener;
	}

	*makeLocalListener {
		listeners[\local] = NetAddr("127.0.0.1", localPort);
	}

	*localPort_ { | argLocalPort = 22245 |
		localPort = argLocalPort;
		this.makeLocalListener;
	}


}