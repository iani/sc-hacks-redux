//Document for kosmas created at 250515_102734
//:[9188.698909667] kosmas kosmas 250515_211938
~hydraBridgeOSCAddr.sendMsg("/hydra/setVideo", "videos/Verses/3.mp4");
//:[9198.96559075] kosmas kosmas 250515_211948
~hydraBridgeOSCAddr.sendMsg("/hydra/setVideo", "videos/Verses/03.mp4");
//:[9206.05788825] kosmas kosmas 250515_211955
~hydraBridgeOSCAddr = NetAddr("127.0.0.1", 12000); // Port MUST match OSC_LISTEN_PORT in server.js
//:[9207.995325875] kosmas kosmas 250515_211957
~hydraBridgeOSCAddr.sendMsg("/hydra/setVideo", "videos/Verses/03.mp4");
//:[9341.223873875] kosmas kosmas 250515_212210
~hydraBridgeOSCAddr.sendMsg("/hydra/setVideo", "videos/Verses/09.mp4");
//:[9352.9947135] kosmas kosmas 250515_212222
~hydraBridgeOSCAddr.sendMsg("/hydra/setVideo", "videos/Verses/11.mp4");
//:[369.712145125] kosmas kosmas 250515_213756
(
Tsr.doOnVerse({ | index, verse, incipit |
    var fileNumberString;
    // Add 1 to index to get the range 1-30
    // Format as a decimal integer (%d) with 2 digits, zero-padded (%02d)
	"registered!!!".postln;
    fileNumberString = "%02d".format(index + 1);

    ("Formatted index: " ++ fileNumberString).postln; // For testing, remove in final code

    ~hydraBridgeOSCAddr.sendMsg("/hydra/setVideo", "videos/Verses/" ++ fileNumberString ++ ".mp4");
});
)
//:[462.500297125] kosmas kosmas 250515_213929
(
Tsr.doOnVerse({ | index, verse, incipit |
    var fileNumberString;
    // Add 1 to index to get the range 1-30
    // Format as a decimal integer (%d) with 2 digits, zero-padded (%02d)
	"registered!!!".postln;
    fileNumberString = (100 + index).asString[1..];
    ("Formatted index: " ++ fileNumberString).postln; // For testing, remove in final code

    ~hydraBridgeOSCAddr.sendMsg("/hydra/setVideo", "videos/Verses/" ++ fileNumberString ++ ".mp4");
});
)
//:[478.95227] kosmas kosmas 250515_213945
(
Tsr.doOnVerse({ | index, verse, incipit |
    var fileNumberString;
    // Add 1 to index to get the range 1-30
    // Format as a decimal integer (%d) with 2 digits, zero-padded (%02d)
	"registered!!!".postln;
    fileNumberString = (100 + index).asString[1..];
    ("Formatted index: " ++ fileNumberString).postln; // For testing, remove in final code

    ~hydraBridgeOSCAddr.sendMsg("/hydra/setVideo", "videos/Verses/" ++ fileNumberString ++ ".mp4");
});
)
//:[584.117998708] kosmas kosmas 250515_214130
(
Tsr.doOnVerse({ | index, verse, incipit |
    var fileNumberString;
    // Add 1 to index to get the range 1-30
    // Format as a decimal integer (%d) with 2 digits, zero-padded (%02d)
	"registered!!!".postln;
    fileNumberString = (100 + index).asString[1..]+1;
    ("Formatted index: " ++ fileNumberString).postln; // For testing, remove in final code

    ~hydraBridgeOSCAddr.sendMsg("/hydra/setVideo", "videos/Verses/" ++ fileNumberString ++ ".mp4");
});
)
//:[595.677332833] kosmas kosmas 250515_214142
(
Tsr.doOnVerse({ | index, verse, incipit |
    var fileNumberString;
    // Add 1 to index to get the range 1-30
    // Format as a decimal integer (%d) with 2 digits, zero-padded (%02d)
	"registered!!!".postln;
    fileNumberString = (100 + index + 1).asString[1..];
    ("Formatted index: " ++ fileNumberString).postln; // For testing, remove in final code

    ~hydraBridgeOSCAddr.sendMsg("/hydra/setVideo", "videos/Verses/" ++ fileNumberString ++ ".mp4");
});
)
//:[964.482702458] kosmas kosmas 250515_214750
(
Tsr.doOnVerse({ | index, verse, incipit |
    var fileNumberString;
    // Add 1 to index to get the range 1-30
    // Format as a decimal integer (%d) with 2 digits, zero-padded (%02d)
	"registered!!!".postln;
    fileNumberString = (100 + index + 1).asString[1..];
    ("Formatted index: " ++ fileNumberString).postln; // For testing, remove in final code

    ~hydraBridgeOSCAddr.sendMsg("/hydra/setVideo", "videos/Verses/" ++ fileNumberString ++ ".mp4");
}, \kosmas);
)
//:[6033.134366292] kosmas kosmas 250515_231219
~hydraBridgeOSCAddr = NetAddr("127.0.0.1", 12000); // Port MUST match OSC_LISTEN_PORT in server.js
//:[6036.248231208] kosmas kosmas 250515_231222
(
Tsr.doOnVerse({ | index, verse, incipit |
    var fileNumberString;
    // Add 1 to index to get the range 1-30
    // Format as a decimal integer (%d) with 2 digits, zero-padded (%02d)
	"registered!!!".postln;
    fileNumberString = (100 + index + 1).asString[1..];
    ("Formatted index: " ++ fileNumberString).postln; // For testing, remove in final code

    ~hydraBridgeOSCAddr.sendMsg("/hydra/setVideo", "videos/Verses/" ++ fileNumberString ++ ".mp4");
}, \kosmas);
)
//:[8738.559366542] kosmas kosmas 250515_235725
~georgios
//:[8739.56276775] kosmas kosmas 250515_235726
~georgios
//:[8748.381948208] kosmas kosmas 250515_235734
~georgios
//:[8748.69826725] kosmas kosmas 250515_235735
~georgios
//:[11103.364880833] kosmas kosmas 250516_003649
~hydraBridgeOSCAddr = NetAddr("127.0.0.1", 12000); // Port MUST match OSC_LISTEN_PORT in server.js
//:[11105.624758375] kosmas kosmas 250516_003652
(
Tsr.doOnVerse({ | index, verse, incipit |
    var fileNumberString;
    // Add 1 to index to get the range 1-30
    // Format as a decimal integer (%d) with 2 digits, zero-padded (%02d)
	"registered!!!".postln;
    fileNumberString = (100 + index + 1).asString[1..];
    ("Formatted index: " ++ fileNumberString).postln; // For testing, remove in final code

    ~hydraBridgeOSCAddr.sendMsg("/hydra/setVideo", "videos/Verses/" ++ fileNumberString ++ ".mp4");
}, \kosmas);
)
//:[75161.315538958] kosmas kosmas 250516_182427
~hydraBridgeOSCAddr.sendMsg("/hydra/setVideo", "videos/Verses/11.mp4");
