/* 20 Aug 2023 19:29
Create the template menu from any instance.
*/

TemplateMenu {
	var <preset;

	*new { | preset | ^this.newCopyArgs(preset).menu; }

	menu {
		^Button().states_([["+"]]).maxWidth_(15)
		.mouseDownAction_({ preset.makeCurrent; })
		.menuActions(preset.pfuncmenu)
	}
}
