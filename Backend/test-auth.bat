@echo off
echo Testing MicroGram Authentication Flow
echo.

echo 1. Registering new user...
curl -X POST http://localhost:8080/api/auth/signup ^
  -H "Content-Type: application/json" ^
  -d "{\"username\":\"testuser\",\"email\":\"test@example.com\",\"password\":\"password123\",\"name\":\"Test User\"}"
echo.
echo.

echo 2. Logging in...
curl -X POST http://localhost:8080/api/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"usernameOrEmail\":\"testuser\",\"password\":\"password123\"}" ^
  -o login_response.json
echo.

echo 3. Extracting token and testing authenticated endpoint...
for /f "tokens=2 delims=:, " %%a in ('findstr "accessToken" login_response.json') do set TOKEN=%%a
set TOKEN=%TOKEN:"=%
curl -X GET http://localhost:8080/api/users/me ^
  -H "Authorization: Bearer %TOKEN%"
echo.
echo.

echo 4. Logging out...
curl -X POST http://localhost:8080/api/auth/logout ^
  -H "Authorization: Bearer %TOKEN%"
echo.

del login_response.json
pause