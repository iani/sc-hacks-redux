/* 27 Feb 2022 02:36

*/
//: Error:
1.removeNotifiers;
//: trace:
Object:removeNotifiers
Meta_Notification:removeNotifiersOf
Meta_Notification:notifiersOf ****************************************
//:
	*notifiersOf { | listener |
		^this.notifications.select({ | n |
			n.listeners includes: listener
		}).collect({ | n | n.model })
	}
//:
Notification.notifications.first;
-> aNotification.
//:
Notification.notifications.first.listeners;
--> nil !!!!!!!!
//:OK
Notification.notifiers; OK
//:i NEEDS DEBUGGING !!!!!!!!!!!!!!
Notification.listeners; // FIXED 27 Feb 2022 02:49
//: flat is wrong. IdentityDictionary does not understand flat.
*listeners { ^controllers.collect({ | c | c.listeners }).flat; }
//:use as Array instead
Notification.controllers.collect({|c| c.listeners}).asArray.flat;
//:
1.addNotifier(2, \x, { "x received".postln; });
2.addNotifier(2, \x, { "x received".postln; });
3.addNotifier(2, \x, { "x received".postln; });
1.addNotifier(1, \x, { "x received".postln; });
1.addNotifier(3, \x, { "x received".postln; });
1.addNotifier(2, \y, { "x received".postln; });
1.addNotifier(4, \z, { "x received".postln; });
2.addNotifier(5, \y, { "x received".postln; });
1.addNotifier(5, \y, { "x received".postln; });
//:
Notification.notifications
Notification.notifiers;
Notification.listeners;
//:
Notification.listenersOf(\x)
Notification.listenersOf(5)
Notification.notifiersOf(\x)
Notification.notifiersOf(1)
Notification.notifications.collect(_.notifier);
Notification.notifications.collect(_.listener);
//:
2.changed(\x);
//:
Notification.notifications.first.listeners;
//:
Notification.notifiersOf(1)
//:
Notification.listenersOf(2)
//: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! TOWARDS A SOLUTION?
Notification.notifications.select({|n| n.listener === 1}).collect({| n | n.notifier });
//:
Notification.controllers.asArray.first;
//:!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
Set.newFrom(Notification.notifications.collect({|n| n.notifier})).asArray;
//:
Notification.listeners;
//:
Notification.notifiers;