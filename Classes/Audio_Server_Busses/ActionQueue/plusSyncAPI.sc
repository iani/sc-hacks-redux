/*

13 Aug 2020 09:14

Methods for syncing various kinds of objects.

N.B. 1: server always defaults to Server.default.
N.B. 2: Buffer 

- aFunction.sync(optional: server) :: Add the function to Queue for synced allocation.
- aSynthDef.sync(opitonal: server) :: Add a function that adds the synthdef to the server for synced allocation.
- aString.sync(optional: server) :: Add a function that loads the file given by path aString.
- Buffer.allocSync :: Like Buffer.alloc, but execution is synced by Queue.  Returns the new buffer.  The buffer instance is created immediately on sclang and returned, but the message for allocating the buffer on the server is sent later in the order given by the Que sync.
- Buffer.readSync :: Like Buffer.read, but execution is synced by Queue.  Returns the new buffer.  The buffer instance is created immediately on sclang and returned, but the message for reading the buffer on the server is sent later in the order given by the Que sync.

*/

+ Synth {
	*q { | defName, args, target, addAction=\addToHead |
		var synth;
		synth = Synth.basicNew(defName, target); // create the synth
		{
			var msg;
			msg = synth.newMsg(target, args, addAction);
			target = target.asTarget.server;
			target.sendMsg(*msg); // start the synth
		}.sync(target.asTarget.server);
		^synth; // return the synth immediately for further use!
	}

	// maybe these are not needed!:
	qSet { | ... args | { this.set(*args) }.sync(this.server); }
	qMap { | ... args | { this.map(*args) }.sync(this.server); }
}

+ Function {
	sync { | server |
		Queue.named(server.asTarget.server).add(this)
	}
	syncPlay { | target, outbus = 0, fadeTime = 0.02, addAction=\addToHead, args |
		{
			this.play(target.asTarget, outbus, fadeTime, addAction, args)
		}.sync(target.asTarget.server);
	}
}

+ String {
	// q { | server | this.sync(server) }
	sync { | server | Queue(server).add({ this.load }) }
}

+ Buffer {
	qsetn { | ... args | // ensure that buffer exists before calling setn
		{ this.setn(*args) }.sync(this.server);
	}

	qsetRange { | array, index = 0 |
		// Set all values of the array, starting in the buffer at index.
		//	Clip the array to fit within the size of the buffer.
		var indices, maxIndex;
		maxIndex = index + array.size - 1 min: (numFrames - 1);
		indices = (index .. maxIndex);
		this.qsetn(*indices.collect({ | i | [i, array[i - index]] }).flat);
	}

	qget { | index, action | // ensure that buffer is synced before calling get
		{ this.get(index, action) }.sync(this.server);
	}

	qplot { | name, bounds, minval, maxval, separately = false |
		{
			{ this.plot(name, bounds, minval, maxval, separately)}.defer
		}.sync(this.server)
	}

	// Ensures that server is booted before allocating buffer
	*qalloc { | server, numFrames, numChannels = 1, completionMessage, bufnum |
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

	*qread { | server, path, startFrame = 0, numFrames = -1, action, bufnum |
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
		// TODO: rewrite this properly.  Use Buffer:readWithInfo
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