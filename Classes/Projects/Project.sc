/* 15 Sep 2021 18:26
Load project scripts and audio files from subfolders of a folder.


	~/sc-projects.

Subfolder structure:

~/sc-projects/global : files used by all projects

~/sc-projects/
~/sc-projects/projects : each subfolder is a project

~/sc-projects/global/setup : loaded at sclang StartUp
~/sc-projects/global/audiofiles : loaded at scsynth boot
~/sc-projects/global/start : loaded at Project.start;
~/sc-projects/global/server_boot : loaded after server boots
?????? (maybe?)  ~/sc-projects/global/server_quit : loaded after server quits
~/sc-projects/global/stop : loaded at Project.stop;
~/sc-projects/global/scripts : loaded from gui buttons
	Project creates a gui with buttons for each file or subfolder.
	Subfolders containing files create buttons with one state per file.
	One can cycle between executing each of the files

~/sc-projects/projects/ : contains one subfolder per project
	Each subfolder has the same structure as the global folder
	There is a default folder with default scripts.
	It can be used as template and copied to create own projects
	Here is the structure of the default folder, showing how
	to make project folders:

~/sc-projects/projects/default/setup : loaded at sclang StartUp
~/sc-projects/projects/default/audiofiles : loaded at scsynth boot
~/sc-projects/projects/default/start : loaded at Project.start;
~/sc-projects/projects/default/server_boot : loaded after server boots
?????? (maybe?)  ~/sc-projects/projects/default/server_quit : loaded after server quits
~/sc-projects/projects/default/stop : loaded at Project.stop;
~/sc-projects/projects/default/scripts : loaded from gui buttons
	Project creates a gui with buttons for each file or folder
	Folders containing files create buttons with one state per file.
*/

Project {
	classvar <>root = "~/sc-projects/";
	classvar <>currentProject; //  = "default";
	var <name, <path;

	*globalPath { ^root +/+ "global/" }
	*globalSetupPath { ^this.globalPath +/+ "setup/" }
	*globalAudiofilesPath { ^this.globalPath +/+ "audiofiles/" }
	*globalStartPath { ^this.globalPath +/+ "start/" }
	*globalServer_bootPath { ^this.globalPath +/+ "server_boot/" }
	*globalServer_quitPath { ^this.globalPath +/+ "server_quit/" }
	*globalStopPath { ^this.globalPath +/+ "stop/" }
	*globalScriptsPath { ^this.globalPath +/+ "scripts/" }

	*projectListGui {

	}

	*gui {
		this.window;
	}
}