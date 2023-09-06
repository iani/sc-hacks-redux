/* 23 Jul 2023 12:01
Draft for menu for saving bookmarks
*/
//:
Button().states_([["save bookmark"]])
				.action_({ | me | Menu(
					MenuAction("save bookmark", {
						bookmarks = bookmarks add:
						this.save })
				)
 this.save }),
//:
{ | b | b.postln }.inputText(
	Date.getDate.stamp,
	"Enter the name for the new bookmark",
);