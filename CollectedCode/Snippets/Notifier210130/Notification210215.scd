/* 15 Feb 2021 14:27
New Notification tests
*/
//:
\test.dependants;
\test.addNotifier2(\notifier1, \something, { | ... args | ("b "++args).postln; });
\test.addNotifier2(\notifier2, \something, { | ... args | ("b "++args).postln; });
\test1.addNotifier2(\notifier1, \something, { | ... args | ("b "++args).postln; });
\test2.addNotifier2(\notifier1, \something, { | ... args | ("b "++args).postln; });
//:
Notification.listenersOf(\notifier1);
Notification.listenersOf(\notifier2);
Notification.notifiersOf(\test);
Notification.notifiersOf(\test1);
//:
\notifier1.dependants.asArray.first;
\notifier1.changed(\xbl);
\notifier1.changed(\something);
\notifier1xzz.changed(\something);
