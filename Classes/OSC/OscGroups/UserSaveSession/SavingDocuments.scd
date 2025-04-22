
d = Document.allDocuments.detect({ | d | d.name.asSymbol === \iani }) ?? { Document("iani") };
d.string = "blablablablabla blablabla";
d.front;
//:
var d, string;
d = Document.allDocuments.detect({ | d | d.name.asSymbol === \iani }) ?? { Document("iani") };
{
	while { string.isNil } {
		string = d.string;
		0.1.wait;
		string = d.currentString;
	};
	"\n\n\n\n\n".postln;
	string.postln;
	"\n\n\n\n\n".postln;
}.fork;
//:
d.string;
d.close;
d.promptToSave