@echo off 
set work_path=G:\������Ϣϵͳ
set d2j_path=G:\huaweiPro\dex2jar-2.x\dex-tools\build\distributions\dex-tools-2.1-SNAPSHOT
set cutline=******************************************
G: 
cd %work_path% 
for /d %%s in (*) do (
echo %cutline%
echo ���ڴ���%%s
echo %cutline%

copy %%s\*.dex %d2j_path%
cd %d2j_path%
call d2j-dex2jar.bat classes*.dex
copy *.jar %work_path%\%%s
del classes*.jar
del classes*.dex
cd %work_path%

) 
echo %cutline%
echo ȫ���������
echo %cutline%
pause 