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
var a;
a = { | n |
	postln(n.listener + "received" + n.message + "from" + n.notifier);
};
2.addNotifier(2, \x, a);
3.addNotifier(2, \x, a);
1.addNotifier(1, \x, a);
1.addNotifier(3, \x, a);
1.addNotifier(2, \y, a);
1.addNotifier(4, \z, a);
2.addNotifier(5, \y, a);
1.addNotifier(5, \y, a);
//:
2.changed(\x);
//:
2.removeListeners;
//:
5.changed(\y);
//:
2.removeNotifiers;
//:
Notification.clear;
//:
("*" + \b + "*").class;