//Fri 12 Jan 2024 18:19 classes for testing the setting of environent variables

EnvirTest1 {
	*initClass {
		StartUp add: {
			"Setting envir class from EnvirTest1 startup".postln;
			~classInitSet = "Environment variable set by EnvirTest1 at startup";
			currentEnvironment.postln;
		}
	}

	*test {
		~classSet = 123;
	}
}