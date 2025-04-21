// 月 21  4 2025 11:38
// Test pushing environments:
// =========== 1
// From the default current environment of local user,
// push a new environment in the local user
// ---- Start of test 1 here:
// Recompile library and test that currentEnvironment is/has local user
currentEnvironment;
// currentEnvironment should always be identical to User.local.envir
User.local.envir;
// push localusertest1 to local user and currentEnvironment:
\lx.push;
// test current environment and confirm:
currentEnvironment;
currentEnvironment.user;
currentEnvironment.user.envirs do: _.postln;
~user; // user is also reachable as environment variable.
// =========== 2
// From the default current environment of the local user,
// get and push the default environment of another user
\dx push: \yunus;
currentEnvironment; // user yunus, environment dx
currentEnvironment.user; // User yunus;
currentEnvironment.user.envirs; // dx: only user envir used so far
~user; // user \yunus
// =========== 3
// From the default current environment of the local user,
// get and bush an arbitrary environment of another user
User.pop;
User.all do: _.postln;
// =========== 4
// From an arbitrary environment of an arbitrary user,
// - switch back to the home environment of the local user
// =========== 5
// From an arbitrary environment of an arbitrary user,
// - switch to an arbitrary environment of the local user