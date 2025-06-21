// 250615ff

Animation.gui;

a = Animation.sessions.asArray.first.name;
x = Animation.sessions.asArray.first;
x.soundFilePath;

File exists: x.soundFilePath;

x.converter;


b = AnimationGui(a);
b
c = AnimationGui(a);
b === c;

b
b.window({ | w | w.postln; }, \guiwindow);

b.hlayout(ListView());

Animation

AnimationPlayer.verbose = true;

\x.window;

OscGroups.enable;
