/* 22 Jul 2023 14:06
Filenavigator for OscData.
*/

OscFileNavigator : FileNavigator {
	bounds { ^Rect(0, 0, 350, 180) }
	homeDir { ^homeDir ?? { homeDir = PathName(Platform.recordingsDir).asDir; } }
}