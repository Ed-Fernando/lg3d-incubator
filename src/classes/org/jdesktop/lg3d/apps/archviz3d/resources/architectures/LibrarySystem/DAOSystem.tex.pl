value(z_DAOSystem(Z_dao,Z_db,Z_rpc1,Z_components,Z_connectors,Z_ports),'Z_dao',Z_dao).
setValue(z_DAOSystem(Z_dao,Z_db,Z_rpc1,Z_components,Z_connectors,Z_ports),z_DAOSystem(Z_NEW_dao,Z_db,Z_rpc1,Z_components,Z_connectors,Z_ports),'Z_dao',Z_NEW_dao).
value(z_DAOSystem(Z_dao,Z_db,Z_rpc1,Z_components,Z_connectors,Z_ports),'Z_db',Z_db).
setValue(z_DAOSystem(Z_dao,Z_db,Z_rpc1,Z_components,Z_connectors,Z_ports),z_DAOSystem(Z_dao,Z_NEW_db,Z_rpc1,Z_components,Z_connectors,Z_ports),'Z_db',Z_NEW_db).
value(z_DAOSystem(Z_dao,Z_db,Z_rpc1,Z_components,Z_connectors,Z_ports),'Z_rpc1',Z_rpc1).
setValue(z_DAOSystem(Z_dao,Z_db,Z_rpc1,Z_components,Z_connectors,Z_ports),z_DAOSystem(Z_dao,Z_db,Z_NEW_rpc1,Z_components,Z_connectors,Z_ports),'Z_rpc1',Z_NEW_rpc1).
value(z_DAOSystem(Z_dao,Z_db,Z_rpc1,Z_components,Z_connectors,Z_ports),'Z_components',Z_components).
setValue(z_DAOSystem(Z_dao,Z_db,Z_rpc1,Z_components,Z_connectors,Z_ports),z_DAOSystem(Z_dao,Z_db,Z_rpc1,Z_NEW_components,Z_connectors,Z_ports),'Z_components',Z_NEW_components).
value(z_DAOSystem(Z_dao,Z_db,Z_rpc1,Z_components,Z_connectors,Z_ports),'Z_connectors',Z_connectors).
setValue(z_DAOSystem(Z_dao,Z_db,Z_rpc1,Z_components,Z_connectors,Z_ports),z_DAOSystem(Z_dao,Z_db,Z_rpc1,Z_components,Z_NEW_connectors,Z_ports),'Z_connectors',Z_NEW_connectors).
value(z_DAOSystem(Z_dao,Z_db,Z_rpc1,Z_components,Z_connectors,Z_ports),'Z_ports',Z_ports).
setValue(z_DAOSystem(Z_dao,Z_db,Z_rpc1,Z_components,Z_connectors,Z_ports),z_DAOSystem(Z_dao,Z_db,Z_rpc1,Z_components,Z_connectors,Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_DAOSystem(globalState(This,Z_State,Z_State),Z_dao,Z_db,Z_rpc1,Z_components,Z_connectors,Z_ports).
z_init_DAOSystem(globalState(This,Z_State,Z_NewState),z_DAOSystem(Z_dao_DECORV,Z_db_DECORV,Z_rpc1_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV)):-
	new(This,'DAO',Z_TEMP2),
	Z_dao_DECORV=Z_TEMP2,
	new(This,'DB',Z_TEMP3),
	Z_db_DECORV=Z_TEMP3,
	new(This,'RPC',Z_TEMP4),
	Z_rpc1_DECORV=Z_TEMP4,
	Z_components_DECORV=[Z_dao_DECORV,Z_db_DECORV],
	Z_connectors_DECORV=[Z_rpc1_DECORV],
	Z_ports_DECORV=[]
	,addChangeOp(Z_State,z_init_DAOSystem,[va('Z_dao'),va('Z_db'),va('Z_rpc1'),va('Z_components'),va('Z_connectors'),va('Z_ports')],Z_NewState).

z_Link(globalState(This,Z_State,Z_NewState),z_DAOSystem(Z_dao,Z_db,Z_rpc1,Z_components,Z_connectors,Z_ports),z_DAOSystem(Z_dao,Z_db,Z_rpc1,Z_components,Z_connectors,Z_ports)):-
	ivar(Z_dao,'Z_callerDB',Z_TEMP1),	ivar(Z_rpc1,'Z_caller',Z_TEMP2),	setIvar(Z_TEMP1,'Z_next',Z_TEMP2),
	ivar(Z_rpc1,'Z_caller',Z_TEMP3),	ivar(Z_dao,'Z_callerDB',Z_TEMP4),	setIvar(Z_TEMP3,'Z_next',Z_TEMP4),
	ivar(Z_rpc1,'Z_called',Z_TEMP5),	ivar(Z_db,'Z_portDAO',Z_TEMP6),	setIvar(Z_TEMP5,'Z_next',Z_TEMP6),
	addChangeOp(Z_State,z_Link,[],Z_NewState),
	z_DAOSystem(globalState(This,Z_NewState,Z_NewState),Z_dao,Z_db,Z_rpc1,Z_components,Z_connectors,Z_ports).

init_DAOSystem_stateIni(tree('Root',[tree('DAOSystem',['dao','db','rpc1','components','connectors','ports'])])).
