/* 20 Aug 2023 19:33
Superclass for different types of view creators.
Create views for preset related instances.
Encapsulate gui item creation for various kinds of instances.

Subclasses add view method.

*/

PresetViewTemplate {
	var <preset;
	*new { | preset | ^this.newCopyArgs(preset) }
}