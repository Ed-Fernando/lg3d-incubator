value(z_Element(Z_ports),'Z_ports',Z_ports).
setValue(z_Element(Z_ports),z_Element(Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_Element(globalState(This,Z_State,Z_State),Z_ports):-
	0=0.

z_init_Element(globalState(This,Z_State,Z_NewState),z_Element(Z_ports_DECORV)):-
	Z_ports_DECORV=[]
	,addChangeOp(Z_State,z_init_Element,[va('Z_ports')],Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Element(Z_ports),z_Element(Z_ports),Z_event,Z_instance,Z_input,Z_output):-
	0=0,
	addChangeOp(Z_State,z_ProcessEvent,[],Z_NewState),
	z_Element(globalState(This,Z_NewState,Z_NewState),Z_ports).

init_Element_stateIni(tree('Root',[tree('Element',['ports'])])).
