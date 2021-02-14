/* 14 Feb 2021 13:51
Perhaps storing a Dict as single dependant in a notifier is more efficient?
*/
\listener.addNotifier(\notifier, \message, { | ... args | args.postln; });
\listener.addNotifier(\notifier, \message2, { | ... args | args.postln; });
\listener.addNotifier(\notifier2, \message, { | ... args | args.postln; });
\listener2.addNotifier(\notifier, \message, { | ... args | args.postln; });
//:
Notifier.notifiers;
Notifier.notifiers.atPath(\notifier);
Notifier.notifiers.atPath(\notifier).keys;
Notifier.notifiers.atPath(\notifier);


//:
\listener.addNotifierOneShot(\notifier, \message, { | ... args | args.postln; });
{ | i | \notifier.changed(\message, i + 1000); } ! 10;

//:post the entire notifier tree
Notifier.notifiers;
//:find notifiers
\listener.addNotifier(\notifx, \messagex, { | ... args | args.postln; });
\listener.addNotifier(\notifx, \messagey, { | ... args | args.postln; });
\listener.addNotifier(\notify, \messagex, { | ... args | args.postln; });
Notifier.notifiersOf(\listener);
Notifier.notifiersOf(\listener);

//:
Notifier.notifiers;
Notifier.notifiers.atPath(\notifx).keys;
//:find messages, listeners
a = Notifier.notifiers;
a.leaves.flat;

a.at(\notifx, \messagex, \listener);
a.leafDo({ | l | l.class.postln; l.postln; });
//:
Notifier.notifiers.dictionary.keys;
