value(z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),'Z_callerM',Z_callerM).
setValue(z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_NEW_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),'Z_callerM',Z_NEW_callerM).
value(z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),'Z_callerD',Z_callerD).
setValue(z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM,Z_NEW_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),'Z_callerD',Z_NEW_callerD).
value(z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),'Z_callerDao',Z_callerDao).
setValue(z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM,Z_callerD,Z_NEW_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),'Z_callerDao',Z_NEW_callerDao).
value(z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),'Z_caller',Z_caller).
setValue(z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_NEW_caller,Z_components,Z_connectors,Z_ports),'Z_caller',Z_NEW_caller).
value(z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),'Z_components',Z_components).
setValue(z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_NEW_components,Z_connectors,Z_ports),'Z_components',Z_NEW_components).
value(z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),'Z_connectors',Z_connectors).
setValue(z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_NEW_connectors,Z_ports),'Z_connectors',Z_NEW_connectors).
value(z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),'Z_ports',Z_ports).
setValue(z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_PrintRouter(globalState(This,Z_State,Z_State),Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports).
z_init_PrintRouter(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV)):-
	new(This,'Port',Z_TEMP2),
	Z_callerM_DECORV=Z_TEMP2,
	setIvar(Z_callerM_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP3),
	Z_callerD_DECORV=Z_TEMP3,
	setIvar(Z_callerD_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP4),
	Z_callerDao_DECORV=Z_TEMP4,
	setIvar(Z_callerDao_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP5),
	Z_caller_DECORV=Z_TEMP5,
	setIvar(Z_caller_DECORV,'Z_element',This),
	Z_components_DECORV=[],
	Z_connectors_DECORV=[],
	Z_ports_DECORV=[Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV]
	,addChangeOp(Z_State,z_init_PrintRouter,[va('Z_callerM'),va('Z_callerD'),va('Z_callerDao'),va('Z_caller'),va('Z_components'),va('Z_connectors'),va('Z_ports')],Z_NewState).

z_AddUser(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=addUser,
	sendV(Z_callerDao,execute,['z_Initiate',[addUser,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_AddUser,[],Z_NewState),
	z_PrintRouter(globalState(This,Z_NewState,Z_NewState),Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports).

z_DeleteUser(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=deleteUser,
	sendV(Z_callerDao,execute,['z_Initiate',[deleteUser,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_DeleteUser,[],Z_NewState),
	z_PrintRouter(globalState(This,Z_NewState,Z_NewState),Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports).

z_VerifyPermission(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=verifyPermission,
	sendV(Z_callerDao,execute,['z_Initiate',[verifyPermission,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_VerifyPermission,[],Z_NewState),
	z_PrintRouter(globalState(This,Z_NewState,Z_NewState),Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports).

z_ValidateUser(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=validateUser,
	sendV(Z_callerDao,execute,['z_Initiate',[validateUser,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_ValidateUser,[],Z_NewState),
	z_PrintRouter(globalState(This,Z_NewState,Z_NewState),Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports).

z_ListUsers(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=listUsers,
	sendV(Z_callerDao,execute,['z_Initiate',[listUsers,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_ListUsers,[],Z_NewState),
	z_PrintRouter(globalState(This,Z_NewState,Z_NewState),Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports).

z_ListPrinters(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=listPrinters,
	sendV(Z_callerD,execute,['z_Initiate',[listPrinters,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_ListPrinters,[],Z_NewState),
	z_PrintRouter(globalState(This,Z_NewState,Z_NewState),Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports).

z_VerifyPrinter(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=verifyPrinter,
	sendV(Z_callerD,execute,['z_Initiate',[verifyPrinter,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_VerifyPrinter,[],Z_NewState),
	z_PrintRouter(globalState(This,Z_NewState,Z_NewState),Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports).

z_PrinterState(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=printerState,
	sendV(Z_callerD,execute,['z_Initiate',[printerState,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_PrinterState,[],Z_NewState),
	z_PrintRouter(globalState(This,Z_NewState,Z_NewState),Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports).

z_FirstPrinterAvailable(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=firstPrinterAvailable,
	sendV(Z_callerD,execute,['z_Initiate',[firstPrinterAvailable,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_FirstPrinterAvailable,[],Z_NewState),
	z_PrintRouter(globalState(This,Z_NewState,Z_NewState),Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports).

z_FirstPrinterOccupied(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=firstPrinterOccupied,
	sendV(Z_callerD,execute,['z_Initiate',[firstPrinterOccupied,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_FirstPrinterOccupied,[],Z_NewState),
	z_PrintRouter(globalState(This,Z_NewState,Z_NewState),Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports).

z_AddPrinter(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=addPrinter,
	sendV(Z_callerD,execute,['z_Initiate',[addPrinter,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_AddPrinter,[],Z_NewState),
	z_PrintRouter(globalState(This,Z_NewState,Z_NewState),Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports).

z_DeletePrinter(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=deletePrinter,
	sendV(Z_callerD,execute,['z_Initiate',[deletePrinter,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_DeletePrinter,[],Z_NewState),
	z_PrintRouter(globalState(This,Z_NewState,Z_NewState),Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports).

z_AddCurrentPrinting(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=addCurrentPrinting,
	sendV(Z_callerD,execute,['z_Initiate',[addCurrentPrinting,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_AddCurrentPrinting,[],Z_NewState),
	z_PrintRouter(globalState(This,Z_NewState,Z_NewState),Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports).

z_DeleteCurrentPrinting(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=deleteCurrentPrinting,
	sendV(Z_callerD,execute,['z_Initiate',[deleteCurrentPrinting,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_DeleteCurrentPrinting,[],Z_NewState),
	z_PrintRouter(globalState(This,Z_NewState,Z_NewState),Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports).

z_AddJobPrintingQueue(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=addJobPrintingQueue,
	sendV(Z_callerD,execute,['z_Initiate',[addJobPrintingQueue,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_AddJobPrintingQueue,[],Z_NewState),
	z_PrintRouter(globalState(This,Z_NewState,Z_NewState),Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports).

z_DeleteJobPrintingQueue(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=deleteJobPrintingQueue,
	sendV(Z_callerD,execute,['z_Initiate',[deleteJobPrintingQueue,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_DeleteJobPrintingQueue,[],Z_NewState),
	z_PrintRouter(globalState(This,Z_NewState,Z_NewState),Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports).

z_JobToPrinter(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=jobToPrinter,
	sendV(Z_callerD,execute,['z_Initiate',[jobToPrinter,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_JobToPrinter,[],Z_NewState),
	z_PrintRouter(globalState(This,Z_NewState,Z_NewState),Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_AddUser(globalState(This,Z_State,Z_TEMP1),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_DeleteUser(globalState(This,Z_State,Z_TEMP1),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_VerifyPermission(globalState(This,Z_State,Z_TEMP1),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_ValidateUser(globalState(This,Z_State,Z_TEMP1),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_ListUsers(globalState(This,Z_State,Z_TEMP1),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_ListPrinters(globalState(This,Z_State,Z_TEMP1),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_VerifyPrinter(globalState(This,Z_State,Z_TEMP1),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_PrinterState(globalState(This,Z_State,Z_TEMP1),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_FirstPrinterAvailable(globalState(This,Z_State,Z_TEMP1),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_FirstPrinterOccupied(globalState(This,Z_State,Z_TEMP1),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_AddPrinter(globalState(This,Z_State,Z_TEMP1),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_DeletePrinter(globalState(This,Z_State,Z_TEMP1),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_JobToPrinter(globalState(This,Z_State,Z_TEMP1),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_AddCurrentPrinting(globalState(This,Z_State,Z_TEMP1),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_DeleteCurrentPrinting(globalState(This,Z_State,Z_TEMP1),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_AddJobPrintingQueue(globalState(This,Z_State,Z_TEMP1),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_DeleteJobPrintingQueue(globalState(This,Z_State,Z_TEMP1),z_PrintRouter(Z_callerM,Z_callerD,Z_callerDao,Z_caller,Z_components,Z_connectors,Z_ports),z_PrintRouter(Z_callerM_DECORV,Z_callerD_DECORV,Z_callerDao_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

init_PrintRouter_stateIni(tree('Root',[tree('PrintRouter',['callerM','callerD','callerDao','caller','components','connectors','ports'])])).
