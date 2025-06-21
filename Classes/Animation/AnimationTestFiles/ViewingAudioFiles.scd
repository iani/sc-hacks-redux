// 木 12  6 2025 02:57
// Viewind data with SoundFileView.
// Possible both with sound files and with numeric data arrays, from OscDataConverter.
// aSoundFileView.setData(data, block, startFrame: 0, channels: 1, samplerate: 44100)
//:

w = Window.new("soundfile test", Rect(0, 0, 1040, 800));
w.view.layout = HLayout(
	a = SoundFileView.new(w, Rect(10, 10, 1030, 780));
);
f = SoundFile.new;
f.openRead(Platform.resourceDir +/+ "sounds/a11wlk01.wav");

a.soundfile = f;
a.read(0, f.numFrames);

a.timeCursorOn = true;
a.timeCursorColor = Color.red;
a.timeCursorPosition = 2050;
a.drawsWaveForm = true;
a.gridOn = true;
a.gridResolution = 0.2;

w.front;
//:Try 1: 51 channels, Yolo (Justine)
k = Animation.sessions.asArray[1];
k.converter.convert;
l = k.converter.numdata;

a.setData(l[..1000].lace, 16, channels: 51);
a.setData(l.lace, 16, channels: 51);
a.zoomAllOut;
a zoom: 0.01;
//:
{
	(0, 0.01 ..1) do: { | i |
		a.scrollTo(i);
		0.5.wait;
	}
}.fork(AppClock)
//:Try 2: 161 channels, Rokoko (Giorgos)
k = Animation.sessions.asArray[0];
k.convertData;
l = k.converter.numdata;

a.setData(l.lace, 16, channels: 161);
//:try different zooms
a.zoomAllOut;
a zoom: 0.1;
//:
{
	(0, 0.01 ..1) do: { | i |
		a.scrollTo(i);
		0.5.wait;
	}
}.fork(AppClock)
//:
l.size;
l.flop.size;
//:
f = SoundFile.new;
f.openRead(Platform.resourceDir +/+ "sounds/a11wlk01.wav");
w = \sf.hlayout(
	a = SoundFileView()
);
a.read(0, f.numFrames);

a.timeCursorOn = true;
a.timeCursorColor = Color.red;
a.timeCursorPosition = 2050;
a.drawsWaveForm = true;
a.gridOn = true;
a.gridResolution = 0.2;

l.first.size;
l.size;