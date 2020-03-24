value(z_Port(Z_number,Z_element,Z_next),'Z_number',Z_number).
setValue(z_Port(Z_number,Z_element,Z_next),z_Port(Z_NEW_number,Z_element,Z_next),'Z_number',Z_NEW_number).
value(z_Port(Z_number,Z_element,Z_next),'Z_element',Z_element).
setValue(z_Port(Z_number,Z_element,Z_next),z_Port(Z_number,Z_NEW_element,Z_next),'Z_element',Z_NEW_element).
value(z_Port(Z_number,Z_element,Z_next),'Z_next',Z_next).
setValue(z_Port(Z_number,Z_element,Z_next),z_Port(Z_number,Z_element,Z_NEW_next),'Z_next',Z_NEW_next).

z_Port(globalState(This,Z_State,Z_State),Z_number,Z_element,Z_next):-
	0=0.

z_init_Port(globalState(This,Z_State,Z_NewState),z_Port(Z_number_DECORV,Z_element_DECORV,Z_next_DECORV)):-
	Z_number_DECORV=0,
	Z_next_DECORV=0,
	Z_element_DECORV=0
	,addChangeOp(Z_State,z_init_Port,[va('Z_number'),va('Z_element'),va('Z_next')],Z_NewState).

z_Initiate(globalState(This,Z_State,Z_NewState),z_Port(Z_number,Z_element,Z_next),z_Port(Z_number,Z_element,Z_next),Z_event,Z_instance,Z_inputP,Z_outputP):-
	sendV(Z_next,execute,['z_ProcessInitiate',[Z_event,Z_instance,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_Initiate,[],Z_NewState),
	z_Port(globalState(This,Z_NewState,Z_NewState),Z_number,Z_element,Z_next).

z_ProcessInitiate(globalState(This,Z_State,Z_NewState),z_Port(Z_number,Z_element,Z_next),z_Port(Z_number,Z_element,Z_next),Z_event,Z_instance,Z_inputP,Z_outputP):-
	sendV(Z_element,execute,['z_ProcessEvent',[Z_event,Z_instance,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_ProcessInitiate,[],Z_NewState),
	z_Port(globalState(This,Z_NewState,Z_NewState),Z_number,Z_element,Z_next).

init_Port_stateIni(tree('Root',[tree('Port',['number','element','next'])])).
