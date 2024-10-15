/* 30 Jan 2021 12:43
Testing Notifier.
*/
\listener.addNotifier(\notifier, \message, { | ... args | args.postln; });
{ | i | \notifier.changed(\message, 2 ** i); } ! 10;
\listener.removeNotifier(\notifier, \message);
{ | i | \notifier.changed(\message, i); } ! 10;

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
