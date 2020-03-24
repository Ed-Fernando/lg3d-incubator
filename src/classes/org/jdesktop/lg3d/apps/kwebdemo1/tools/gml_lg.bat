@echo off
echo graph [
echo   comment "LG KWeb as of %DATE%, (cleaned of dangeling edges)"
echo   directed 1
call gml_nodes_lg.bat
call gml_edges_lg.bat
echo   ]