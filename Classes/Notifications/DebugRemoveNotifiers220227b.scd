/* 27 Feb 2022 07:32

	*removeNotifiersOf { | listener |
		this.notifying(listener) do: _.remove;
	}

	*removeListenersOf { | notifier |
		this.listeningto(notifier) do: _.remove;
	}
*/
//:
1.addNotifier(2, \x, { "x received".postln; });
//:
Notification.notifications;
//:
Notification.listeningto(2);
//:
Notification.notifying(1);
//:
Notification.notifiersOf(1);
//:
Notification.removeNotifiersOf(1);
//:
Notification.listeningto(2) do: _.remove;
//:
Notification.listeningto(2);
//:
Notification.listenersOf(2);
//:
Notification.listenersOf(5);
//:
Notification.removeNotifiersOf(3);
//:
Notification.removeNotifiersOf(2);
//:
//:
Notification.notifications.first.remove;
//:
2.addNotifier(2, \x, { "x received".postln; });
3.addNotifier(2, \x, { "x received".postln; });
1.addNotifier(1, \x, { "x received".postln; });
1.addNotifier(3, \x, { "x received".postln; });
1.addNotifier(2, \y, { "x received".postln; });
1.addNotifier(4, \z, { "x received".postln; });
2.addNotifier(5, \y, { "x received".postln; });
1.addNotifier(5, \y, { "x received".postln; });
