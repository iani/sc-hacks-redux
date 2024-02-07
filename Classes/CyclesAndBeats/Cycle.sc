//Wed  7 Feb 2024 19:25
//Play cycles using TDuty and Dbufrd to define their subdivisions
//Each Cycle instance is responsible for one cycle.
//Tempo (beats per second) of the cycle is variable.
//The cycle shares subdivision onsets in 3 ways:
//1. By writing them as impulses in a control-rate Bus
//2. By broadcasting them as triggers via Osc (SendReply).
//3. By issuing a changed message from the Cycle instance itself.

Cycle {

}