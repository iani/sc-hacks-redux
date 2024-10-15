/* 29 Jul 2023 15:45

*/

PresetList.loadPresets;

a = PresetList.parentPath;
a


a = PresetList.scriptsInLib;
PathName(a +/+ "PresetScripts" +/+ "*.scd")

.pathMatch;
