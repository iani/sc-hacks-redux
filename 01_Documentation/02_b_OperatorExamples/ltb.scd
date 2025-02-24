//Fri 23 Feb 2024 11:51
//SymbolOperators

+ Symbol {
	// EventStream played actions
	// Actions run every time an EventStream plays its next event
	<! { | func, envir | this.addEventStreamAction(func, envir); }
}