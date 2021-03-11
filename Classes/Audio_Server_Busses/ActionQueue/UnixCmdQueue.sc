/* 11 Mar 2021 17:22
A Queue that synchronizes unix commands.

Commands are executed in the order in which they are sent.
Each next command in the queue is executed after the previous has 
completed execution.

This is a draft modeled after class Queue.
It uses the function callback argument from String:next to call then next step.



*/

UnixCmdQueue {
	var <commands;   // list of commands to run sequentially
	var <inactive = true; // If inactive, start. Else wait for previous cmd.
	var <next, <pid; // last executed code and pid of the process created by it
	var <>onEnd;

	*new {
		^this.newCopyArgs(List());		
	}

	add { | command | // command is a String to be sent as unix cmd.
		commands add: command;
		if (inactive) {  // If inactive, start
			inactive = false; 
			this.changed(\started, Process.elapsedTime);
			this.prNext;
		}
		// if active, wait for next command to finish
	}
	
	prNext {
		/* runs when receiving sync from previous evaluation,
			or, when starting, at the very beginning evaluating
			the first element.
			
			Use changed method to broadcast the last evaluated function
			and the pid of evaluating it. 

			Obtain the next element and evaluate it.
			Store the next element and the pid of its evaluation.
		*/
		// First broadcast the commands and pid from the previous evaluation.
		this.changed(\eval, next, pid);
		next = commands[0];
		if (next.isNil) {
			inactive = true;
			this.changed(\stopped, Process.elapsedTime);
			onEnd.(this);
			// "Que ended!".postln;
		}{
			commands remove: next;
			pid = next.unixCmd({ | exitCode, exitPid |
				postf("unix cmd % had exit code % and exit pid %\n",
					next, exitCode, exitPid
				);
				this.prNext;
			});
		}	
	}
}

