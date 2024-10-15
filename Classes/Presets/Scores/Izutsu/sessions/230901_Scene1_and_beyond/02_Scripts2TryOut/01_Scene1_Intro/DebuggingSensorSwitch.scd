//emulate sensor input to check why sensors stop working in snippet 3
//(index 2) of IntroWithControls.
//:jun on x5 > 0.5
LocalAddr().sendMsg('/minibee/data', 5, 0.52, 0.2, 0.5);
//:jun off x5 < 0.5;
LocalAddr().sendMsg('/minibee/data', 5, 0.48, 0.2, 0.5);