call runcrud.bat
if "%ERRORLEVEL%" == "0" goto openbrawser
echo.
echo Problems in application startup - breaking work... Sorry...
goto fail

:openbrawser
start chrome http://localhost:8080/crud/v1/task/getTasks
goto end

:fail
echo.
echo There are errors

:end
echo.
echo Mission completed