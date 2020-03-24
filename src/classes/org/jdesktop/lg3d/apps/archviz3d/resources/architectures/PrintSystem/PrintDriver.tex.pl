value(z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),'Z_caller',Z_caller).
setValue(z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_NEW_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),'Z_caller',Z_NEW_caller).
value(z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),'Z_called',Z_called).
setValue(z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_NEW_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),'Z_called',Z_NEW_called).
value(z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),'Z_calledP1',Z_calledP1).
setValue(z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_NEW_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),'Z_calledP1',Z_NEW_calledP1).
value(z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),'Z_calledP2',Z_calledP2).
setValue(z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_NEW_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),'Z_calledP2',Z_NEW_calledP2).
value(z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),'Z_printingQueue',Z_printingQueue).
setValue(z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_NEW_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),'Z_printingQueue',Z_NEW_printingQueue).
value(z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),'Z_currentPrinting',Z_currentPrinting).
setValue(z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_NEW_currentPrinting,Z_components,Z_connectors,Z_ports),'Z_currentPrinting',Z_NEW_currentPrinting).
value(z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),'Z_components',Z_components).
setValue(z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_NEW_components,Z_connectors,Z_ports),'Z_components',Z_NEW_components).
value(z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),'Z_connectors',Z_connectors).
setValue(z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_NEW_connectors,Z_ports),'Z_connectors',Z_NEW_connectors).
value(z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),'Z_ports',Z_ports).
setValue(z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_PrintDriver(globalState(This,Z_State,Z_State),Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports).
z_init_PrintDriver(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV)):-
	new(This,'Port',Z_TEMP2),
	Z_caller_DECORV=Z_TEMP2,
	setIvar(Z_caller_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP3),
	Z_called_DECORV=Z_TEMP3,
	setIvar(Z_called_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP4),
	Z_calledP1_DECORV=Z_TEMP4,
	setIvar(Z_calledP1_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP5),
	Z_calledP2_DECORV=Z_TEMP5,
	setIvar(Z_calledP2_DECORV,'Z_element',This),
	Z_printingQueue_DECORV=[tuple(2,2,job2)],
	Z_currentPrinting_DECORV=[tuple(2,1,job1)],
	Z_components_DECORV=[],
	Z_connectors_DECORV=[],
	Z_ports_DECORV=[Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV]
	,addChangeOp(Z_State,z_init_PrintDriver,[va('Z_caller'),va('Z_called'),va('Z_calledP1'),va('Z_calledP2'),va('Z_printingQueue'),va('Z_currentPrinting'),va('Z_components'),va('Z_connectors'),va('Z_ports')],Z_NewState).

z_ListPrinters(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=listPrinters,
	sendV(Z_called,execute,['z_Initiate',[listPrinters,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_ListPrinters,[],Z_NewState),
	z_PrintDriver(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports).

z_VerifyPrinter(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=verifyPrinter,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
	SE=[Printer],
	sendV(Z_called,execute,['z_Initiate',[verifyPrinter,This,SE,Z_outputP]],_),
	addChangeOp(Z_State,z_VerifyPrinter,[],Z_NewState),
	z_PrintDriver(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports).

z_PrinterStateOk(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=printerState,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
	SE=[Printer],
	sendV(Z_called,execute,['z_Initiate',[verifyPrinter,This,SE,R]],_),
	R='printer_exist',
	sendV(Z_called,execute,['z_Initiate',[printerState,This,SE,Z_outputP]],_),
	addChangeOp(Z_State,z_PrinterStateOk,[],Z_NewState),
	z_PrintDriver(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports).

z_PrinterStateFailed(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=printerState,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
	SE=[Printer],
	sendV(Z_called,execute,['z_Initiate',[verifyPrinter,This,SE,R]],_),
	R='printer_not_exist',
	Z_outputP=R,
	addChangeOp(Z_State,z_PrinterStateFailed,[],Z_NewState),
	z_PrintDriver(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports).

z_FirstPrinterAvailable(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=firstPrinterAvailable,
	sendV(Z_called,execute,['z_Initiate',[firstPrinterAvailable,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_FirstPrinterAvailable,[],Z_NewState),
	z_PrintDriver(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports).

z_FirstPrinterOccupied(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=firstPrinterOccupied,
	sendV(Z_called,execute,['z_Initiate',[firstPrinterOccupied,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_FirstPrinterOccupied,[],Z_NewState),
	z_PrintDriver(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports).

z_AddPrinterOk(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=addPrinter,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
	SE=[Printer],
	sendV(Z_called,execute,['z_Initiate',[verifyPrinter,This,SE,V]],_),
	V='printer_not_exist',
	sendV(Z_called,execute,['z_Initiate',[addPrinter,This,Z_inputP,P]],_),
	P='add_printer_ok',
	Z_report=P,
	addChangeOp(Z_State,z_AddPrinterOk,[],Z_NewState),
	z_PrintDriver(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports).

z_AddPrinterFailed(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=addPrinter,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
	SE=[Printer],
	sendV(Z_called,execute,['z_Initiate',[verifyPrinter,This,SE,V]],_),
	V='printer_exist',
	Z_report=V,
	addChangeOp(Z_State,z_AddPrinterFailed,[],Z_NewState),
	z_PrintDriver(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports).

z_DeletePrinterOk(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=deletePrinter,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
	SE=[Printer],
	sendV(Z_called,execute,['z_Initiate',[verifyPrinter,This,SE,V]],_),
	V='printer_exist',
	sendV(Z_called,execute,['z_Initiate',[deletePrinter,This,Z_inputP,P]],_),
	P='delete_printer_ok',
	Z_report=P,
	addChangeOp(Z_State,z_DeletePrinterOk,[],Z_NewState),
	z_PrintDriver(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports).

z_DeletePrinterFailed(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=deletePrinter,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
	SE=[Printer],
	sendV(Z_called,execute,['z_Initiate',[verifyPrinter,This,SE,V]],_),
	V='printer_not_exist',
	Z_report=V,
	addChangeOp(Z_State,z_DeletePrinterFailed,[],Z_NewState),
	z_PrintDriver(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports).

z_AddCurrentPrinting(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting_DECORV,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=addCurrentPrinting,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	User=Z_TEMP3,
	tail(Z_inputP,Z_TEMP4),
	tail(Z_TEMP4,Z_TEMP5),
	head(Z_TEMP5,Z_TEMP6),
	Job=Z_TEMP6,
	uni(Z_currentPrinting,[tuple(Printer,User,Job)],Z_TEMP7),
	Z_currentPrinting_DECORV=Z_TEMP7,
	Z_report='printing_assigned',
	addChangeOp(Z_State,z_AddCurrentPrinting,[va('Z_currentPrinting')],Z_NewState),
	z_PrintDriver(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting_DECORV,Z_components,Z_connectors,Z_ports).

z_DeleteCurrentPrinting(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting_DECORV,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=deleteCurrentPrinting,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	User=Z_TEMP3,
	tail(Z_inputP,Z_TEMP4),
	tail(Z_TEMP4,Z_TEMP5),
	head(Z_TEMP5,Z_TEMP6),
	Job=Z_TEMP6,
	setminus(Z_currentPrinting,[tuple(Printer,User,Job)],Z_TEMP7),
	Z_currentPrinting_DECORV=Z_TEMP7,
	Z_report='printing_unassigned',
	addChangeOp(Z_State,z_DeleteCurrentPrinting,[va('Z_currentPrinting')],Z_NewState),
	z_PrintDriver(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting_DECORV,Z_components,Z_connectors,Z_ports).

z_AddJobPrintingQueue(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue_DECORV,Z_currentPrinting,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=addJobPrintingQueue,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	User=Z_TEMP3,
	tail(Z_inputP,Z_TEMP4),
	tail(Z_TEMP4,Z_TEMP5),
	head(Z_TEMP5,Z_TEMP6),
	Job=Z_TEMP6,
	uni(Z_printingQueue,[tuple(Printer,User,Job)],Z_TEMP7),
	Z_printingQueue_DECORV=Z_TEMP7,
	Z_report='add_job_printing_queue',
	addChangeOp(Z_State,z_AddJobPrintingQueue,[va('Z_printingQueue')],Z_NewState),
	z_PrintDriver(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue_DECORV,Z_currentPrinting,Z_components,Z_connectors,Z_ports).

z_DeleteJobPrintingQueue(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue_DECORV,Z_currentPrinting,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=deleteJobPrintingQueue,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	User=Z_TEMP3,
	tail(Z_inputP,Z_TEMP4),
	tail(Z_TEMP4,Z_TEMP5),
	head(Z_TEMP5,Z_TEMP6),
	Job=Z_TEMP6,
	setminus(Z_printingQueue,[tuple(Printer,User,Job)],Z_TEMP7),
	Z_printingQueue_DECORV=Z_TEMP7,
	Z_report='delete_job_printing_queue',
	addChangeOp(Z_State,z_DeleteJobPrintingQueue,[va('Z_printingQueue')],Z_NewState),
	z_PrintDriver(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue_DECORV,Z_currentPrinting,Z_components,Z_connectors,Z_ports).

z_ListCurrentPrinting(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=listCurrentPrinting,
	Z_report=Z_currentPrinting,
	addChangeOp(Z_State,z_ListCurrentPrinting,[],Z_NewState),
	z_PrintDriver(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports).

z_ListPrintingQueue(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=listPrintingQueue,
	Z_report=Z_printingQueue,
	addChangeOp(Z_State,z_ListPrintingQueue,[],Z_NewState),
	z_PrintDriver(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports).

z_JobToPrinterPrinter1(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=jobToPrinter,
	nth(tuple(Z_inputP,1),Z_TEMP1),
	Z_TEMP1=1,
	sendV(Z_calledP1,execute,['z_Initiate',[jobToPrinter,This,Z_inputP,R]],_),
	R='printing_finished',
	Z_report='printing_finished_in_printer1',
	addChangeOp(Z_State,z_JobToPrinterPrinter1,[],Z_NewState),
	z_PrintDriver(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports).

z_JobToPrinterPrinter2(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=jobToPrinter,
	nth(tuple(Z_inputP,1),Z_TEMP1),
	Z_TEMP1=2,
	sendV(Z_calledP2,execute,['z_Initiate',[jobToPrinter,This,Z_inputP,R]],_),
	R='printing_finished',
	Z_report='printing_finished_in_printer2',
	addChangeOp(Z_State,z_JobToPrinterPrinter2,[],Z_NewState),
	z_PrintDriver(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_ListPrinters(globalState(This,Z_State,Z_TEMP1),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_VerifyPrinter(globalState(This,Z_State,Z_TEMP1),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_PrinterStateOk(globalState(This,Z_State,Z_TEMP1),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_PrinterStateFailed(globalState(This,Z_State,Z_TEMP1),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_FirstPrinterAvailable(globalState(This,Z_State,Z_TEMP1),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_FirstPrinterOccupied(globalState(This,Z_State,Z_TEMP1),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AddPrinterOk(globalState(This,Z_State,Z_TEMP1),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AddPrinterFailed(globalState(This,Z_State,Z_TEMP1),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_DeletePrinterOk(globalState(This,Z_State,Z_TEMP1),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_DeletePrinterFailed(globalState(This,Z_State,Z_TEMP1),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AddCurrentPrinting(globalState(This,Z_State,Z_TEMP1),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_DeleteCurrentPrinting(globalState(This,Z_State,Z_TEMP1),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AddJobPrintingQueue(globalState(This,Z_State,Z_TEMP1),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_DeleteJobPrintingQueue(globalState(This,Z_State,Z_TEMP1),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_ListCurrentPrinting(globalState(This,Z_State,Z_TEMP1),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_ListPrintingQueue(globalState(This,Z_State,Z_TEMP1),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_JobToPrinterPrinter1(globalState(This,Z_State,Z_TEMP1),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_JobToPrinterPrinter2(globalState(This,Z_State,Z_TEMP1),z_PrintDriver(Z_caller,Z_called,Z_calledP1,Z_calledP2,Z_printingQueue,Z_currentPrinting,Z_components,Z_connectors,Z_ports),z_PrintDriver(Z_caller_DECORV,Z_called_DECORV,Z_calledP1_DECORV,Z_calledP2_DECORV,Z_printingQueue_DECORV,Z_currentPrinting_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

init_PrintDriver_stateIni(tree('Root',[tree('PrintDriver',['caller','called','calledP1','calledP2','printingQueue','currentPrinting','components','connectors','ports'])])).
