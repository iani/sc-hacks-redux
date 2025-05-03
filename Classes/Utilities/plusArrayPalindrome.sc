// 土  3  5 2025 17:25
// Create palindrome: append reversed copy to self.
/*
//:
(0..5).palindrome;
(degree: (0..10).palindrome.pseq, dur: 0.1) +> \test;
//:
*/
+ Array {
	palindrome { ^this ++ this.reverse }
}