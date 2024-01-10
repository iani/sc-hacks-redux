/* 12 Oct 2023 23:03
List of water buffers.
*/
//:post all buffer names to select:
Buffer.all do: _.postln;
//:all buffer names:
bird_crickets
birdcarman
birdcarmanshort
columbia
cricket_ff
cricket_taps
crickets
crossing
default
echo_crickets
fones
hanbaiki
izumi
kiiroi
kojobottling
kojohaisuizemi
lake_plane
loud_crickets
marina
marinaboto
marinagunyoki
melodia
melodic_road
mizujuten
nami
namikodomokuruma
namitooiugoki
niwatoriame
noisebirdsclick
pilgrims
pulu
rough_steps
sarizaglassbottle
sarizakogawazoom
sarizakojo
sarizaplasticbottle
sarizaplasticbottle2
sarizasuiryu
shireimarina
suiro
suiryuzemi
swallows
sweet_crickets
takiike
takikoorogi
takisuiryu
takizemi
takunia
weddell
weddellpock
wind_moderate
wind_strong
yang_bird
yangdrums

//:water buffers:
~waterbufnames = [
	\izumi,
	\kojobottling,
	\kojohaisuizemi,
	\marina,
	\marinaboto,
	\marinagunyoki,
	\mizujuten,
	\nami,
	\namikodomokuruma,
	\namitooiugoki,
	\niwatoriame,
	\pulu,
	\sarizaglassbottle,
	\sarizakogawazoom,
	\sarizakojo,
	\sarizaplasticbottle,
	\sarizaplasticbottle2,
	\sarizasuiryu,
	\shireimarina,
	\suiro,
	\suiryuzemi,
	\takiike,
	\takikoorogi,
	\takisuiryu,
	\takizemi
];
//:
~waterbufnames.size;
//:
~waterbufs = ~waterbufnames collect: _.buf;
//:+ (PREVIEW)
~waterbufs do: { | b |
	b.path.name.postln;
	b.play;
	10.wait;
};