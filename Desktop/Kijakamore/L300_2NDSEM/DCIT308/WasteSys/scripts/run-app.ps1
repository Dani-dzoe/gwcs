$root = Split-Path $PSScriptRoot -Parent
$driver = Join-Path $env:USERPROFILE '.m2\repository\org\xerial\sqlite-jdbc\3.46.1.3\sqlite-jdbc-3.46.1.3.jar'

if (-not (Test-Path $driver)) {
    throw "SQLite JDBC driver not found at $driver"
}

& (Join-Path $PSScriptRoot 'build.ps1')
java --enable-native-access=ALL-UNNAMED -cp ("{0};{1}" -f (Join-Path $root 'out'), $driver) gh.edu.ug.wastesys.cli.WasteSysApp