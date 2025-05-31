Loading models
Models are loaded with a key to identify them and a path to a torchscript file:
NN.load(\modelName, "/path/to/torchscript.ts")
The sclang interface instructs the server to load the .ts file, receives and stores models' info from the server, and keeps track of which models are loaded. Once a model is loaded, and its info received, it becomes possible to create UGens for processing.

Real-time processing
You can get UGens for each models' method like this:
NN(\modelName, \methodName).ar(inputs, blockSize, debug, attributes)
Each NN().ar UGen is specific to a loaded model and method. This is because different models and methods require different numbers of inputs and outputs. Each UGen loads an independent instance of the model, to make sure independent inferences on the same model don't interfere with each other. For this reason, setting attributes is supported only at the UGen level. For processing multiple inputs at the same time using only one instance of a model, see section Multi-channel.

Attributes
Torchscript can support settable attributes:
NN(\modelName, \methodName).ar(inputs, attributes: [
    attributeName: LFNoise0.kr(1),
    anotherAttr: LFTri.kr(1).range(0, 10)
])
NNUGen accepts a list of pairs (attributeName, attributeValue), and will set each attribute when its value changes. It might be useful to limit the rate at which an attribute is set by using Latch:
NN(\modelName, \methodName).ar(inputs, attributes: [
    temperature: Latch.kr(LFTri.kr(1).range(0, 10)),
    reset: MouseButton.kr(lag:0),
], debug: 1)
With debug: 1, NNUGen will print the attribute value every time it's set. The printed value is read from the model for every print.

Multi-channel
When supplying multiple inputs, NN.ar will process them using the same model, with batch processing. This is different from SuperCollider multi-channel expansion, where multiple inputs produce multiple UGens. NN.ar prefers to load a single UGen, and thus a single model, that can process all inputs in parallel, thus reducing computational cost.
// example 1: resynthesize three sine waves
// without multi-channel: three separate copies of the same model (expensive)
{ SinOsc.ar([100,200,300]).collect { |in| NN(\ravePerc, \forward).ar(in) } }.play
// with multi-channel: use only one model (cheaper)
{ NN(\ravePerc, \forward).ar(SinOsc.ar([100,200,300])) }.play
​
// example 2: pseudo-stereo:
// - add 2 different noise vectors to the same latents
// - decode 2 slightly different channels, using a single model
{
    var latents = NN(\ravePerc, \encode).ar(SoundIn.ar);
    // latent size is 8
    var noiseVectors = 2.collect { LFNoise1.ar(1).range(-0.1, 0.1)!latents.size };
    // noiseVectors shape is [2, 8]
    var modLatents = noiseVectors.collect { |noise| latents + noise };
    // modLatents shape is [2, 8]
    var out = NN(\ravePerc, \decode).ar(modLatents);
    // out shape is [2]
    out
}.play
Note that Attributes don't cause multichannel expansion, precisely because we are loading a single model, that can only have one set of attributes.

NRT processing
In order to load and play with models on an NRT server, models' informations have to be stored in a file. This method is intended for running NRT servers without even booting a real-time one:
// Prerequisite:
// ask a running server to dump all currently loaded models' info to a file
NN.dumpInfo("models.yaml");
​
a = Score([
    // NN.nrt reads that info file and makes an osc bundle
    [0.0] ++ NN.nrt("models.yaml") {
        // load models
        NN.load(\a, "path/to/model_a.ts");
        NN.load(\b, "path/to/model_b.ts");
        // synthdefs: use .doSend(s) to get them into the bundle
        SynthDef(\nnfb) { |out=0|
            var fb = LocalIn.ar(1);
            var sig = NN(\b, \forward).ar(
                    NN(\a, \forward).ar(fb)
            );
            LocalOut.ar(sig);
            Out.ar(out, sig);
        }.doSend(s);
    },
    [0.0, Synth.basicNew(\nnfb).newMsg],
    [30.0]
]).recordNRT(
    outputFilePath: "gen.wav",
    headerFormat: "wav",
    sampleFormat: "float",
    action: { "done".postln }
)
In the NN.nrt() { ... } block, the syntax to load methods and create SynthDefs is almost the same as in real-time, with the only difference being that SynthDefs need to be "sent" to server with .doSend(s) instead of other methods.

A second method is available if models are already loaded on a running server. The following code creates messages for the NRT server to load all models currently loaded on a RT server, with the same indices, so that SynthDefs built on the RT server work also on the NRT one. The obvious drawback is that this method is more expensive in terms of resources, since models are loaded on both the real-time and any NRT servers that are launched.
Score([
    [0.0] ++ NN.models.collect(_.loadMsg).postln,
    [0.0, ["/d_recv", SynthDescLib.global[\nnar].asBytes]],
    [0.0, Synth.basicNew(\nnar).newMsg],
    [30.0]
]).recordNRT(
    outputFilePath: "gen.wav",
    headerFormat: "wav",
    sampleFormat: "float",
    action: { "done".postln }
)
First-execution warmup
If model processing is very slow for the first execution right after the model is loaded, and then becomes much faster, it might be due to torchscript performing optimizations during the first passes. NN offers a way of performing a custom number of processing passes silently:
{ NN(\model, \forward).ar(WhiteNoise.ar) }.play
// long dropouts at first, then fine
​
{ NN(\model, \forward).ar(WhiteNoise.ar, warmup: 2) }.play
// longer silence, but no initial stuttering
Warmup passes are done on an empty input buffer: the model processes "silent" inputs and discards their outputs before starting to process actual inputs.

Class MethodsPre-trained models
NN.ar is only an interface to load and use torchscripts, but doesn't include any pre-trained model. However, pre-trained RAVE models for real-time neural synthesis can be found on the Internet, for example:

at acids-ircam, the creators of RAVE: https://acids-ircam.github.io/rave_models_download
at iil (Intelligent Instruments Lab): https://huggingface.co/Intelligent-Instruments-Lab/rave-models
For training your own RAVE models, please refer to RAVE documentation at https://github.com/acids-ircam/rave. NN.ar is not limited to RAVE model, and can load and use any model prepared to work in nn_tilde. For more information please visit https://github.com/acids-ircam/nn_tilde
