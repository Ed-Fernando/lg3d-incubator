value(z_BankSystem(Z_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports),'Z_broker',Z_broker).
setValue(z_BankSystem(Z_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports),z_BankSystem(Z_NEW_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports),'Z_broker',Z_NEW_broker).
value(z_BankSystem(Z_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports),'Z_client',Z_client).
setValue(z_BankSystem(Z_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports),z_BankSystem(Z_broker,Z_NEW_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports),'Z_client',Z_NEW_client).
value(z_BankSystem(Z_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports),'Z_server',Z_server).
setValue(z_BankSystem(Z_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports),z_BankSystem(Z_broker,Z_client,Z_NEW_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports),'Z_server',Z_NEW_server).
value(z_BankSystem(Z_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports),'Z_rpc1',Z_rpc1).
setValue(z_BankSystem(Z_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports),z_BankSystem(Z_broker,Z_client,Z_server,Z_NEW_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports),'Z_rpc1',Z_NEW_rpc1).
value(z_BankSystem(Z_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports),'Z_rpc2',Z_rpc2).
setValue(z_BankSystem(Z_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports),z_BankSystem(Z_broker,Z_client,Z_server,Z_rpc1,Z_NEW_rpc2,Z_components,Z_connectors,Z_ports),'Z_rpc2',Z_NEW_rpc2).
value(z_BankSystem(Z_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports),'Z_components',Z_components).
setValue(z_BankSystem(Z_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports),z_BankSystem(Z_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_NEW_components,Z_connectors,Z_ports),'Z_components',Z_NEW_components).
value(z_BankSystem(Z_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports),'Z_connectors',Z_connectors).
setValue(z_BankSystem(Z_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports),z_BankSystem(Z_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_NEW_connectors,Z_ports),'Z_connectors',Z_NEW_connectors).
value(z_BankSystem(Z_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports),'Z_ports',Z_ports).
setValue(z_BankSystem(Z_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports),z_BankSystem(Z_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_BankSystem(globalState(This,Z_State,Z_State),Z_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports).
z_init_BankSystem(globalState(This,Z_State,Z_NewState),z_BankSystem(Z_broker_DECORV,Z_client_DECORV,Z_server_DECORV,Z_rpc1_DECORV,Z_rpc2_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV)):-
	new(This,'Broker',Z_TEMP2),
	Z_broker_DECORV=Z_TEMP2,
	new(This,'ATM',Z_TEMP3),
	Z_client_DECORV=Z_TEMP3,
	new(This,'BankServer',Z_TEMP4),
	Z_server_DECORV=Z_TEMP4,
	new(This,'RPC',Z_TEMP5),
	Z_rpc1_DECORV=Z_TEMP5,
	new(This,'RPC',Z_TEMP6),
	Z_rpc2_DECORV=Z_TEMP6,
	Z_components_DECORV=[Z_broker_DECORV,Z_client_DECORV,Z_server_DECORV],
	Z_connectors_DECORV=[Z_rpc1_DECORV,Z_rpc2_DECORV],
	Z_ports_DECORV=[]
	,addChangeOp(Z_State,z_init_BankSystem,[va('Z_broker'),va('Z_client'),va('Z_server'),va('Z_rpc1'),va('Z_rpc2'),va('Z_components'),va('Z_connectors'),va('Z_ports')],Z_NewState).

z_init_Arch_BankSystem(globalState(This,Z_State,Z_NewState),z_BankSystem(Z_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports),z_BankSystem(Z_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports)):-
	ivar(Z_client,'Z_callerB',Z_TEMP1),	ivar(Z_rpc1,'Z_caller',Z_TEMP2),	setIvar(Z_TEMP1,'Z_next',Z_TEMP2),
	ivar(Z_rpc1,'Z_caller',Z_TEMP3),	ivar(Z_client,'Z_callerB',Z_TEMP4),	setIvar(Z_TEMP3,'Z_next',Z_TEMP4),
	ivar(Z_rpc1,'Z_called',Z_TEMP5),	ivar(Z_broker,'Z_definer',Z_TEMP6),	setIvar(Z_TEMP5,'Z_next',Z_TEMP6),
	ivar(Z_server,'Z_callerB',Z_TEMP7),	ivar(Z_rpc2,'Z_caller',Z_TEMP8),	setIvar(Z_TEMP7,'Z_next',Z_TEMP8),
	ivar(Z_rpc2,'Z_caller',Z_TEMP9),	ivar(Z_server,'Z_callerB',Z_TEMP10),	setIvar(Z_TEMP9,'Z_next',Z_TEMP10),
	ivar(Z_rpc2,'Z_called',Z_TEMP11),	ivar(Z_broker,'Z_definer',Z_TEMP12),	setIvar(Z_TEMP11,'Z_next',Z_TEMP12),
	sendV(Z_server,execute,['z_InitService',[]],_),
	sendV(Z_client,execute,['z_Connect',[]],_),
	addChangeOp(Z_State,z_init_Arch_BankSystem,[],Z_NewState),
	z_BankSystem(globalState(This,Z_NewState,Z_NewState),Z_broker,Z_client,Z_server,Z_rpc1,Z_rpc2,Z_components,Z_connectors,Z_ports).

init_BankSystem_stateIni(tree('Root',[tree('BankSystem',['broker','client','server','rpc1','rpc2','components','connectors','ports'])])).
