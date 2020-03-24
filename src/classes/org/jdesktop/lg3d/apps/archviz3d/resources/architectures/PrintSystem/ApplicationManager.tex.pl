value(z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),'Z_caller',Z_caller).
setValue(z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_NEW_caller,Z_called,Z_components,Z_connectors,Z_ports),'Z_caller',Z_NEW_caller).
value(z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),'Z_called',Z_called).
setValue(z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller,Z_NEW_called,Z_components,Z_connectors,Z_ports),'Z_called',Z_NEW_called).
value(z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),'Z_components',Z_components).
setValue(z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller,Z_called,Z_NEW_components,Z_connectors,Z_ports),'Z_components',Z_NEW_components).
value(z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),'Z_connectors',Z_connectors).
setValue(z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_NEW_connectors,Z_ports),'Z_connectors',Z_NEW_connectors).
value(z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),'Z_ports',Z_ports).
setValue(z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_ApplicationManager(globalState(This,Z_State,Z_State),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).
z_init_ApplicationManager(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV)):-
	new(This,'Port',Z_TEMP2),
	Z_caller_DECORV=Z_TEMP2,
	setIvar(Z_caller_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP3),
	Z_called_DECORV=Z_TEMP3,
	setIvar(Z_called_DECORV,'Z_element',This),
	Z_components_DECORV=[],
	Z_connectors_DECORV=[],
	Z_ports_DECORV=[Z_caller_DECORV,Z_called_DECORV]
	,addChangeOp(Z_State,z_init_ApplicationManager,[va('Z_caller'),va('Z_called'),va('Z_components'),va('Z_connectors'),va('Z_ports')],Z_NewState).

z_ListPrinters(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=listPrinters,
	sendV(Z_called,execute,['z_Initiate',[listPrinters,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_ListPrinters,[],Z_NewState),
	z_ApplicationManager(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_VerifyPrinter(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=verifyPrinter,
	sendV(Z_called,execute,['z_Initiate',[verifyPrinter,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_VerifyPrinter,[],Z_NewState),
	z_ApplicationManager(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_PrinterState(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=printerState,
	sendV(Z_called,execute,['z_Initiate',[printerState,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_PrinterState,[],Z_NewState),
	z_ApplicationManager(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_FirstPrinterAvailable(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=firstPrinterAvailable,
	sendV(Z_called,execute,['z_Initiate',[firstPrinterAvailable,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_FirstPrinterAvailable,[],Z_NewState),
	z_ApplicationManager(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_FirstPrinterOccupied(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=firstPrinterOccupied,
	sendV(Z_called,execute,['z_Initiate',[firstPrinterOccupied,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_FirstPrinterOccupied,[],Z_NewState),
	z_ApplicationManager(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_AddPrinter(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=addPrinter,
	sendV(Z_called,execute,['z_Initiate',[addPrinter,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_AddPrinter,[],Z_NewState),
	z_ApplicationManager(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_DeletePrinter(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=deletePrinter,
	sendV(Z_called,execute,['z_Initiate',[deletePrinter,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_DeletePrinter,[],Z_NewState),
	z_ApplicationManager(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_JobToPrinterFinished(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=jobToPrinter,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
	PR=[Printer],
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	User=Z_TEMP3,
	SE=[User],
	sendV(Z_called,execute,['z_Initiate',[validateUser,This,SE,R]],_),
	R='exist_user',
	sendV(Z_called,execute,['z_Initiate',[verifyPrinter,This,PR,RE]],_),
	RE='printer_exist',
	SV=[Printer,User],
	sendV(Z_called,execute,['z_Initiate',[verifyPermission,This,SV,RV]],_),
	RV='permission_accepted',
	sendV(Z_called,execute,['z_Initiate',[printerState,This,PR,RA]],_),
	RA='printer_available',
	sendV(Z_called,execute,['z_Initiate',[addCurrentPrinting,This,Z_inputP,RS]],_),
	RS='printing_assigned',
	sendV(Z_called,execute,['z_Initiate',[jobToPrinter,This,Z_inputP,RF]],_),
	sendV(Z_called,execute,['z_Initiate',[deleteCurrentPrinting,This,Z_inputP,RU]],_),
	RU='printing_unassigned',
	Z_report=RF,
	addChangeOp(Z_State,z_JobToPrinterFinished,[],Z_NewState),
	z_ApplicationManager(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_JobToPrinterQueue(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=jobToPrinter,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
	PR=[Printer],
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	User=Z_TEMP3,
	SE=[User],
	sendV(Z_called,execute,['z_Initiate',[validateUser,This,SE,R]],_),
	R='exist_user',
	sendV(Z_called,execute,['z_Initiate',[verifyPrinter,This,PR,RE]],_),
	RE='printer_exist',
	SV=[Printer,User],
	sendV(Z_called,execute,['z_Initiate',[verifyPermission,This,SV,RV]],_),
	RV='permission_accepted',
	sendV(Z_called,execute,['z_Initiate',[printerState,This,PR,RO]],_),
	RO='printer_occupied',
	sendV(Z_called,execute,['z_Initiate',[addJobPrintingQueue,This,Z_inputP,RA]],_),
	RA='add_job_printing_queue',
	Z_report=RA,
	addChangeOp(Z_State,z_JobToPrinterQueue,[],Z_NewState),
	z_ApplicationManager(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_JobToPrinterNonAvailable(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=jobToPrinter,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
	PR=[Printer],
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	User=Z_TEMP3,
	SE=[User],
	sendV(Z_called,execute,['z_Initiate',[validateUser,This,SE,R]],_),
	R='exist_user',
	sendV(Z_called,execute,['z_Initiate',[verifyPrinter,This,PR,RE]],_),
	RE='printer_exist',
	SV=[Printer,User],
	sendV(Z_called,execute,['z_Initiate',[verifyPermission,This,SV,RV]],_),
	RV='permission_accepted',
	sendV(Z_called,execute,['z_Initiate',[printerState,This,PR,RN]],_),
	RN='printer_non_available',
	Z_report=RN,
	addChangeOp(Z_State,z_JobToPrinterNonAvailable,[],Z_NewState),
	z_ApplicationManager(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_JobToPrinterPermissionRejected(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=jobToPrinter,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
	PR=[Printer],
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	User=Z_TEMP3,
	SE=[User],
	sendV(Z_called,execute,['z_Initiate',[validateUser,This,SE,R]],_),
	R='exist_user',
	sendV(Z_called,execute,['z_Initiate',[verifyPrinter,This,PR,RE]],_),
	RE='printer_exist',
	SV=[Printer,User],
	sendV(Z_called,execute,['z_Initiate',[verifyPermission,This,SV,RV]],_),
	RV='permission_rejected',
	Z_report=RV,
	addChangeOp(Z_State,z_JobToPrinterPermissionRejected,[],Z_NewState),
	z_ApplicationManager(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_JobToPrinterNotExistUser(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=jobToPrinter,
	tail(Z_inputP,Z_TEMP1),
	head(Z_TEMP1,Z_TEMP2),
	User=Z_TEMP2,
	SE=[User],
	sendV(Z_called,execute,['z_Initiate',[validateUser,This,SE,R]],_),
	R='not_exist_user',
	Z_report=R,
	addChangeOp(Z_State,z_JobToPrinterNotExistUser,[],Z_NewState),
	z_ApplicationManager(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_JobToPrinterNotExist(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=jobToPrinter,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
	PR=[Printer],
	sendV(Z_called,execute,['z_Initiate',[verifyPrinter,This,PR,RE]],_),
	RE='printer_not_exist',
	Z_report=RE,
	addChangeOp(Z_State,z_JobToPrinterNotExist,[],Z_NewState),
	z_ApplicationManager(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_JobToPrintersPoolFinished(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=jobToPrintersPool,
	head(Z_inputP,Z_TEMP1),
	User=Z_TEMP1,
	SE=[User],
	sendV(Z_called,execute,['z_Initiate',[validateUser,This,SE,R]],_),
	R='exist_user',
	SA=[],
	sendV(Z_called,execute,['z_Initiate',[firstPrinterAvailable,This,SA,RA]],_),
	RA\=[],
	head(Z_inputP,Z_TEMP2),
	User=Z_TEMP2,
	tail(Z_inputP,Z_TEMP3),
	head(Z_TEMP3,Z_TEMP4),
	Job=Z_TEMP4,
	SV=[RA,User],
	sendV(Z_called,execute,['z_Initiate',[verifyPermission,This,SV,RV]],_),
	RV='permission_accepted',
	SC=[RA,User,Job],
	sendV(Z_called,execute,['z_Initiate',[addCurrentPrinting,This,SC,RS]],_),
	RS='printing_assigned',
	sendV(Z_called,execute,['z_Initiate',[jobToPrinter,This,SC,RF]],_),
	sendV(Z_called,execute,['z_Initiate',[deleteCurrentPrinting,This,SC,RU]],_),
	RU='printing_unassigned',
	Z_report=RF,
	addChangeOp(Z_State,z_JobToPrintersPoolFinished,[],Z_NewState),
	z_ApplicationManager(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_JobToPrintersPoolQueue(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=jobToPrintersPool,
	head(Z_inputP,Z_TEMP1),
	User=Z_TEMP1,
	SE=[User],
	sendV(Z_called,execute,['z_Initiate',[validateUser,This,SE,R]],_),
	R='exist_user',
	SA=[],
	sendV(Z_called,execute,['z_Initiate',[firstPrinterOccupied,This,SA,RO]],_),
	RO\=[],
	head(Z_inputP,Z_TEMP2),
	User=Z_TEMP2,
	tail(Z_inputP,Z_TEMP3),
	head(Z_TEMP3,Z_TEMP4),
	Job=Z_TEMP4,
	SV=[RO,User],
	sendV(Z_called,execute,['z_Initiate',[verifyPermission,This,SV,RV]],_),
	RV='permission_accepted',
	SC=[RO,User,Job],
	sendV(Z_called,execute,['z_Initiate',[addJobPrintingQueue,This,SC,RA]],_),
	RA='add_job_printing_queue',
	Z_report=RA,
	addChangeOp(Z_State,z_JobToPrintersPoolQueue,[],Z_NewState),
	z_ApplicationManager(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_JobToPrintersPoolNonAvailable(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=jobToPrintersPool,
	head(Z_inputP,Z_TEMP1),
	User=Z_TEMP1,
	SE=[User],
	sendV(Z_called,execute,['z_Initiate',[validateUser,This,SE,R]],_),
	R='exist_user',
	SA=[],
	sendV(Z_called,execute,['z_Initiate',[firstPrinterAvailable,This,SA,RA]],_),
	RA=[],
	sendV(Z_called,execute,['z_Initiate',[firstPrinterOccupied,This,SA,RO]],_),
	RO=[],
	Z_report='printer_non_available',
	addChangeOp(Z_State,z_JobToPrintersPoolNonAvailable,[],Z_NewState),
	z_ApplicationManager(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_JobToPrintersPoolPermissionRejected(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=jobToPrintersPool,
	head(Z_inputP,Z_TEMP1),
	User=Z_TEMP1,
	SE=[User],
	sendV(Z_called,execute,['z_Initiate',[validateUser,This,SE,R]],_),
	R='exist_user',
	SA=[],
	sendV(Z_called,execute,['z_Initiate',[firstPrinterOccupied,This,SA,RO]],_),
	RO\=[],
	head(Z_inputP,Z_TEMP2),
	User=Z_TEMP2,
	tail(Z_inputP,Z_TEMP3),
	head(Z_TEMP3,Z_TEMP4),
	Job=Z_TEMP4,
	SV=[RO,User],
	sendV(Z_called,execute,['z_Initiate',[verifyPermission,This,SV,RV]],_),
	RV='permission_rejected',
	Z_report=RV,
	addChangeOp(Z_State,z_JobToPrintersPoolPermissionRejected,[],Z_NewState),
	z_ApplicationManager(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_JobToPrintersPoolNotExistUser(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=jobToPrintersPool,
	head(Z_inputP,Z_TEMP1),
	User=Z_TEMP1,
	SE=[User],
	sendV(Z_called,execute,['z_Initiate',[validateUser,This,SE,R]],_),
	R='not_exist_user',
	Z_report=R,
	addChangeOp(Z_State,z_JobToPrintersPoolNotExistUser,[],Z_NewState),
	z_ApplicationManager(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_ListPrinters(globalState(This,Z_State,Z_TEMP1),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_VerifyPrinter(globalState(This,Z_State,Z_TEMP1),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_PrinterState(globalState(This,Z_State,Z_TEMP1),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_FirstPrinterAvailable(globalState(This,Z_State,Z_TEMP1),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_FirstPrinterOccupied(globalState(This,Z_State,Z_TEMP1),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_AddPrinter(globalState(This,Z_State,Z_TEMP1),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_DeletePrinter(globalState(This,Z_State,Z_TEMP1),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_JobToPrinterFinished(globalState(This,Z_State,Z_TEMP1),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_JobToPrinterQueue(globalState(This,Z_State,Z_TEMP1),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_JobToPrinterNonAvailable(globalState(This,Z_State,Z_TEMP1),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_JobToPrinterPermissionRejected(globalState(This,Z_State,Z_TEMP1),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_JobToPrinterNotExistUser(globalState(This,Z_State,Z_TEMP1),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_JobToPrinterNotExist(globalState(This,Z_State,Z_TEMP1),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_JobToPrintersPoolFinished(globalState(This,Z_State,Z_TEMP1),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_JobToPrintersPoolQueue(globalState(This,Z_State,Z_TEMP1),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_JobToPrintersPoolNonAvailable(globalState(This,Z_State,Z_TEMP1),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_JobToPrintersPoolPermissionRejected(globalState(This,Z_State,Z_TEMP1),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_JobToPrintersPoolNotExistUser(globalState(This,Z_State,Z_TEMP1),z_ApplicationManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

init_ApplicationManager_stateIni(tree('Root',[tree('ApplicationManager',['caller','called','components','connectors','ports'])])).
