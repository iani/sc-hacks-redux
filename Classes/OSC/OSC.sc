/* 14 Feb 2022 11:17
Alternative scheme for OSC reception
*/

OSC {

	*add { | receiver, message, function |
		receiver.addNotifier(this, message, function);
	}

	*remove { | receiver, message |
		receiver.removeNotifier(this, message);
	}

	*clear {
		this.releaseDependants;
	}

}