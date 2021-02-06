/*

13 Aug 2020 09:14

Methods for syncing various kinds of objects.

N.B. 1: server always defaults to Server.default.
N.B. 2: Buffer 

- aFunction.sync(optional: server) :: Add the function to Que for synced allocation.
- aSynthDef.sync(opitonal: server) :: Add a function that adds the synthdef to the server for synced allocation.
- aString.sync(optional: server) :: Add a function that loads the file given by path aString.
- Buffer.allocSync :: Like Buffer.alloc, but execution is synced by Que.  Returns the new buffer.  The buffer instance is created immediately on sclang and returned, but the message for allocating the buffer on the server is sent later in the order given by the Que sync.
- Buffer.readSync :: Like Buffer.read, but execution is synced by Que.  Returns the new buffer.  The buffer instance is created immediately on sclang and returned, but the message for reading the buffer on the server is sent later in the order given by the Que sync.

*/

+ Synth {
	sync { | defName, args, target, addAction=\addToHead |
		/* Rewrite this to return the synth immediately and 
			send the synth bundle to the server later.
			See SynthPlayer:makeSynth in sc-hacks.
		*/
		// var synth;
		// synth = Synth.basicNew(...)
		{
			/*
				source.doSend (
					server, // TODO: remove \out, outbus from args and check
					process.newMsg(target, [\i_out, outbus, \out, outbus] ++ args,
						envir [\addAction] ? \addToHead);
				);
			*/
			Synth(defName, args, target, addAction)
			
		}.sync(target.asTarget.server);
		// ^synth; // RETURN THE SYNTH IMMEDIATELY FOR FURTHER USE!
	}

	syncSet { | ... args |
		{ this.set(*args) }.sync(this.server);
	}

	syncMap { | ... args |
		{ this.map(*args) }.sync(this.server);
	}
}

+ Function {
	sync { | server | Queue(server).add(this) }
	syncPlay { | target, outbus = 0, fadeTime = 0.02, addAction=\addToHead, args |
		{
			this.play(target.asTarget, outbus, fadeTime, addAction, args)
		}.sync(target.asTarget.server);
	}
}

+ String {
	sync { | server | Queue(server).add({ this.load }) }
}

+ Buffer {
	*allocSync { | server, numFrames, numChannels = 1, completionMessage, bufnum |
		/*
			Create new buffer and return it.
			Postpone the allocation on the server to happen in synced order,
			using Queue.add(). 
		*/
		var buffer;
		server = server ? Server.default;
		bufnum ?? { bufnum = server.nextBufferNumber(1) };
		buffer = super.newCopyArgs(server,
						bufnum,
						numFrames,
						numChannels);
		{
			buffer.alloc(completionMessage).sampleRate_(server.sampleRate).cache;
		}.sync(server);
		^buffer; 
	}

	*readSync { | server, path, startFrame = 0, numFrames = -1, action, bufnum |
		/*
			Create new buffer and return it.
			Postpone the reading from file on the server 
			to happen in synced order, using Queue.add(). 

			TODO: Use method from SuperDirt to set buffer frame info etc.
			from info on file.  See: Buffer:readWithInfo
		*/
		var buffer;
		server = server ? Server.default;
		bufnum ?? { bufnum = server.nextBufferNumber(1) };
		buffer = super.newCopyArgs(server, bufnum);
		{
			buffer.doOnInfo_(action).cache
			.allocRead(path, startFrame, numFrames, {|buf|["/b_query", buf.bufnum] });
		}.sync(server);
		^buffer;
	}
}

/* // templates for implementation
	*alloc { arg server, numFrames, numChannels = 1, completionMessage, bufnum;
		server = server ? Server.default;
		bufnum ?? { bufnum = server.nextBufferNumber(1) };
		^super.newCopyArgs(server,
						bufnum,
						numFrames,
						numChannels)
					.alloc(completionMessage).sampleRate_(server.sampleRate).cache
	}

	*read { arg server, path, startFrame = 0, numFrames = -1, action, bufnum;
		server = server ? Server.default;
		bufnum ?? { bufnum = server.nextBufferNumber(1) };
		^super.newCopyArgs(server, bufnum)
					.doOnInfo_(action).cache
					.allocRead(path, startFrame, numFrames, {|buf|["/b_query", buf.bufnum] })
	}

*/