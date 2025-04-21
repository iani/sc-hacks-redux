//: create a document for a user and store it somewhere.
a = Document("iani1");
//:keep appending strings to the end of the document,
//documenting the code received via OSC
1000 do: { | i | a.string_(format("\nsecond % x", i), 1, 1000); };
//:
var document, id;
var documents;
id = User.localId;
id.postln;
documents = Document.allDocuments;
document = documents detect: { | d | d.name.asSymbol === id };
document ?? { document = Document(id.asString) };
document.front;
//:
